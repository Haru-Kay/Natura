package com.haru.natura.datagen;

import com.haru.natura.datagen.loot.NaturaBlockLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;

import java.util.List;
import java.util.Set;
public class NaturaLootTableProvider {
    public static LootTableProvider create(PackOutput output) {
        return new LootTableProvider(output, Set.of(), List.of(
                new LootTableProvider.SubProviderEntry(NaturaBlockLootTables::new, LootContextParamSets.BLOCK)
        ));
    }
}
