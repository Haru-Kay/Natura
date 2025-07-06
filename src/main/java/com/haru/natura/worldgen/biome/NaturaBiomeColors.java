package com.haru.natura.worldgen.biome;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.ColorResolver;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static net.minecraft.client.renderer.BiomeColors.FOLIAGE_COLOR_RESOLVER;
import static net.minecraft.client.renderer.BiomeColors.WATER_COLOR_RESOLVER;

@OnlyIn(Dist.CLIENT)
public class NaturaBiomeColors {
    public static final ColorResolver GRASS_COLOR_RESOLVER = Biome::getGrassColor;

    private static int getAverageColor(BlockAndTintGetter pLevel, BlockPos pBlockPos, ColorResolver pColorResolver) {
        return pLevel.getBlockTint(pBlockPos, pColorResolver);
    }

    public static int getAverageGrassColor(BlockAndTintGetter pLevel, BlockPos pBlockPos) {
        return getAverageColor(pLevel, pBlockPos, GRASS_COLOR_RESOLVER);
    }

    public static int getAverageFoliageColor(BlockAndTintGetter pLevel, BlockPos pBlockPos) {
        return getAverageColor(pLevel, pBlockPos, FOLIAGE_COLOR_RESOLVER);
    }

    public static int getAverageFoliageColorDark(BlockAndTintGetter pLevel, BlockPos pBlockPos) {
        return getAverageFoliageColor(pLevel, pBlockPos) + NaturaFoliageColor.getDarkenColor();
    }

    public static int getAverageWaterColor(BlockAndTintGetter pLevel, BlockPos pBlockPos) {
        return getAverageColor(pLevel, pBlockPos, WATER_COLOR_RESOLVER);
    }
}
