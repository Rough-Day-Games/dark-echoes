package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.menus.BaseAugStationMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModMenus {
    public static final DeferredRegister<MenuType<?>> MENU_TYPE = DeferredRegister.create(Registries.MENU, DarkEchoes.MOD_ID);

    public static final Supplier<MenuType<BaseAugStationMenu>> AUGMENT_STATION_MENU = MENU_TYPE.register("augment_station_menu", () -> new MenuType<>(BaseAugStationMenu::new, FeatureFlags.DEFAULT_FLAGS));
}
