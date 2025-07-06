package com.haru.natura.block.entity;

import com.haru.natura.Natura;
import com.haru.natura.block.custom.wood.WoodBuilder;
import com.haru.natura.block.entity.custom.NaturaSignBlockEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChiseledBookShelfBlockEntity;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class NaturaBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Natura.MODID);
    public static final RegistryObject<BlockEntityType<NaturaSignBlockEntity>> SIGN =
            BLOCK_ENTITIES.register("natura_sign", () ->
                    BlockEntityType.Builder.of(NaturaSignBlockEntity::new,
                            WoodBuilder.getSignBlocks()).build(null));

    public static final RegistryObject<BlockEntityType<NaturaSignBlockEntity>> HANGING_SIGN =
            BLOCK_ENTITIES.register("natura_hanging_sign", () ->
                    BlockEntityType.Builder.of(NaturaSignBlockEntity::new,
                            WoodBuilder.getHangingSignBlocks()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }

    public static <T extends BlockEntityType> RegistryObject<T> register(String name, Supplier<T> entity) {
        return BLOCK_ENTITIES.register(name, entity);
    }

}
