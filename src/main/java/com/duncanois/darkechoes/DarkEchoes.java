package com.duncanois.darkechoes;

import com.duncanois.darkechoes.client.ModItemTags;
import com.duncanois.darkechoes.client.ModMenus;
import com.duncanois.darkechoes.combat.CombatEvents;
import com.duncanois.darkechoes.combat.CombatRules;
import com.duncanois.darkechoes.config.CombatConfig;
import com.duncanois.darkechoes.registry.*;
import com.mojang.logging.LogUtils;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.item.Item;
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

    @SubscribeEvent // on the mod event bus
    public static void modifyComponents(ModifyDefaultComponentsEvent event) {
//        TODO WIP!!! still figuring out what "Components not bound yet" means
//        event.modifyMatching(
//                (item, components) -> item.getDefaultInstance().is(ModItemTags.AUGMENTABLE_GEAR),
//                builder -> builder.set(ModDataComponents.AUGMENT_SLOTS, 0)
//        );
//        event.modifyMatching(
//                (item, components) -> item.getDefaultInstance().is(ModItemTags.AUGMENTABLE_GEAR),
//                (components, context, item) -> {
//                    components.set(ModDataComponents.AUGMENT_SLOTS, 0);
//                }
//        );
    }
}
