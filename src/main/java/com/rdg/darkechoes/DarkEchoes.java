package com.rdg.darkechoes;

import com.mojang.logging.LogUtils;
import com.rdg.darkechoes.client.ModMenus;
import com.rdg.darkechoes.combat.CombatEvents;
import com.rdg.darkechoes.combat.CombatRules;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.helpers.AugStationData;
import com.rdg.darkechoes.registry.*;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.slf4j.Logger;

@Mod(DarkEchoes.MOD_ID)
public final class DarkEchoes {
    public static final String MOD_ID = "darkechoes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DarkEchoes(IEventBus modBus, ModContainer modContainer) {
        ModDataComponents.COMPONENTS.register(modBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modBus);
        ModCodec.GLOBAL_LOOT_MOD_SERIALIZERS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModRecipes.SERIALIZERS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlocks.BLOCK_ENTITY_TYPES.register(modBus);
        ModBlocks.BLOCK_TYPE.register(modBus);
        ModMenus.MENU_TYPE.register(modBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, CombatConfig.SPEC);
        modBus.addListener(DarkEchoes::onConfigLoading);
        modBus.addListener(DarkEchoes::onConfigReloading);
        modBus.addListener(DarkEchoes::addCreativeTabContents);
        modBus.addListener(DarkEchoes::payloadHandlers);
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

    private static void payloadHandlers(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
        registrar.playToServer(
                AugStationData.TYPE,
                AugStationData.STREAM_CODEC,
                AugStationData::handle
        );
    }
}
