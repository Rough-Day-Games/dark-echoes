package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import static com.rdg.darkechoes.registry.ModRegistries.BUILDER;

//import static com.rdg.darkechoes.client.AugmentsGen.BUILDER;

@EventBusSubscriber(modid = DarkEchoes.MOD_ID)
public class DataGen {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent.Client event) {
        event.createProvider(GlobalLootModProvider::new);
        event.createProvider(Models::new);
        event.createProvider(LangProvider::new);
        event.createProvider(ModItemTags::new);
        event.createDatapackRegistryObjects(BUILDER);
        event.createProvider(ModRecipeProvider.Runner::new);
    }
}
