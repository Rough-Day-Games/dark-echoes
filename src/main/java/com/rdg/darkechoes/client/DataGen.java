package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

import static com.rdg.darkechoes.registry.ModRegistries.BUILDER;


@EventBusSubscriber(modid = DarkEchoes.MOD_ID)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(GlobalLootModProvider::new);
        event.createProvider((output, lookupProvider) -> new LootTableProvider(
                output, Set.of(), List.of(new LootTableProvider.SubProviderEntry(
                ModBlockLootSubProvider::new,
                LootContextParamSets.BLOCK
        )), lookupProvider
        ));
        event.createProvider(Models::new);
        event.createProvider(LangProvider::new);
        event.createProvider(ModBlockTags::new);
        event.createProvider(ModItemTags::new);
        event.createDatapackRegistryObjects(BUILDER);
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}
