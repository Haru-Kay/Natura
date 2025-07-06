package com.haru.natura.worldgen.tree;

import com.haru.natura.Natura;
import com.haru.natura.worldgen.tree.custom.decorator.WillowVineDecorator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NaturaTreeDecoratorTypes {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS =
            DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, Natura.MODID);

    public static final RegistryObject<TreeDecoratorType<WillowVineDecorator>> WILLOW_VINE_DECORATOR =
            TREE_DECORATORS.register("willow_tree_decorator", () -> new TreeDecoratorType<>(WillowVineDecorator.CODEC));

    public static void register(IEventBus eventBus) {
        TREE_DECORATORS.register(eventBus);
    }
}
