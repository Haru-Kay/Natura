package com.haru.natura.worldgen;

import com.haru.natura.Config;
import com.haru.natura.Natura;
import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.util.NaturaWoodTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public class NaturaPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MAPLE_TREE_PLACED_KEY = registerKey("maple_tree_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        //register(context, OVERWORLD_WOOD_PLACED_KEY, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.OVERWORLD_WOOD_KEY),
        //        NaturaOrePlacement.commonOrePlacement(12,
        //                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(88))));

        register(context, MAPLE_TREE_PLACED_KEY, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.MAPLE_TREE_KEY),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(1, 0.1f, 1),
                        NaturaWoodTypes.MAPLE.sapling.get()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Natura.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
