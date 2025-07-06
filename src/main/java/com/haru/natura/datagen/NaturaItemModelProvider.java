package com.haru.natura.datagen;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

public class NaturaItemModelProvider extends ItemModelProvider {
    public NaturaItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Natura.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

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
    }

    private void generateGeneric(WoodBuilder woodBuilder) {
    }

    private void generatedShared(WoodBuilder woodBuilder) {
        simpleBlockItem(woodBuilder.door);

        fenceItem(woodBuilder.fence, woodBuilder.planks);
        buttonItem(woodBuilder.button, woodBuilder.planks);

        evenSimplerBlockItem(woodBuilder.stairs);
        evenSimplerBlockItem(woodBuilder.slab);
        evenSimplerBlockItem(woodBuilder.pressurePlate);
        evenSimplerBlockItem(woodBuilder.fenceGate);

        trapdoorItem(woodBuilder.trapdoor);

        simpleItem(woodBuilder.signItem);
        simpleItem(woodBuilder.hangingSignItem);

        simpleItem(woodBuilder.boat);
        simpleItem(woodBuilder.chestBoat);

        saplingItem(woodBuilder.sapling);
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Natura.MODID,"item/" + item.getId().getPath()));
    }

    public ItemModelBuilder saplingItem(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Natura.MODID,"block/" + block.getId().getPath()));
    }

    public void evenSimplerBlockItem(RegistryObject<Block> block) {
        this.withExistingParent(Natura.MODID + ":" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath()));
    }

    public void trapdoorItem(RegistryObject<Block> block) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(),
                modLoc("block/" + ForgeRegistries.BLOCKS.getKey(block.get()).getPath() + "_bottom"));
    }

    public void fenceItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/fence_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Natura.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void buttonItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/button_inventory"))
                .texture("texture",  ResourceLocation.fromNamespaceAndPath(Natura.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    public void wallItem(RegistryObject<Block> block, RegistryObject<Block> baseBlock) {
        this.withExistingParent(ForgeRegistries.BLOCKS.getKey(block.get()).getPath(), mcLoc("block/wall_inventory"))
                .texture("wall",  ResourceLocation.fromNamespaceAndPath(Natura.MODID, "block/" + ForgeRegistries.BLOCKS.getKey(baseBlock.get()).getPath()));
    }

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Natura.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Natura.MODID,"item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleBlockItemBlockTexture(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.tryParse("item/generated")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(Natura.MODID,"block/" + item.getId().getPath()));
    }
}
