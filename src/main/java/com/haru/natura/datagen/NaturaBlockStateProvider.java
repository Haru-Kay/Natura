package com.haru.natura.datagen;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonElement;
import com.haru.natura.Natura;
import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.*;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelProvider;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class NaturaBlockStateProvider extends BlockStateProvider {
    public NaturaBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Natura.MODID, exFileHelper);
    }
    @Override
    protected void registerStatesAndModels() {

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


    protected void generateRedwood(RedwoodBuilder woodBuilder) {
        blockWithItem(woodBuilder.bark);
        blockWithItem(woodBuilder.root);
        blockWithItem(woodBuilder.heart);
    }

    protected void generateGeneric(WoodBuilder woodBuilder) {
        logBlock((RotatedPillarBlock) woodBuilder.log.get());
        axisBlock((RotatedPillarBlock) woodBuilder.wood.get(), blockTexture(woodBuilder.log.get()), blockTexture(woodBuilder.log.get()));
        axisBlock(((RotatedPillarBlock) woodBuilder.strippedLog.get()), blockTexture(woodBuilder.strippedLog.get()),
                ResourceLocation.fromNamespaceAndPath(Natura.MODID, "block/" + woodBuilder.name + "_stripped_log_top"));
        axisBlock(((RotatedPillarBlock) woodBuilder.strippedWood.get()),  blockTexture(woodBuilder.strippedLog.get()),
                blockTexture(woodBuilder.strippedLog.get()));

        blockItem(woodBuilder.log);
        blockItem(woodBuilder.wood);
        blockItem(woodBuilder.strippedLog);
        blockItem(woodBuilder.strippedWood);
    }

    protected void generatedShared(WoodBuilder woodBuilder) {
        blockWithItem(woodBuilder.planks);
        stairsBlock(((StairBlock) woodBuilder.stairs.get()), blockTexture(woodBuilder.planks.get()));
        slabBlock(((SlabBlock) woodBuilder.slab.get()), blockTexture(woodBuilder.planks.get()), blockTexture(woodBuilder.planks.get()));
        fenceBlock(((FenceBlock) woodBuilder.fence.get()), blockTexture(woodBuilder.planks.get()));
        fenceGateBlock(((FenceGateBlock) woodBuilder.fenceGate.get()), blockTexture(woodBuilder.planks.get()));
        doorBlockWithRenderType(((DoorBlock) woodBuilder.door.get()), modLoc("block/" + woodBuilder.name + "_door_bottom"), modLoc("block/" + woodBuilder.name + "_door_top"), "cutout");
        trapdoorBlockWithRenderType(((TrapDoorBlock) woodBuilder.trapdoor.get()), modLoc("block/" + woodBuilder.name + "_trap_door"), true, "cutout");
        buttonBlock(((ButtonBlock) woodBuilder.button.get()), blockTexture(woodBuilder.planks.get()));
        pressurePlateBlock(((PressurePlateBlock) woodBuilder.pressurePlate.get()), blockTexture(woodBuilder.planks.get()));
        saplingBlock(woodBuilder.sapling);
        leavesBlock(woodBuilder.leaves, woodBuilder.name);
        signBlock(woodBuilder.sign.get(), woodBuilder.wallSign.get(),
                blockTexture(woodBuilder.planks.get()));
        hangingSignBlock(woodBuilder.hangingSign.get(), woodBuilder.hangingWallSign.get(),
                blockTexture(woodBuilder.planks.get()));
        workbenchBlock(woodBuilder.workbench, woodBuilder.name);
        bookshelfBlock(woodBuilder.bookshelf, woodBuilder.name);
    }

    public ResourceLocation blockTexture(Block block) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + name.getPath());
    }
    public ResourceLocation blockTexture(Block block, String path) {
        ResourceLocation name = key(block);
        return ResourceLocation.fromNamespaceAndPath(name.getNamespace(), ModelProvider.BLOCK_FOLDER + "/" + path + name.getPath());
    }
    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(),
                blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ResourceLocation texture) {
        ModelFile sign = models().sign(name(signBlock), texture);
        hangingSignBlock(signBlock, wallSignBlock, sign);
    }

    public void hangingSignBlock(Block signBlock, Block wallSignBlock, ModelFile sign) {
        simpleBlock(signBlock, sign);
        simpleBlock(wallSignBlock, sign);
    }

    private String name(Block block) {
        return key(block).getPath();
    }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject, String wood) {
        simpleBlockWithItem(blockRegistryObject.get(),
                models().singleTexture(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), ResourceLocation.parse("minecraft:block/leaves"),
                        "all", blockTexture(blockRegistryObject.get())).renderType("cutout_mipped"));
    }

    private void bookshelfBlock(RegistryObject<Block> blockRegistryObject, String wood) {
        simpleBlockWithItem(blockRegistryObject.get(), models().cubeColumn(wood + "_bookshelf",
                modLoc("block/" + wood + "_bookshelf"),
                modLoc("block/" + wood + "_planks")));
    }
    private void workbenchBlock(RegistryObject<Block> blockRegistryObject, String wood) {
        simpleBlockWithItem(blockRegistryObject.get(), models().cube(wood + "_workbench",
                modLoc("block/" + wood + "_planks"),
                modLoc("block/" + wood + "_workbench_top"),
                modLoc("block/" + wood + "_workbench_face"),
                modLoc("block/" + wood + "_workbench_side"),
                modLoc("block/" + wood + "_workbench_face"),
                modLoc("block/" + wood + "_workbench_side")));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile(Natura.MODID +
                ":block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }
}
