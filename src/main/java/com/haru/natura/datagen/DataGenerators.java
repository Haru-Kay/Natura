package com.haru.natura.datagen;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = Natura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(event.includeServer(), new NaturaRecipeProvider(packOutput));
        generator.addProvider(event.includeServer(), NaturaLootTableProvider.create(packOutput));

        generator.addProvider(event.includeClient(), new NaturaBlockStateProvider(packOutput, existingFileHelper));
        generator.addProvider(event.includeClient(), new NaturaItemModelProvider(packOutput, existingFileHelper));

        NaturaBlockTagGenerator blockTagGenerator = generator.addProvider(event.includeServer(),
                new NaturaBlockTagGenerator(packOutput, lookupProvider, existingFileHelper));
        generator.addProvider(event.includeServer(), new NaturaItemTagGenerator(packOutput, lookupProvider, blockTagGenerator.contentsGetter(), existingFileHelper));

        //generator.addProvider(event.includeServer(), new NaturaGlobalLootModifiersProvider(packOutput));
        generator.addProvider(event.includeServer(), new NaturaPoiTypeTagsProvider(packOutput, lookupProvider, existingFileHelper));

        generator.addProvider(event.includeServer(), new NaturaWorldGenProvider(packOutput, lookupProvider));
    }
}
