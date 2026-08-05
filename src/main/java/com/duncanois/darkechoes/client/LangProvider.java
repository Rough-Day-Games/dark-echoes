package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(output, DarkEchoes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(ModItems.RESONANCE_CRYSTAL.get(), "Resonance Crystal");
        add(ModItems.ECHO_HELMET.get(), "Echo Helmet");
        add(ModItems.ECHO_CHESTPLATE.get(), "Echo Chestplate");
        add(ModItems.ECHO_LEGGINGS.get(), "Echo Leggings");
        add(ModItems.ECHO_BOOTS.get(), "Echo Boots");
        add(ModItems.ECHO_SWORD.get(), "Echo Sword");
        add(ModItems.ECHO_AXE.get(), "Echo Axe");
        add(ModItems.T_ONE_AUGSTATION.get(), "Augmentation Station: Tier One");
        add(ModItems.T_TWO_AUGSTATION.get(), "Augmentation Station: Tier Two");
        add(ModItems.T_THREE_AUGSTATION.get(), "Augmentation Station: Tier Three");

        add("mod.darkechoes.name", "Dark Echoes");
        add("tooltip.darkechoes.armor_progression", "%s Level %s: +%s%% armor - Level %s in %s %s");
        add("tooltip.darkechoes.armor_progression_max", "%s Level %s: +%s%% armor - Max Level");
        add("tooltip.darkechoes.echo_fusion", "Echo Fusion");
        add("tooltip.darkechoes.hit.many", "hits");
        add("tooltip.darkechoes.hit.one", "hit");
        add("tooltip.darkechoes.kill.many", "kills");
        add("tooltip.darkechoes.kill.one", "kill");
        add("tooltip.darkechoes.progression_pending", "Progressing against %s - Lock in %s %s");
        add("tooltip.darkechoes.weapon_progression", "%s Level %s: +%s%% dmg - Level %s in %s %s");
        add("tooltip.darkechoes.weapon_progression_max", "%s Level %s: +%s%% dmg - Max Level");
        add("container.augment_station", "Augmentation Station");
    }
}
