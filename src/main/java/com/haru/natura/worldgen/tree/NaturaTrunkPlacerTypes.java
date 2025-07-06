package com.haru.natura.worldgen.tree;
import com.haru.natura.Natura;
import com.haru.natura.worldgen.biome.NaturaFoliageColor;
import com.haru.natura.worldgen.tree.custom.trunk.RedwoodTrunkPlacer;
import com.haru.natura.worldgen.tree.custom.trunk.WillowTrunkPlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NaturaTrunkPlacerTypes {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER =
            DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, Natura.MODID);

    public static final RegistryObject<TrunkPlacerType<WillowTrunkPlacer>> WILLOW_TRUNK_PLACER =
            TRUNK_PLACER.register("willow_trunk_placer", () -> new TrunkPlacerType<>(WillowTrunkPlacer.CODEC));
    public static final RegistryObject<TrunkPlacerType<RedwoodTrunkPlacer>> REDWOOD_TRUNK_PLACER =
            TRUNK_PLACER.register("redwood_trunk_placer", () -> new TrunkPlacerType<>(RedwoodTrunkPlacer.CODEC));

    public static void register(IEventBus eventBus) {
        TRUNK_PLACER.register(eventBus);
    }
}