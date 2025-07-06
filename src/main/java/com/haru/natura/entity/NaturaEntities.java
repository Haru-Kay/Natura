package com.haru.natura.entity;

import com.haru.natura.Natura;
import com.haru.natura.entity.custom.NaturaBoatEntity;
import com.haru.natura.entity.custom.NaturaChestBoatEntity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class NaturaEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES =
            DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, Natura.MODID);
    public static final RegistryObject<EntityType<NaturaBoatEntity>> NATURA_BOAT =
            ENTITY_TYPES.register("mod_boat", () -> EntityType.Builder.<NaturaBoatEntity>of(NaturaBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("natura_boat"));
    public static final RegistryObject<EntityType<NaturaChestBoatEntity>> NATURA_CHEST_BOAT =
            ENTITY_TYPES.register("mod_chest_boat", () -> EntityType.Builder.<NaturaChestBoatEntity>of(NaturaChestBoatEntity::new, MobCategory.MISC)
                    .sized(1.375f, 0.5625f).build("natura_chest_boat"));

    public static void register(IEventBus eventBus) {
        ENTITY_TYPES.register(eventBus);
    }
}
