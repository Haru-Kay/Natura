package com.haru.natura.worldgen.tree.custom.foliage;

import com.haru.natura.worldgen.tree.NaturaFoliagePlacerTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.RandomSource;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;

public class RedwoodFoliagePlacer extends FoliagePlacer {
    public static final Codec<RedwoodFoliagePlacer> CODEC = RecordCodecBuilder.create(redwoodFoliagePlacerInstance
            -> foliagePlacerParts(redwoodFoliagePlacerInstance).and(Codec.intRange(0, 16).fieldOf("height")
            .forGetter(fp -> fp.height)).apply(redwoodFoliagePlacerInstance, RedwoodFoliagePlacer::new));
    private final int height;

    public RedwoodFoliagePlacer(IntProvider pRadius, IntProvider pOffset, int height) {
        super(pRadius, pOffset);
        this.height = height;
    }

    @Override
    protected FoliagePlacerType<?> type() {
        return NaturaFoliagePlacerTypes.REDWOOD_FOLIAGE_PLACER.get();
    }

    protected void createFoliage(LevelSimulatedReader level, FoliagePlacer.FoliageSetter setter, RandomSource random,
                                 TreeConfiguration config, int maxHeight, FoliagePlacer.FoliageAttachment attachment,
                                 int height, int radius, int offset) {

    }

    @Override
    public int foliageHeight(RandomSource pRandom, int pHeight, TreeConfiguration pConfig) {
        return 0;
    }

    @Override
    protected boolean shouldSkipLocation(RandomSource pRandom, int pLocalX, int pLocalY, int pLocalZ, int pRange, boolean pLarge) {
        return false;
    }
}
