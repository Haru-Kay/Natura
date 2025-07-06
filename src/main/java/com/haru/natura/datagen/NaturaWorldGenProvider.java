package com.haru.natura.datagen;

import com.haru.natura.Natura;
import com.haru.natura.worldgen.NaturaBiomeModifiers;
import com.haru.natura.worldgen.NaturaConfiguredFeatures;
import com.haru.natura.worldgen.NaturaPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class NaturaWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, NaturaConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, NaturaPlacedFeatures::bootstrap)
            .add(ForgeRegistries.Keys.BIOME_MODIFIERS, NaturaBiomeModifiers::bootstrap);
    public NaturaWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Natura.MODID));
    }
}
