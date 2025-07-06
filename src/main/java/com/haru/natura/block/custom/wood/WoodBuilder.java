package com.haru.natura.block.custom.wood;

import com.haru.natura.Natura;
import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.block.custom.*;
import com.haru.natura.block.entity.NaturaBlockEntities;
import com.haru.natura.block.entity.custom.NaturaHangingSignBlockEntity;
import com.haru.natura.block.entity.custom.NaturaSignBlockEntity;
import com.haru.natura.item.NaturaItems;
import com.haru.natura.item.custom.NaturaBoatItem;
import com.haru.natura.util.NaturaWoodTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


public class WoodBuilder {
    public String name;
    public AbstractTreeGrower treeGrower;
    public MapColor color;
    public WoodType woodType;
    public int maxDistance;

    public RegistryObject<Block> log;
    public RegistryObject<Block> wood;
    public RegistryObject<Block> strippedLog;
    public RegistryObject<Block> strippedWood;
    public RegistryObject<Block> planks;
    public RegistryObject<Block> stairs;
    public RegistryObject<Block> slab;
    public RegistryObject<Block> fence;
    public RegistryObject<Block> fenceGate;
    public RegistryObject<Block> door;
    public RegistryObject<Block> trapdoor;
    public RegistryObject<Block> button;
    public RegistryObject<Block> pressurePlate;
    public RegistryObject<Block> sapling;
    public RegistryObject<Block> leaves;
    public RegistryObject<NaturaStandingSignBlock> sign;
    public RegistryObject<NaturaWallSignBlock> wallSign;
    public RegistryObject<Block> hangingSign;
    public RegistryObject<Block> hangingWallSign;
    public RegistryObject<Block> workbench;
    public RegistryObject<Block> bookshelf;
    public RegistryObject<Block> chiseledBookshelf;

    public RegistryObject<Item> signItem;
    public RegistryObject<Item> hangingSignItem;
    public RegistryObject<Item> boat;
    public RegistryObject<Item> chestBoat;

    public RegistryObject<BlockEntityType<ChiseledBookShelfBlockEntity>> chiseledBookshelfEntity;

    public WoodBuilder(String name, AbstractTreeGrower treeGrower) {
        this.name = name;
        this.treeGrower = treeGrower;
    }
    public WoodBuilder(String name, AbstractTreeGrower treeGrower, MapColor color) {
        this.name = name;
        this.treeGrower = treeGrower;
        this.color = color;
    }
    public WoodBuilder(String name, AbstractTreeGrower treeGrower, int maxDistance) {
        this.name = name;
        this.treeGrower = treeGrower;
        this.maxDistance = maxDistance;
    }

