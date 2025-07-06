package com.haru.natura.worldgen;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.worldgen.tree.custom.decorator.WillowVineDecorator;
import com.haru.natura.worldgen.tree.custom.foliage.HopseedFoliagePlacer;
import com.haru.natura.worldgen.tree.custom.trunk.RedwoodTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FancyFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.treedecorators.BeehiveDecorator;
import net.minecraft.world.level.levelgen.feature.trunkplacers.FancyTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.GiantTrunkPlacer;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;

import java.util.List;
import java.util.OptionalInt;

public class NaturaConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMARANTH_TREE_KEY = registerKey("amaranth_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> AMARANTH_TREE_BEES_KEY = registerKey("amaranth_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EUCALYPTUS_TREE_KEY = registerKey("eucalyptus_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> EUCALYPTUS_TREE_BEES_KEY = registerKey("eucalyptus_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOPSEED_TREE_KEY = registerKey("hopseed_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> HOPSEED_TREE_BEES_KEY = registerKey("hopseed_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_KEY = registerKey("maple_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAPLE_TREE_BEES_KEY = registerKey("maple_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> REDWOOD_TREE_KEY = registerKey("redwood_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAKURA_TREE_KEY = registerKey("sakura_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SAKURA_TREE_BEES_KEY = registerKey("sakura_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVERBELL_TREE_KEY = registerKey("silverbell_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SILVERBELL_TREE_BEES_KEY = registerKey("silverbell_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIGER_TREE_KEY = registerKey("tiger_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TIGER_TREE_BEES_KEY = registerKey("tiger_tree_bees");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_TREE_KEY = registerKey("willow_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> WILLOW_TREE_BEES_KEY = registerKey("willow_tree_bees");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        //RuleTest stoneReplaceable = new TagMatchTest(BlockTags.STONE_ORE_REPLACEABLES);

        //List<OreConfiguration.TargetBlockState> overworldOakOre = List.of(OreConfiguration.target(stoneReplaceable,
        //        Blocks.OAK_LOG.defaultBlockState()));
        //register(context, OVERWORLD_WOOD_KEY, Feature.ORE, new OreConfiguration(overworldOakOre, 9));

        var amaranthBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.AMARANTH.log.get()),
                new StraightTrunkPlacer(9, 8, 0),
                BlockStateProvider.simple(NaturaWoodTypes.AMARANTH.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, AMARANTH_TREE_KEY, Feature.TREE, amaranthBuilder.build());
        register(context, AMARANTH_TREE_BEES_KEY, Feature.TREE,
                amaranthBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var eucalyptusBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.EUCALYPTUS.log.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(NaturaWoodTypes.EUCALYPTUS.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, EUCALYPTUS_TREE_KEY, Feature.TREE, eucalyptusBuilder.build());
        register(context, EUCALYPTUS_TREE_BEES_KEY, Feature.TREE,
                eucalyptusBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var hopseedBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.HOPSEED.log.get()),
                new GiantTrunkPlacer(3, 0, 0),
                BlockStateProvider.simple(NaturaWoodTypes.HOPSEED.leaves.get()),
                new HopseedFoliagePlacer(ConstantInt.of(5), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 2, 2)).ignoreVines();

        register(context, HOPSEED_TREE_KEY, Feature.TREE, hopseedBuilder.build());
        register(context, HOPSEED_TREE_BEES_KEY, Feature.TREE,
                hopseedBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var mapleBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.MAPLE.log.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(NaturaWoodTypes.MAPLE.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, MAPLE_TREE_KEY, Feature.TREE, mapleBuilder.build());
        register(context, MAPLE_TREE_BEES_KEY, Feature.TREE,
                mapleBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var sakuraBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.SAKURA.log.get()),
                new FancyTrunkPlacer(3, 11, 0),
                BlockStateProvider.simple(NaturaWoodTypes.SAKURA.leaves.get()),
                new FancyFoliagePlacer(ConstantInt.of(2), ConstantInt.of(4), 4),
                new TwoLayersFeatureSize(0, 0, 0, OptionalInt.of(4))).ignoreVines(); //needs heightmap for placed feature

        register(context, SAKURA_TREE_KEY, Feature.TREE, sakuraBuilder.build());
        register(context, SAKURA_TREE_BEES_KEY, Feature.TREE,
                sakuraBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var silverbellBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.SILVERBELL.log.get()),
                new StraightTrunkPlacer(4, 2, 0),
                BlockStateProvider.simple(NaturaWoodTypes.SILVERBELL.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, SILVERBELL_TREE_KEY, Feature.TREE, silverbellBuilder.build());
        register(context, SILVERBELL_TREE_BEES_KEY, Feature.TREE,
                silverbellBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var tigerBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.TIGER.log.get()),
                new StraightTrunkPlacer(6, 4, 0),
                BlockStateProvider.simple(NaturaWoodTypes.TIGER.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, TIGER_TREE_KEY, Feature.TREE, tigerBuilder.build());
        register(context, TIGER_TREE_BEES_KEY, Feature.TREE,
                tigerBuilder.decorators(List.of(new BeehiveDecorator(0.05F))).build());

        var willowBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.WILLOW.log.get()),
                new StraightTrunkPlacer(5, 4, 0),
                BlockStateProvider.simple(NaturaWoodTypes.WILLOW.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1));

        TreeConfiguration willowWithVines = willowBuilder.decorators(List.of(new WillowVineDecorator(0.25F))).build();
        register(context, WILLOW_TREE_KEY, Feature.TREE, willowWithVines);
        register(context, WILLOW_TREE_BEES_KEY, Feature.TREE,
                willowBuilder.decorators(List.of(new WillowVineDecorator(0.25F), new BeehiveDecorator(0.05F))).build());


        var redwoodBuilder = new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(NaturaWoodTypes.REDWOOD.bark.get()),
                new RedwoodTrunkPlacer(12, 12, 0),
                BlockStateProvider.simple(NaturaWoodTypes.REDWOOD.leaves.get()),
                new BlobFoliagePlacer(ConstantInt.of(2), ConstantInt.of(0), 3),
                new TwoLayersFeatureSize(1, 0, 1)).ignoreVines();

        register(context, REDWOOD_TREE_KEY, Feature.TREE, redwoodBuilder.build());
                

    }

    public static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, ResourceLocation.fromNamespaceAndPath(Natura.MODID, name));
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                          ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
