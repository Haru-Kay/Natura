package com.haru.natura.worldgen.tree;

import com.haru.natura.Natura;
import com.haru.natura.worldgen.tree.custom.foliage.HopseedFoliagePlacer;
import com.haru.natura.worldgen.tree.custom.foliage.RedwoodFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class NaturaFoliagePlacerTypes {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACERS =
            DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, Natura.MODID);

    public static final RegistryObject<FoliagePlacerType<HopseedFoliagePlacer>> HOPSEED_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("hopseed_foliage_placer", () -> new FoliagePlacerType<>(HopseedFoliagePlacer.CODEC));
    public static final RegistryObject<FoliagePlacerType<RedwoodFoliagePlacer>> REDWOOD_FOLIAGE_PLACER =
            FOLIAGE_PLACERS.register("redwood_foliage_placer", () -> new FoliagePlacerType<>(RedwoodFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACERS.register(eventBus);
    }
}
