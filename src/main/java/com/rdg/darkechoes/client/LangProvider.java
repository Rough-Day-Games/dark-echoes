package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.Augments;
import com.rdg.darkechoes.registry.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class LangProvider extends LanguageProvider {
    public LangProvider(PackOutput output) {
        super(output, DarkEchoes.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        add("mod.darkechoes.name", "Dark Echoes");

        // Items
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
        add(ModItems.ECHO_SPEAR.get(), "Echo Spear");
        add(ModItems.TIER_ONE_REPAIR_KIT.get(), "Repair Kit: Tier One");
        add(ModItems.TIER_TWO_REPAIR_KIT.get(), "Repair Kit: Tier Two");
        add(ModItems.TIER_THREE_REPAIR_KIT.get(), "Repair Kit: Tier Three");
        add(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get(), "Echo Upgrade Smithing Template");
        add(ModItems.WARDEN_TOTEM.get(), "Totem of the Warden");
        add(ModItems.T_ONE_AUGSTATION.get(), "Augmentation Station: Tier One");
        add(ModItems.T_TWO_AUGSTATION.get(), "Augmentation Station: Tier Two");
        add(ModItems.T_THREE_AUGSTATION.get(), "Augmentation Station: Tier Three");

        // Augments
        addAugmentTranslations(Augments.MALLEABLE.identifier(), "Malleable");
        addAugmentTranslations(Augments.MAGIC_REBORN.identifier(), "Magic: Reborn");
        addAugmentTranslations(Augments.FLEXIBLE_ADAPTATION.identifier(),  "Flexible Adaptation");
        addAugmentTranslations(Augments.EARTH_SHATTERER.identifier(), "Earth Shatterer");
        addAugmentTranslations(Augments.HEAVENS.identifier(), "Rise to the Heavens");
        addAugmentTranslations(Augments.ECHO_SENSE.identifier(), "Echo Sense (WIP)");
        addAugmentTranslations(Augments.LOW_GRAVITY.identifier(), "Low Gravity");

        // Tooltips
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
        add("tooltip.darkechoes.fragile", "Fragile");
        add("tooltip.darkechoes.weakened", "Weakened");
        add("tooltip.darkechoes.malleable_broken", "Limbo!");

        // AugmenStation-related
        add("container.augment_station", "AugmenStation");
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
        add("container.augment_station.gear_weakened", "This gear is weakened!");
        add("container.augment_station.gear_fragile", "This gear is fragile!");
        add("container.augment_station.cannot_gain_augment_slots", "This gear cannot gain augment slots!");
        add("container.augment_station.proceed_with_true_awakeners", "Can only resonate with true awakeners.");
        add("container.augment_station.add_augment", "To be added: %s");
        add("container.augment_station.gear_tier_too_high", "Gear tier is too high for the mender item!");
        add("container.augment_station.fragile_warning", "Will make gear fragile! Are you sure?");
        add("container.augment_station.weakened_warning", "Will make item weakened! Are you sure?");
        add("container.augment_station.no_augment_slots", "No augment slots are available!");
        add("container.augment_station.augment_slots_left", "Slots left: %s");
        add("container.augment_station.incompatible_augment", "This augment is incompatible with the gear!");
        add("container.augment_station.required_gear_type", "Required gear type: %s");
        add("button.darkechoes.augstation.initawakening", "Awaken");
        add("button.darkechoes.augstation.initresonance", "Resonate");
        add("button.darkechoes.augstation.initaugment", "Augment");
        add("button.darkechoes.augstation.amend", "Amend");
        add("menu.darkechoes.augment_station.page.augments", "Augments");
        add("menu.darkechoes.augment_station.page.awakening", "Awakening");
        add("menu.darkechoes.augment_station.page.adaptation", "Adaptation");
        add("menu.darkechoes.augment_station.page.augmentation", "Augmenting");
        add("menu.darkechoes.augment_station.page.amendment", "Amendment");

        // Configs
        add("darkechoes.configuration.toolMiningSpeedBonusPerLevel", "Tool Mining Speed Bonus per Level");
        add("darkechoes.configuration.maxToolProgressionLevelTier1", "Max Tool Progression Level: Tier One");
        add("darkechoes.configuration.amendmentPercentageTier1", "Amendment Percentage of Tier One Menders");
        add("darkechoes.configuration.armorHitsPerLevel", "Armor Hits per Level");
        add("darkechoes.configuration.amendmentPercentageTier2", "Amendment Percentage of Tier Two Menders");
        add("darkechoes.configuration.amendmentPercentageTier3", "Amendment Percentage of Tier Three Menders");
        add("darkechoes.configuration.armorReductionBonusPerLevel", "Armor's Damage Reduction Bonus per Level");
        add("darkechoes.configuration.maxToolProgressionLevelTier2", "Max Tool Progression Level: Tier Two");
        add("darkechoes.configuration.maxToolProgressionLevelTier3", "Max Tool Progression Level: Tier Three");
        add("darkechoes.configuration.weaponDamageBonusPerLevel", "Weapon Damage Bonus per Level");
        add("darkechoes.configuration.maxAugmentSlotsTier3", "Max Amount of Augment Slots of Tier Three Gear");
        add("darkechoes.configuration.maxAugmentSlotsTier2", "Max Amount of Augment Slots of Tier Two Gear");
        add("darkechoes.configuration.maxAugmentSlotsTier1", "Max Amount of Augment Slots of Tier One Gear");
        add("darkechoes.configuration.maxMobProgressionLevelTier1", "Max Mob Progression Level: Tier One");
        add("darkechoes.configuration.killsPerLevel", "Kills per Level");
        add("darkechoes.configuration.incomingDamageMultipliers", "Incoming Damage Multipliers");
        add("darkechoes.configuration.maxMobProgressionLevelTier3", "Max Mob Progression Level: Tier Three");
        add("darkechoes.configuration.maxMobProgressionLevelTier2", "Max Mob Progression Level: Tier Two");
        add("darkechoes.configuration.outgoingDamageMultipliers", "Outgoing Damage Multipliers");
        add("darkechoes.configuration.itemDamageMultipliers", "Item Damage Multipliers");
        add("darkechoes.configuration.combat", "Combat");
        add("darkechoes.configuration.common", "Common");
        add("darkechoes.configuration.initialAdaptationSlotsTier3", "Initial Adaptation Slots: Tier Three");
        add("darkechoes.configuration.initialAdaptationSlotsTier2", "Initial Adaptation Slots: Tier Two");
        add("darkechoes.configuration.initialAdaptationSlotsTier1", "Initial Adaptation Slots: Tier One");
        add("darkechoes.configuration.toolBlocksPerLevel", "Tool Blocks Per Level");
        add("darkechoes.configuration.debugLogging", "Debug Logging");
        add("darkechoes.configuration.section.darkechoes.common.toml", "Dark Echoes: Common");
        add("darkechoes.configuration.title", "Dark Echoes Configs");
        add("darkechoes.configuration.common.tooltip", "Common configs, usually composed of initial adaptation slots, max augment slots, and amendment percentage.");
        add("darkechoes.configuration.section.darkechoes.server.toml", "Dark Echoes: Server");
        add("darkechoes.configuration.toolProgression", "Tool Progression");
    }

    public void addAugmentTranslations(Identifier augmentIdentifier, String name) {
        this.add(augmentIdentifier.toLanguageKey("augment"), name);
    }
}
