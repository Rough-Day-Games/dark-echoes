package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

@EventBusSubscriber(modid = DarkEchoes.MOD_ID)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider((out, lookupProvider) -> new LootTableProvider(
                out,
                Set.of(),
                List.of(
                        new LootTableProvider.SubProviderEntry(
                                LootTables::new,
                                LootContextParamSets.EMPTY
                        )
                ), lookupProvider
        ));
        event.createProvider(Models::new);
        event.createProvider(LangProvider::new);
        event.createProvider(ModItemTags::new);
    }
}
