package com.haru.natura.datagen;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.util.concurrent.CompletableFuture;
public class NaturaItemTagGenerator extends ItemTagsProvider {
    public NaturaItemTagGenerator(PackOutput p_275343_, CompletableFuture<HolderLookup.Provider> p_275729_,
                               CompletableFuture<TagLookup<Block>> p_275322_, @Nullable ExistingFileHelper existingFileHelper) {
        super(p_275343_, p_275729_, p_275322_, Natura.MODID, existingFileHelper);
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

    private void generateRedwood(RedwoodBuilder woodBuilder) {
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(woodBuilder.heart.get().asItem())
                .add(woodBuilder.root.get().asItem())
                .add(woodBuilder.bark.get().asItem());
    }

    private void generateGeneric(WoodBuilder woodBuilder) {
        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(woodBuilder.log.get().asItem())
                .add(woodBuilder.wood.get().asItem())
                .add(woodBuilder.strippedLog.get().asItem())
                .add(woodBuilder.strippedWood.get().asItem());
    }

    private void generatedShared(WoodBuilder woodBuilder) {
        this.tag(ItemTags.PLANKS)
                .add(woodBuilder.planks.get().asItem());
        this.tag(ItemTags.BOATS)
                .add(woodBuilder.boat.get().asItem());
        this.tag(ItemTags.CHEST_BOATS)
                .add(woodBuilder.chestBoat.get().asItem());
    }
}
