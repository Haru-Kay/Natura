package com.haru.natura.item;

import com.haru.natura.Natura;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class NaturaItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Natura.MODID);




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


    public static <T extends Item> RegistryObject<T> register(String name, Supplier<T> itemObject) {
        return ITEMS.register(name, itemObject);
    }
}
