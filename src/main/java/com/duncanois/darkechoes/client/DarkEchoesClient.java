package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;

@Mod(value = DarkEchoes.MOD_ID, dist = Dist.CLIENT)
public final class DarkEchoesClient {
    public DarkEchoesClient(ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class,
                (minecraft, parent) -> new ConfigurationScreen(container, parent));
    }
}
