package com.haru.natura.worldgen.tree.custom.grower;

import com.haru.natura.worldgen.NaturaConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class AmaranthTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource pRandom, boolean pHasFlowers) {
        return pHasFlowers ? NaturaConfiguredFeatures.AMARANTH_TREE_BEES : NaturaConfiguredFeatures.AMARANTH_TREE;
    }
}
