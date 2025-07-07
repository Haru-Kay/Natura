package com.haru.natura.worldgen;

import com.haru.natura.Natura;
import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ForgeBiomeModifiers;
import net.minecraftforge.registries.ForgeRegistries;

public class NaturaBiomeModifiers {
    //public static final ResourceKey<BiomeModifier> ADD_WOOD_ORE = registerKey("add_wood_ore");
    public static final ResourceKey<BiomeModifier> ADD_MAPLE_TREE = registerKey("add_maple_tree");
    public static final ResourceKey<BiomeModifier> ADD_SILVERBELL_TREE = registerKey("add_silverbell_tree");
    public static final ResourceKey<BiomeModifier> ADD_TIGER_TREE = registerKey("add_tiger_tree");
    public static final ResourceKey<BiomeModifier> ADD_SAKURA_TREE_FOREST = registerKey("add_sakura_tree_forest");
    public static final ResourceKey<BiomeModifier> ADD_SAKURA_TREE = registerKey("add_sakura_tree");
    public static final ResourceKey<BiomeModifier> ADD_EUCALYPTUS_TREE = registerKey("add_eucalyptus_tree");
    public static final ResourceKey<BiomeModifier> ADD_EUCALYPTUS_TREE_MOUNTAIN = registerKey("add_eucalyptus_tree_mountain");
    public static final ResourceKey<BiomeModifier> ADD_EUCALYPTUS_TREE_PLAINS = registerKey("add_eucalyptus_tree_plains");
    public static final ResourceKey<BiomeModifier> ADD_REDWOOD_TREE = registerKey("add_redwood_tree");
    public static final ResourceKey<BiomeModifier> ADD_HOPSEED_TREE = registerKey("add_hopseed_tree");
    public static final ResourceKey<BiomeModifier> ADD_WILLOW_TREE = registerKey("add_willow_tree");
    public static final ResourceKey<BiomeModifier> ADD_WILLOW_TREE_SWAMP = registerKey("add_willow_tree_swamp");
    public static final ResourceKey<BiomeModifier> ADD_AMARANTH_TREE = registerKey("add_amaranth_tree");

    public static void bootstrap(BootstapContext<BiomeModifier> context) {
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biomes = context.lookup(Registries.BIOME);

        //context.register(ADD_WOOD_ORE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
        //        biomes.getOrThrow(BiomeTags.IS_OVERWORLD),
        //        HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.OVERWORLD_WOOD)),
        //        GenerationStep.Decoration.UNDERGROUND_ORES));

        context.register(ADD_MAPLE_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.MAPLE_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SAKURA_TREE_FOREST, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.SAKURA_TREE_FOREST)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SAKURA_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.SAKURA_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_EUCALYPTUS_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.EUCALYPTUS_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_SILVERBELL_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.SILVERBELL_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_TIGER_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_FOREST),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.TIGER_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_WILLOW_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_RIVER),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.WILLOW_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_WILLOW_TREE_SWAMP, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_SWAMP),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.WILLOW_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_AMARANTH_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_JUNGLE),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.AMARANTH_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_HOPSEED_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.HOPSEED_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_EUCALYPTUS_TREE_MOUNTAIN, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(BiomeTags.IS_MOUNTAIN),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.EUCALYPTUS_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_EUCALYPTUS_TREE_PLAINS, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.EUCALYPTUS_TREE_PLAINS)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

        context.register(ADD_REDWOOD_TREE, new ForgeBiomeModifiers.AddFeaturesBiomeModifier(
                biomes.getOrThrow(Tags.Biomes.IS_PLAINS),
                HolderSet.direct(placedFeatures.getOrThrow(NaturaPlacedFeatures.REDWOOD_TREE)),
                GenerationStep.Decoration.VEGETAL_DECORATION));

    }

    private static ResourceKey<BiomeModifier> registerKey(String name) {
        return ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(Natura.MODID, name));
    }
}
