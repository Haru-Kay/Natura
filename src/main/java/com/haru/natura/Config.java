package com.haru.natura;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Forge's config APIs
@Mod.EventBusSubscriber(modid = Natura.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean generateRedwood = false;

    public static boolean generateMaple = true;
    public static boolean generateSilverbell = true;
    public static boolean generateAmaranth = true;
    public static boolean generateTiger = true;

    public static boolean generateWillow = true;
    public static boolean generateEucalyptus = true;
    public static boolean generateHopseed = true;
    public static boolean generateSakura = true;

    public static boolean generateBloodwood = true;
    public static boolean generateDarkwood = true;
    public static boolean generateFusewood = true;
    public static boolean generateGhostwood = true;

    public static int redwoodSpawnRarity = 150;

    public static int mapleRarity = 10;
    public static int silverbellRarity = 70;
    public static int amaranthRarity = 1;
    public static int tigerRarity = 32;

    public static int willowRarity = 10;
    public static int eucalyptusSpawnRarity = 25;
    public static int eucalyptusSpawnRange = 32;
    public static int hopseedSpawnRarity = 10;
    public static int hopseedSpawnRange = 20;
    public static int sakuraSpawnRarity = 10;
    public static int sakuraSpawnRange = 32;

    public static int bloodwoodSpawnRarity = 14;
    public static int darkwoodSpawnRarity = 10;
    public static int fusewoodSpawnRarity = 50;
    public static int ghostwoodSpawnRarity = 10;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {

    }
}

