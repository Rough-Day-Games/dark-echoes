package com.duncanois.darkechoes;

import com.duncanois.darkechoes.combat.CombatEvents;
import com.duncanois.darkechoes.combat.CombatRules;
import com.duncanois.darkechoes.config.CombatConfig;
import com.duncanois.darkechoes.registry.ModDataComponents;
import com.duncanois.darkechoes.registry.ModItems;
import com.duncanois.darkechoes.registry.ModRecipes;
import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.slf4j.Logger;

@Mod(DarkEchoes.MOD_ID)
public final class DarkEchoes {
    public static final String MOD_ID = "darkechoes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DarkEchoes(IEventBus modBus, ModContainer modContainer) {
        ModDataComponents.COMPONENTS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModRecipes.SERIALIZERS.register(modBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, CombatConfig.SPEC);
        modBus.addListener(DarkEchoes::onConfigLoading);
        modBus.addListener(DarkEchoes::onConfigReloading);
        modBus.addListener(DarkEchoes::addCreativeTabContents);
        NeoForge.EVENT_BUS.register(CombatEvents.class);
    }

    private static void addCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(new ItemStack(ModItems.RESONANCE_CRYSTAL.get()));
        }
    }

    private static void onConfigLoading(ModConfigEvent.Loading event) {
        if (event.getConfig().getSpec() == CombatConfig.SPEC) {
            CombatRules.reload();
        }
    }

    private static void onConfigReloading(ModConfigEvent.Reloading event) {
        if (event.getConfig().getSpec() == CombatConfig.SPEC) {
            CombatRules.reload();
        }
    }
}
