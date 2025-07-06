package com.haru.natura.datagen.loot;

import com.haru.natura.Natura;
import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.util.Set;

public class NaturaBlockLootTables extends BlockLootSubProvider {
    public NaturaBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
            try {
                var builderObj = builder.get(null);

                if (builderObj instanceof RedwoodBuilder woodBuilder) {
                    Natura.LOGGER.info("NaturaBlockLootTables found redwood");
                    generateRedwood(woodBuilder);
                    generatedShared(woodBuilder);
                } else if(builderObj instanceof WoodBuilder woodBuilder) {
                    Natura.LOGGER.info("NaturaBlockLootTables found wood");
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

    protected void generateRedwood(RedwoodBuilder woodBuilder) {
        this.dropSelf(woodBuilder.bark.get());
        this.dropSelf(woodBuilder.root.get());
        this.dropSelf(woodBuilder.heart.get());
    }

    protected void generateGeneric(WoodBuilder woodBuilder) {
        this.dropSelf(woodBuilder.log.get());
        this.dropSelf(woodBuilder.wood.get());
        this.dropSelf(woodBuilder.strippedLog.get());
        this.dropSelf(woodBuilder.strippedWood.get());
    }

    protected void generatedShared(WoodBuilder woodBuilder) {
        this.dropSelf(woodBuilder.planks.get());
        this.dropSelf(woodBuilder.stairs.get());
        this.dropSelf(woodBuilder.fence.get());
        this.dropSelf(woodBuilder.fenceGate.get());
        this.dropSelf(woodBuilder.trapdoor.get());
        this.dropSelf(woodBuilder.button.get());
        this.dropSelf(woodBuilder.pressurePlate.get());
        this.dropSelf(woodBuilder.sapling.get());
        this.dropSelf(woodBuilder.sign.get());
        this.dropSelf(woodBuilder.wallSign.get());
        this.dropSelf(woodBuilder.hangingSign.get());
        this.dropSelf(woodBuilder.hangingWallSign.get());
        this.dropSelf(woodBuilder.workbench.get());
        this.add(woodBuilder.bookshelf.get(), block ->
                createSingleItemTableWithSilkTouch(block, Items.BOOK, ConstantValue.exactly(3.0F)));
        this.dropWhenSilkTouch(woodBuilder.chiseledBookshelf.get());

        this.add(woodBuilder.slab.get(), block ->
                createSlabItemTable(woodBuilder.slab.get()));
        this.add(woodBuilder.door.get(), block ->
                createDoorTable(woodBuilder.door.get()));
        this.add(woodBuilder.leaves.get(), block ->
                createLeavesDrops(block, woodBuilder.sapling.get(), NORMAL_LEAVES_SAPLING_CHANCES));
        this.add(woodBuilder.sign.get(), block ->
                createSingleItemTable(woodBuilder.signItem.get()));
        this.add(woodBuilder.wallSign.get(), block ->
                createSingleItemTable(woodBuilder.signItem.get()));
        this.add(woodBuilder.hangingSign.get(), block ->
                createSingleItemTable(woodBuilder.hangingSignItem.get()));
        this.add(woodBuilder.hangingWallSign.get(), block ->
                createSingleItemTable(woodBuilder.hangingSignItem.get()));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return NaturaBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
