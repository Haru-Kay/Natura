package com.haru.natura.worldgen.tree.custom.foliage;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaUtil;
import com.haru.natura.worldgen.tree.NaturaFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class HopseedFoliagePlacer extends FoliagePlacer {
    public static final Codec<HopseedFoliagePlacer> CODEC = RecordCodecBuilder.create(willowFoliagePlacerInstance
            -> foliagePlacerParts(willowFoliagePlacerInstance).and(Codec.intRange(0, 16).fieldOf("height")
            .forGetter(fp -> fp.height)).apply(willowFoliagePlacerInstance, HopseedFoliagePlacer::new));
    private final int height;

    public HopseedFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return NaturaFoliagePlacerTypes.HOPSEED_FOLIAGE_PLACER.get();
    }

    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter setter, RandomSource random, TreeConfiguration config, int maxHeight, FoliagePlacer.FoliageAttachment attachment, int height, int radius, int offset) {
        BlockPos basePos = attachment.pos();
        int topY = basePos.getY() + height - 2;

        for (int y = topY - 2; y <= topY; y++) {
            int verticalOffset = y - topY;
            int layerRadius = 3 - verticalOffset;

            for (int x = basePos.getX() - layerRadius; x <= basePos.getX() + layerRadius; x++) {
                int offsetX = x - basePos.getX();

                for (int z = basePos.getZ() - layerRadius; z <= basePos.getZ() + layerRadius; z++) {
                    int offsetZ = z - basePos.getZ();

                    BlockPos currentPos = new BlockPos(x, y, z);
                    //IBlockState state = world.getBlockState(currentPos);

                    // Distance squared from the center
                    int distSq = offsetX * offsetX + offsetZ * offsetZ;

                    // Conditions for natural-looking leaf shape
                    boolean withinPrimaryRadius = (offsetX >= 0 || offsetZ >= 0 || distSq <= layerRadius * layerRadius);
                    boolean withinSecondaryRadius = (offsetX <= 0 && offsetZ <= 0 || distSq <= (layerRadius + 1) * (layerRadius + 1));
                    boolean randomVariation = (random.nextInt(4) != 0 || distSq <= (layerRadius - 1) * (layerRadius - 1));



                    boolean canPlace = level.isStateAtPosition(currentPos, state -> state.isAir()) ||
                            level.isStateAtPosition(currentPos, state -> state.canBeReplaced()) ||
                            level.isStateAtPosition(currentPos, state -> state.is(BlockTags.LEAVES));

                    if (withinPrimaryRadius && withinSecondaryRadius && randomVariation && canPlace) {
                        tryPlaceLeaf(level, setter, random, config, currentPos);
                    }
                }
            }
        }
    }

    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return this.height;
    }

    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return pLocalX == pRange && pLocalZ == pRange && (pRandom.nextInt(2) == 0 || pLocalY == 0);
    }
}