    public WoodBuilder build() {
        strippedLog = NaturaBlocks.registerBlock(name + "_stripped_log",
                () -> new NaturaLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
        strippedWood = NaturaBlocks.registerBlock(name + "_stripped_wood",
                () -> new NaturaLogBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
        log = NaturaBlocks.registerBlock(name + "_log",
                () -> new NaturaLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG), strippedLog.get()));
        wood = NaturaBlocks.registerBlock(name + "_wood",
                () -> new NaturaLogBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD), strippedWood.get()));
        leaves = NaturaBlocks.registerBlock(name + "_leaves",
                () -> new NaturaLeavesBlock(maxDistance, BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
        buildShared();
        return this;
    }
    public void buildShared() {
        woodType = WoodType.register(new WoodType(Natura.MODID + ":" + name, BlockSetType.OAK));
        planks = NaturaBlocks.registerBlock(name + "_planks", 
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
        stairs = NaturaBlocks.registerBlock(name + "_stairs",
                () -> new StairBlock(() -> planks.get().defaultBlockState(), BlockBehaviour.Properties.copy(Blocks.OAK_STAIRS)));
        slab = NaturaBlocks.registerBlock(name + "_slab",
                () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SLAB)));
        fence = NaturaBlocks.registerBlock(name + "_fence",
                () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE)));
        fenceGate = NaturaBlocks.registerBlock(name + "_fence_gate",
                () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.OAK_FENCE_GATE), SoundEvents.FENCE_GATE_OPEN, SoundEvents.FENCE_GATE_CLOSE));
        door = NaturaBlocks.registerBlock(name + "_door",
                () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_DOOR), BlockSetType.OAK));
        trapdoor = NaturaBlocks.registerBlock(name + "_trapdoor",
                () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.OAK_TRAPDOOR), BlockSetType.OAK));
        button = NaturaBlocks.registerBlock(name + "_button",
                () -> new ButtonBlock(BlockBehaviour.Properties.copy(Blocks.OAK_BUTTON), BlockSetType.OAK, 10, true));
        pressurePlate = NaturaBlocks.registerBlock(name + "_pressure_plate",
                () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS), BlockSetType.OAK));
        sapling = NaturaBlocks.registerBlock(name + "_sapling",
                () -> new SaplingBlock(treeGrower, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
        workbench = NaturaBlocks.registerBlock(name + "_workbench",
                () -> new CraftingTableBlock(BlockBehaviour.Properties.copy(Blocks.CRAFTING_TABLE)));
        bookshelf = NaturaBlocks.registerBlock(name + "_bookshelf",
                () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF)));
        chiseledBookshelf = NaturaBlocks.registerBlock(name + "_chiseled_bookshelf",
                () -> new NaturaChiseledBookshelfBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_BOOKSHELF)));
        boat = NaturaItems.register(name + "_boat",
                () -> new NaturaBoatItem(false, Boat.Type.OAK, (new Item.Properties()).stacksTo(1)));
        chestBoat = NaturaItems.register(name + "_chest_boat",
                () -> new NaturaBoatItem(true, Boat.Type.OAK, (new Item.Properties()).stacksTo(1)));
        chiseledBookshelfEntity = NaturaBlockEntities.register(name + "_chiseled_bookshelf",
                () -> BlockEntityType.Builder.of(ChiseledBookShelfBlockEntity::new, chiseledBookshelf.get()).build(null));
        sign = NaturaBlocks.register(name + "_sign",
                () -> new NaturaStandingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_SIGN), woodType));
        wallSign = NaturaBlocks.register(name + "_wall_sign",
                () -> new NaturaWallSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_SIGN), woodType));
        hangingSign = NaturaBlocks.register(name + "_hanging_sign",
                () -> new NaturaHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_HANGING_SIGN), woodType));
        hangingWallSign = NaturaBlocks.register(name + "_hanging_wall_sign",
                () -> new NaturaWallHangingSignBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WALL_HANGING_SIGN), woodType));
        signItem = NaturaItems.register(name + "_sign",
                () -> new SignItem(new Item.Properties().stacksTo(16), sign.get(), wallSign.get()));
        hangingSignItem = NaturaItems.register(name + "_hanging_sign",
                () -> new SignItem(new Item.Properties().stacksTo(16), hangingSign.get(), hangingWallSign.get()));

    }

    public static Block[] getSignBlocks() {
        List<Block> ret = new ArrayList<>();

        for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
            try {
                WoodBuilder builderObj = (WoodBuilder) builder.get(null);
                ret.add(builderObj.sign.get());
                ret.add(builderObj.wallSign.get());
            } catch (IllegalAccessException e) {
                Natura.LOGGER.info("Could not access wood type " + builder.getName() + ". Skipping.");
            }
        }

        return ret.toArray(new Block[0]);
    }

    public static Block[] getHangingSignBlocks() {
        List<Block> ret = new ArrayList<>();

        for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
            try {
                WoodBuilder builderObj = (WoodBuilder) builder.get(null);
                ret.add(builderObj.hangingSign.get());
                ret.add(builderObj.hangingWallSign.get());

            } catch (IllegalAccessException e) {
                Natura.LOGGER.info("Could not access wood type " + builder.getName() + ". Skipping.");
            }
        }

        return ret.toArray(new Block[0]);
    }
}
