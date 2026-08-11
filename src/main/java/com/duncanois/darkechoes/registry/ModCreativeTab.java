package com.duncanois.darkechoes.registry;

import com.duncanois.darkechoes.DarkEchoes;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, DarkEchoes.MOD_ID);
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> DARK_ECHOES_TAB = CREATIVE_MODE_TABS.register("dark_echoes_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("mod.darkechoes.name"))
                    .withTabsBefore(CreativeModeTabs.COMBAT)
                    .icon(() -> ModItems.RESONANCE_CRYSTAL.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.RESONANCE_CRYSTAL);
                        output.accept(ModItems.ECHO_SWORD);
                        output.accept(ModItems.ECHO_AXE);
                        output.accept(ModItems.ECHO_PICKAXE);
                        output.accept(ModItems.ECHO_SHOVEL);
                        output.accept(ModItems.ECHO_HOE);
                        output.accept(ModItems.ECHO_HELMET);
                        output.accept(ModItems.ECHO_CHESTPLATE);
                        output.accept(ModItems.ECHO_LEGGINGS);
                        output.accept(ModItems.ECHO_BOOTS);
                        output.accept(ModItems.T_ONE_AUGSTATION);
                        output.accept(ModItems.T_TWO_AUGSTATION);
                        output.accept(ModItems.T_THREE_AUGSTATION);
                    })
                    .build());
}
