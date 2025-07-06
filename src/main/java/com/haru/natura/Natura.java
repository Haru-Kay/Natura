package com.haru.natura;

import com.haru.natura.block.NaturaBlocks;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import com.haru.natura.block.entity.NaturaBlockEntities;
import com.haru.natura.item.NaturaCreativeModTabs;
import com.haru.natura.item.NaturaItems;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.worldgen.biome.NaturaBiomeColors;
import com.haru.natura.worldgen.biome.NaturaFoliageColor;
import com.haru.natura.worldgen.tree.NaturaFoliagePlacerTypes;
import com.haru.natura.worldgen.tree.NaturaTreeDecoratorTypes;
import com.haru.natura.worldgen.tree.NaturaTrunkPlacerTypes;
import com.mojang.logging.LogUtils;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.lang.reflect.Field;
import java.util.Random;

// The value here should match an entry in the META-INF/mods.toml file
@SuppressWarnings("unused")
@Mod(Natura.MODID)
//@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class Natura
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "natura";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();
    public static final Random RANDOM = new Random();
    public static Natura instance;
    public static boolean configLoaded = false;

    //public static final FeatureType[] FEATURE_TYPES = FeatureType.values();

    public Natura(FMLJavaModLoadingContext context)
    {
        instance = this;

        //context.registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);
        //context.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT_SPEC);

        IEventBus eventBus = context.getModEventBus();

        NaturaBlocks.register(eventBus);
        NaturaItems.register(eventBus);
        NaturaCreativeModTabs.register(eventBus);

        NaturaWoodTypes.init();
        NaturaCreativeModTabs.init();

        NaturaTrunkPlacerTypes.register(eventBus);
        NaturaFoliagePlacerTypes.register(eventBus);
        NaturaTreeDecoratorTypes.register(eventBus);
        NaturaBlockEntities.register(eventBus);
        // Register the commonSetup method for modloading
        eventBus.addListener(this::commonSetup);



        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);


        // Register our mod's ForgeConfigSpec so that Forge can create and load the config file for us
        context.registerConfig(ModConfig.Type.COMMON, com.haru.natura.Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

    }

    /*
    @SubscribeEvent
    static void configChanged(final ModConfigEvent configEvent) {
        ModConfig config = configEvent.getConfig();
        if (config.getModId().equals(MODID)) {
            Config.clearCache(config.getSpec());
            if (config.getSpec() == Config.SERVER_SPEC) {
                configLoaded = true;
            }
        }

    }
     */
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {
        // Do something when the server starts
        LOGGER.info("HELLO from server starting");
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(NaturaBlockEntities.SIGN.get(), SignRenderer::new);
            event.registerBlockEntityRenderer(NaturaBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
        }

        @SubscribeEvent
        public static void onRegisterRenderLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
            ItemBlockRenderTypes.setRenderLayer(NaturaWoodTypes.MAPLE.leaves.get(), RenderType.cutoutMipped());
            for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
                try {
                    WoodBuilder builderObj = (WoodBuilder) builder.get(null);
                    //event.registerLayerDefinition(builderObj.boatLayer.get(), BoatModel::createBodyModel);
                    //event.registerLayerDefinition(builderObj.chestBoatLayer.get(), ChestBoatModel::createBodyModel);

                } catch (IllegalAccessException e) {
                    Natura.LOGGER.info("Could not access wood type " + builder.getName() + ". Skipping.");
                }
            }
        }
//amaranth, willow, tiger, redwood dark
        @SubscribeEvent
        public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
            event.register((state, level, pos, tintIndex) -> NaturaFoliageColor.getMapleColor(),
                    NaturaWoodTypes.MAPLE.leaves.get()
            );
            event.register((state, level, pos, tintIndex) -> NaturaBiomeColors.getAverageFoliageColorDark(level, pos),
                    NaturaWoodTypes.REDWOOD.leaves.get());
            event.register((state, level, pos, tintIndex) -> NaturaBiomeColors.getAverageFoliageColor(level, pos),
                    NaturaWoodTypes.HOPSEED.leaves.get(),
                    NaturaWoodTypes.AMARANTH.leaves.get(),
                    NaturaWoodTypes.EUCALYPTUS.leaves.get());
        }

        @SubscribeEvent
        public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
            event.register((stack, tintIndex) -> NaturaFoliageColor.getMapleColor(),
                    NaturaWoodTypes.MAPLE.leaves.get()
            );
            event.register((stack, tintIndex) -> NaturaFoliageColor.getDefaultColor(),
                    NaturaWoodTypes.HOPSEED.leaves.get(),
                    NaturaWoodTypes.EUCALYPTUS.leaves.get(),
                    NaturaWoodTypes.AMARANTH.leaves.get(),
                    NaturaWoodTypes.REDWOOD.leaves.get()
                    );
        }
    }
}
