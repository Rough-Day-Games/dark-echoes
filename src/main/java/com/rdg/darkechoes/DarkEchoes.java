package com.rdg.darkechoes;

import com.mojang.logging.LogUtils;
import com.rdg.darkechoes.client.ModMenus;
import com.rdg.darkechoes.combat.CombatEvents;
import com.rdg.darkechoes.combat.CombatRules;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.helpers.AugStationAugment;
import com.rdg.darkechoes.helpers.AugStationAwaken;
import com.rdg.darkechoes.helpers.AugStationPageListener;
import com.rdg.darkechoes.helpers.augment_value_effect.LevelBasedValue;
import com.rdg.darkechoes.progression.Augment;
import com.rdg.darkechoes.progression.ToolProgressionEvents;
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
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import static com.rdg.darkechoes.registry.ModRegistries.*;

@Mod(DarkEchoes.MOD_ID)
public final class DarkEchoes {
    public static final String MOD_ID = "darkechoes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DarkEchoes(IEventBus modBus, ModContainer modContainer) {
//        Augments.AUGMENTS.register(modBus);
        ModDataComponents.COMPONENTS.register(modBus);
        ModDataComponents.AUGMENT_COMPONENT_TYPES.register(modBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modBus);
        ModCodec.GLOBAL_LOOT_MOD_SERIALIZERS.register(modBus);
        ModItems.ITEMS.register(modBus);
        ModRecipeSerializers.SERIALIZERS.register(modBus);
        ModBlocks.BLOCKS.register(modBus);
        ModBlocks.BLOCK_ENTITY_TYPES.register(modBus);
        ModBlocks.BLOCK_TYPE.register(modBus);
        ModMenus.MENU_TYPE.register(modBus);
        modContainer.registerConfig(ModConfig.Type.SERVER, CombatConfig.SPEC);
        modBus.addListener(DarkEchoes::onConfigLoading);
        modBus.addListener(DarkEchoes::onConfigReloading);
        modBus.addListener(DarkEchoes::addCreativeTabContents);
        modBus.addListener(DarkEchoes::payloadHandlers);
        modBus.addListener(DarkEchoes::registerRegistries);
        modBus.addListener(DarkEchoes::registerDatapackRegistries);
        NeoForge.EVENT_BUS.register(CombatEvents.class);
        NeoForge.EVENT_BUS.register(ToolProgressionEvents.class);
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
                AugStationAwaken.TYPE,
                AugStationAwaken.STREAM_CODEC,
                AugStationAwaken::handle
        );

        registrar.playToServer(
                AugStationAugment.TYPE,
                AugStationAugment.STREAM_CODEC,
                AugStationAugment::handle
        );

        registrar.playToServer(
                AugStationPageListener.TYPE,
                AugStationPageListener.STREAM_CODEC,
                AugStationPageListener::handle
        );
    }

    private static void registerRegistries(NewRegistryEvent event) {
        event.register(AUGMENT_EFFECT_COMPONENT_TYPE);
        event.register(AUGMENT_PROVIDER_TYPE);
        event.register(AUGMENT_LEVEL_BASED_VALUE_TYPE);
        event.register(AUGMENT_VALUE_EFFECT_TYPE);
    }

    private static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                AUGMENTS_REGISTRY_KEY,
                Augment.DIRECT_CODEC,
                Augment.DIRECT_CODEC,
                builder -> builder.maxId(256)
        );
//        event.dataPackRegistry(
//                AUGMENT_LEVEL_BASED_VALUE_TYPE_KEY,
//                LevelBasedValue.CODEC,
//                LevelBasedValue.CODEC,
//                builder -> builder.maxId(256)
//        );
    }
}
