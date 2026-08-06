package com.duncanois.darkechoes;

import com.duncanois.darkechoes.client.ModItemTags;
import com.duncanois.darkechoes.client.ModMenus;
import com.duncanois.darkechoes.combat.CombatEvents;
import com.duncanois.darkechoes.combat.CombatRules;
import com.duncanois.darkechoes.config.CombatConfig;
import com.duncanois.darkechoes.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.ModifyDefaultComponentsEvent;
import org.slf4j.Logger;

import java.util.Optional;

@Mod(DarkEchoes.MOD_ID)
@EventBusSubscriber(modid = DarkEchoes.MOD_ID)
public final class DarkEchoes {
    public static final String MOD_ID = "darkechoes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public DarkEchoes(IEventBus modBus, ModContainer modContainer) {
        ModDataComponents.COMPONENTS.register(modBus);
        ModCreativeTab.CREATIVE_MODE_TABS.register(modBus);
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
