package com.haru.natura.worldgen;

import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public class NaturaOrePlacement {
    public static List<PlacementModifier> orePlacement(PlacementModifier mod1, PlacementModifier mod2) {
        return List.of(mod1, InSquarePlacement.spread(), mod2, BiomeFilter.biome());
    }

    public static List<PlacementModifier> commonOrePlacement(int pCount, PlacementModifier pHeightRange) {
        return orePlacement(CountPlacement.of(pCount), pHeightRange);
    }

    public static List<PlacementModifier> rareOrePlacement(int pChance, PlacementModifier pHeightRange) {
        return orePlacement(RarityFilter.onAverageOnceEvery(pChance), pHeightRange);
    }
}
