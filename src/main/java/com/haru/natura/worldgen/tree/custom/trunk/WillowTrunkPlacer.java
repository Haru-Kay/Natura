package com.haru.natura.worldgen.tree.custom.trunk;

import com.google.common.collect.ImmutableList;
import com.haru.natura.Natura;
import com.haru.natura.worldgen.tree.NaturaTrunkPlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;

import java.util.List;
import java.util.function.BiConsumer;

public class WillowTrunkPlacer extends TrunkPlacer {
    public static final Codec<WillowTrunkPlacer> CODEC = RecordCodecBuilder.create(willowTrunkPlacerInstance ->
            trunkPlacerParts(willowTrunkPlacerInstance).apply(willowTrunkPlacerInstance, WillowTrunkPlacer::new));
    public WillowTrunkPlacer(int pBaseHeight, int pHeightRandA, int pHeightRandB) {
        super(pBaseHeight, pHeightRandA, pHeightRandB);
    }

    @Override
    protected TrunkPlacerType<?> type() {
        return NaturaTrunkPlacerTypes.WILLOW_TRUNK_PLACER.get();
    }

    @Override
    public List<FoliagePlacer.FoliageAttachment> placeTrunk(LevelSimulatedReader pLevel, BiConsumer<BlockPos, BlockState> pBlockSetter,
                                                            RandomSource pRandom, int pFreeTreeHeight, BlockPos pPos, TreeConfiguration pConfig) {
        BlockPos ground = pPos.below();
        setDirtAt(pLevel, pBlockSetter, pRandom, ground, pConfig);
        int height = pFreeTreeHeight + pRandom.nextInt(heightRandA);

        for(int i = 0; i < height; i++) {
            placeLog(pLevel, pBlockSetter, pRandom, pPos.above(i), pConfig);


        }

        return ImmutableList.of(new FoliagePlacer.FoliageAttachment(pPos.above(height), 0, false));
    }
}
