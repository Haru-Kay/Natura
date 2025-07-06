package com.haru.natura.item;

import com.haru.natura.Natura;
import com.haru.natura.util.NaturaWoodTypes;
import com.haru.natura.block.custom.wood.RedwoodBuilder;
import com.haru.natura.block.custom.wood.WoodBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.lang.reflect.Field;

public class NaturaCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
        DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Natura.MODID);

    public static RegistryObject<CreativeModeTab> NATURA_NATURE_TAB;

    public static void init() {
        NATURA_NATURE_TAB = CREATIVE_MODE_TABS.register("natura_nature_tab",
                () -> CreativeModeTab.builder().icon(() -> new ItemStack(NaturaWoodTypes.MAPLE.sapling.get()))
                        .title(Component.translatable("creativetab.natura_nature_tab"))
                        .displayItems((pParameters, pOutput) -> {
                            try {
                                for (Field builder : NaturaWoodTypes.class.getDeclaredFields()) {
                                    var builderObj = builder.get(null);

                                    if (builderObj instanceof RedwoodBuilder woodBuilder) {
                                        generateRedwood(woodBuilder, pOutput);
                                        generatedShared(woodBuilder, pOutput);
                                    } else if(builderObj instanceof WoodBuilder woodBuilder) {
                                        generateGeneric(woodBuilder, pOutput);
                                        generatedShared(woodBuilder, pOutput);
                                    } else {
                                        Natura.LOGGER.info(builder.getClass() + " " + builder.getName() + " is not a valid WoodBuilder.");
                                    }
                                }

                            } catch (IllegalAccessException e) {
                                throw new RuntimeException(e);
                            }
                        }).build());
    }

    private static void generateRedwood(RedwoodBuilder woodBuilder, CreativeModeTab.Output pOutput) {
        pOutput.accept(woodBuilder.bark.get());
        pOutput.accept(woodBuilder.heart.get());
        pOutput.accept(woodBuilder.root.get());
    }

    private static void generateGeneric(WoodBuilder woodBuilder, CreativeModeTab.Output pOutput) {
        pOutput.accept(woodBuilder.log.get());
        pOutput.accept(woodBuilder.wood.get());
        pOutput.accept(woodBuilder.strippedLog.get());
        pOutput.accept(woodBuilder.strippedWood.get());
    }

    private static void generatedShared(WoodBuilder woodBuilder, CreativeModeTab.Output pOutput) {
        pOutput.accept(woodBuilder.planks.get());
        pOutput.accept(woodBuilder.stairs.get());
        pOutput.accept(woodBuilder.slab.get());
        pOutput.accept(woodBuilder.fence.get());
        pOutput.accept(woodBuilder.fenceGate.get());
        pOutput.accept(woodBuilder.door.get());
        pOutput.accept(woodBuilder.trapdoor.get());
        pOutput.accept(woodBuilder.button.get());
        pOutput.accept(woodBuilder.pressurePlate.get());
        pOutput.accept(woodBuilder.sapling.get());
        pOutput.accept(woodBuilder.leaves.get());
        pOutput.accept(woodBuilder.bookshelf.get());
        pOutput.accept(woodBuilder.chiseledBookshelf.get());
        pOutput.accept(woodBuilder.workbench.get());
        pOutput.accept(woodBuilder.signItem.get());
        pOutput.accept(woodBuilder.hangingSignItem.get());
    }


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
