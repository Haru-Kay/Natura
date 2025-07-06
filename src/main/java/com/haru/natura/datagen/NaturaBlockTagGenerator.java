package com.haru.natura.datagen;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;

public class NaturaBlockTagGenerator extends BlockTagsProvider {
    public NaturaBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, Natura.MODID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
            try {
                var builderObj = builder.get(null);

                if (builderObj instanceof RedwoodBuilder woodBuilder) {
                    generateRedwood(woodBuilder);
                    generatedShared(woodBuilder);
                } else if(builderObj instanceof WoodBuilder woodBuilder) {
                    generateGeneric(woodBuilder);
                    generatedShared(woodBuilder);
                } else {
                    Natura.LOGGER.info(builder.getClass() + " " + builder.getName() + " is not a valid WoodBuilder.");
                }
            } catch (IllegalAccessException e) {
                Natura.LOGGER.info("Could not access wood type " + builder.getName() + ". Skipping.");
            }
        }
    }

    private void generateGeneric(WoodBuilder woodBuilder) {
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(woodBuilder.log.get())
                .add(woodBuilder.wood.get())
                .add(woodBuilder.strippedLog.get())
                .add(woodBuilder.strippedWood.get());
        this.tag(BlockTags.LOGS)
                .add(woodBuilder.log.get())
                .add(woodBuilder.wood.get())
                .add(woodBuilder.strippedLog.get())
                .add(woodBuilder.strippedWood.get());
    }

    private void generateRedwood(RedwoodBuilder woodBuilder) {
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(woodBuilder.root.get())
                .add(woodBuilder.bark.get())
                .add(woodBuilder.heart.get());
        this.tag(BlockTags.LOGS)
                .add(woodBuilder.root.get())
                .add(woodBuilder.bark.get())
                .add(woodBuilder.heart.get());
    }

    private void generatedShared(WoodBuilder woodBuilder) {
        this.tag(BlockTags.FENCES).add(woodBuilder.fence.get());
        this.tag(BlockTags.FENCE_GATES).add(woodBuilder.fenceGate.get());

        this.tag(BlockTags.PLANKS).add(woodBuilder.planks.get());

        this.tag(BlockTags.LEAVES).add(woodBuilder.leaves.get());
        this.tag(BlockTags.SAPLINGS).add(woodBuilder.sapling.get());

        this.tag(BlockTags.STANDING_SIGNS).add(woodBuilder.sign.get());
        this.tag(BlockTags.WALL_SIGNS).add(woodBuilder.wallSign.get());
        this.tag(BlockTags.CEILING_HANGING_SIGNS).add(woodBuilder.hangingSign.get());
        this.tag(BlockTags.WALL_HANGING_SIGNS).add(woodBuilder.hangingWallSign.get());

        this.tag(BlockTags.WOODEN_DOORS).add(woodBuilder.door.get());
        this.tag(BlockTags.WOODEN_TRAPDOORS).add(woodBuilder.trapdoor.get());

        this.tag(BlockTags.ENCHANTMENT_POWER_PROVIDER).add(woodBuilder.bookshelf.get());
    }

}
