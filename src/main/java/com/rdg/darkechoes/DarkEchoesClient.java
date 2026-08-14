package com.rdg.darkechoes;

import com.rdg.darkechoes.client.ModMenus;
import com.rdg.darkechoes.client.screen.BaseAugStationScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = DarkEchoes.MOD_ID, dist = Dist.CLIENT)
@EventBusSubscriber(modid = DarkEchoes.MOD_ID, value = Dist.CLIENT)
public class DarkEchoesClient {
    public DarkEchoesClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }

    @SubscribeEvent
    static void onClientSetup(final FMLClientSetupEvent event) {
        DarkEchoes.LOGGER.info("Setting up Dark Echoes client!");
    }

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.AUGMENT_STATION_MENU.get(), BaseAugStationScreen::new);
    }
}
