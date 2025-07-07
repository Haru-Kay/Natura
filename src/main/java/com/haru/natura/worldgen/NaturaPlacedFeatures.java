package com.haru.natura.worldgen;

import com.haru.natura.Config;
import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.data.worldgen.placement.VegetationPlacements;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class NaturaPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MAPLE_TREE = registerKey("maple_tree");
    public static final ResourceKey<PlacedFeature> MAPLE_TREE_BEES = registerKey("maple_tree_bees");
    public static final ResourceKey<PlacedFeature> SILVERBELL_TREE = registerKey("silverbell_tree");
    public static final ResourceKey<PlacedFeature> SILVERBELL_TREE_BEES = registerKey("silverbell_tree_bees");
    public static final ResourceKey<PlacedFeature> TIGER_TREE = registerKey("tiger_tree");
    public static final ResourceKey<PlacedFeature> TIGER_TREE_BEES = registerKey("tiger_tree_bees");
    public static final ResourceKey<PlacedFeature> SAKURA_TREE = registerKey("sakura_tree");
    public static final ResourceKey<PlacedFeature> SAKURA_TREE_FOREST = registerKey("sakura_tree_forest");
    public static final ResourceKey<PlacedFeature> EUCALYPTUS_TREE = registerKey("eucalyptus_tree");
    public static final ResourceKey<PlacedFeature> EUCALYPTUS_TREE_PLAINS = registerKey("eucalyptus_tree_plains");
    public static final ResourceKey<PlacedFeature> REDWOOD_TREE = registerKey("redwood_tree");
    public static final ResourceKey<PlacedFeature> HOPSEED_TREE = registerKey("hopseed_tree");
    public static final ResourceKey<PlacedFeature> HOPSEED_TREE_BEES = registerKey("hopseed_tree_bees");
    public static final ResourceKey<PlacedFeature> WILLOW_TREE = registerKey("willow_tree");
    public static final ResourceKey<PlacedFeature> WILLOW_TREE_BEES = registerKey("willow_tree_bees");
    public static final ResourceKey<PlacedFeature> AMARANTH_TREE = registerKey("amaranth_tree");
    public static final ResourceKey<PlacedFeature> AMARANTH_TREE_BEES = registerKey("amaranth_tree_bees");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        //register(context, OVERWORLD_WOOD, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.OVERWORLD_WOOD),
        //        NaturaOrePlacement.commonOrePlacement(12,
        //                HeightRangePlacement.uniform(VerticalAnchor.absolute(-64), VerticalAnchor.absolute(88))));

        /*
        countExtra
            p_195365: count - how many trees are being placed with normal distribution each check
            p_195366: chance - the decimal chance for extra generation
            p_195367: count2 - the amount of extra trees to generate
         */
        register(context, MAPLE_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.MAPLE_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.mapleRarity, 1),
                        NaturaWoodTypes.MAPLE.sapling.get()));
        register(context, SILVERBELL_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.SILVERBELL_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.silverbellRarity, 1),
                        NaturaWoodTypes.SILVERBELL.sapling.get()));
        register(context, TIGER_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.TIGER_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.tigerRarity, 1),
                        NaturaWoodTypes.TIGER.sapling.get()));
        register(context, SAKURA_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.SAKURA_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.sakuraSpawnRarity, 1),
                        NaturaWoodTypes.SAKURA.sapling.get()));
        register(context, SAKURA_TREE_FOREST, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.SAKURA_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / (5 * Config.sakuraSpawnRarity), 1),
                        NaturaWoodTypes.SAKURA.sapling.get()));
        register(context, EUCALYPTUS_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.EUCALYPTUS_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.eucalyptusSpawnRarity, 1),
                        NaturaWoodTypes.EUCALYPTUS.sapling.get()));
        register(context, EUCALYPTUS_TREE_PLAINS, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.EUCALYPTUS_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / (int) (1.5 * Config.eucalyptusSpawnRarity), 1),
                        NaturaWoodTypes.EUCALYPTUS.sapling.get()));
        register(context, REDWOOD_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.REDWOOD_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.redwoodSpawnRarity, 1),
                        NaturaWoodTypes.REDWOOD.sapling.get()));
        register(context, HOPSEED_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.HOPSEED_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.hopseedSpawnRarity, 1),
                        NaturaWoodTypes.HOPSEED.sapling.get()));
        register(context, WILLOW_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.HOPSEED_TREE_BEES),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.willowRarity, 1),
                        NaturaWoodTypes.WILLOW.sapling.get()));
        register(context, AMARANTH_TREE, configuredFeatures.getOrThrow(NaturaConfiguredFeatures.AMARANTH_TREE),
                VegetationPlacements.treePlacement(PlacementUtils.countExtra(0, 1.0f / Config.amaranthRarity, 1),
                        NaturaWoodTypes.AMARANTH.sapling.get()));
    }

    public static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, ResourceLocation.fromNamespaceAndPath(Natura.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuration,
                                 List<PlacementModifier> modifiers) {
        context.register(key, new PlacedFeature(configuration, List.copyOf(modifiers)));
    }
}
