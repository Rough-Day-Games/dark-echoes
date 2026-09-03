package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.ModItems;
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
        add(ModItems.ECHO_PICKAXE.get(), "Echo Pickaxe");
        add(ModItems.ECHO_SHOVEL.get(), "Echo Shovel");
        add(ModItems.ECHO_HOE.get(), "Echo Hoe");
        add(ModItems.T_ONE_AUGSTATION.get(), "Augmentation Station: Tier One");
        add(ModItems.T_TWO_AUGSTATION.get(), "Augmentation Station: Tier Two");
        add(ModItems.T_THREE_AUGSTATION.get(), "Augmentation Station: Tier Three");

        add("mod.darkechoes.name", "Dark Echoes");
        add("tooltip.darkechoes.armor_progression", "%s Level %s: +%s%% armor - Level %s in %s %s");
        add("tooltip.darkechoes.armor_progression_max", "%s Level %s: +%s%% armor - Max Level");
        add("tooltip.darkechoes.block.many", "blocks");
        add("tooltip.darkechoes.block.one", "block");
        add("tooltip.darkechoes.awakened", "Awakened");
        add("tooltip.darkechoes.hit.many", "hits");
        add("tooltip.darkechoes.hit.one", "hit");
        add("tooltip.darkechoes.kill.many", "kills");
        add("tooltip.darkechoes.kill.one", "kill");
        add("tooltip.darkechoes.progression_pending", "Progressing against %s - Lock in %s %s");
        add("tooltip.darkechoes.tool_progression", "%s Level %s: +%s%% mining speed - Level %s in %s %s");
        add("tooltip.darkechoes.tool_progression_max", "%s Level %s: +%s%% mining speed - Max Level");
        add("tooltip.darkechoes.tool_progression_pending", "Progressing against %s - Lock in %s %s");
        add("tooltip.darkechoes.weapon_progression", "%s Level %s: +%s%% dmg - Level %s in %s %s");
        add("tooltip.darkechoes.weapon_progression_max", "%s Level %s: +%s%% dmg - Max Level");
        add("container.augment_station", "AugmenStation");
        add("button.darkechoes.augstation.initawakening", "Awaken");
        add("button.darkechoes.augstation.initresonance", "Resonate");
        add("button.darkechoes.augstation.initaugment", "Augment");
        add("tooltip.darkechoes.fragile", "Fragile");
        add("tooltip.darkechoes.weakened", "Weakened");
        add("container.augment_station.no_adaptation", "None");
        add("container.augment_station.incompatible", "Gear is incompatible with the current tier of AugmenStation!");
        add("container.augment_station.limit_augment_slots", "This gear's augment slot count has reached its limit!");
        add("container.augment_station.target_details", "%s: LV %s");
        add("container.augment_station.pending_target_details", "%s: Pending");
        add("container.augment_station.progression_pending", "Lock in %s %s");
        add("container.augment_station.weapon_progression", "+%s%% dmg - LV %s in %s %s");
        add("container.augment_station.tool_progression", "+%s%% mining spd - LV %s in %s %s");
        add("container.augment_station.weapon_progression_max", "+%s%% dmg - Max");
        add("container.augment_station.tool_progression_max", "+%s%% mining spd - Max");
        add("container.augment_station.augment_slots", "Augment Slots: %s");
        add("container.augment_station.adaptation_slots", "Adaptation Slots: %s");
        add("container.augment_station.gear_not_awakened", "Not awakened yet!");
        add("menu.darkechoes.augment_station.page.augments", "Augments");
        add("menu.darkechoes.augment_station.page.awakening",  "Awakening");
        add("menu.darkechoes.augment_station.page.adaptation", "Adaptation");
        add("menu.darkechoes.augment_station.page.augmentation", "Augmenting");
        add("container.augment_station.gear_weakened", "This gear is weakened!");
        add("container.augment_station.gear_fragile", "This gear is fragile!");
        add("container.augment_station.fragile_warning", "Will make gear fragile! Are you sure?");
        add("container.augment_station.weakened_warning", "Will make item weakened! Are you sure?");
        add("augment.darkechoes.malleable", "Malleable");
        add("tooltip.darkechoes.malleable_broken", "Limbo!");
    }
}
