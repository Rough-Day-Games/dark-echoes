package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.ModItemTags;
import com.rdg.darkechoes.progression.BlockProgression;
import com.rdg.darkechoes.progression.MobProgression;
import com.rdg.darkechoes.registry.items.EchoArmorItem;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Util;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.*;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.EnumMap;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DarkEchoes.MOD_ID);

    public static final ResourceKey<EquipmentAsset> ECHO_ASSET = ResourceKey.create(EquipmentAssets.ROOT_ID, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "echo"));

    public static final ToolMaterial ECHO_TOOL_MATERIAL = new ToolMaterial(
            BlockTags.INCORRECT_FOR_NETHERITE_TOOL,
            2031,
            9.0F,
            4.0F,
            15,
            ModItemTags.REPAIRS_ECHO_GEAR
    );

    public static final ArmorMaterial ECHO_ARMOR_MATERIAL = new ArmorMaterial(
            37, Util.make(new EnumMap<>(ArmorType.class), map -> {
        map.put(ArmorType.BOOTS, 3);
        map.put(ArmorType.LEGGINGS, 6);
        map.put(ArmorType.CHESTPLATE, 8);
        map.put(ArmorType.HELMET, 3);
        map.put(ArmorType.BODY, 19);
    }),
            15,
            SoundEvents.ARMOR_EQUIP_NETHERITE,
            3.0F,
            0.1F,
            ModItemTags.REPAIRS_ECHO_GEAR,
            ECHO_ASSET
    );

    public static final DeferredItem<Item> RESONANCE_CRYSTAL = ITEMS.registerSimpleItem("resonance_crystal");

    public static final DeferredItem<Item> WARDEN_TOTEM = ITEMS.registerSimpleItem("warden_totem");
    public static final DeferredItem<Item> ECHO_UPGRADE_SMITHING_TEMPLATE = ITEMS.registerSimpleItem("echo_upgrade_smithing_template");
    public static final DeferredItem<Item> TIER_ONE_REPAIR_KIT = ITEMS.registerSimpleItem("tier_one_repair_kit");
    public static final DeferredItem<Item> TIER_TWO_REPAIR_KIT = ITEMS.registerSimpleItem("tier_two_repair_kit");
    public static final DeferredItem<Item> TIER_THREE_REPAIR_KIT = ITEMS.registerSimpleItem("tier_three_repair_kit");

    public static final DeferredItem<Item> ECHO_SWORD = ITEMS.registerItem("echo_sword", props -> new Item(props.sword(ECHO_TOOL_MATERIAL, 3, -2.4f).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<AxeItem> ECHO_AXE = ITEMS.registerItem(
            "echo_axe",
            properties -> new AxeItem(ECHO_TOOL_MATERIAL, 5.0F, -3.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<ShovelItem> ECHO_SHOVEL = ITEMS.registerItem(
            "echo_shovel",
            properties -> new ShovelItem(ECHO_TOOL_MATERIAL, 1.5F, -3.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<Item> ECHO_PICKAXE = ITEMS.registerItem("echo_pickaxe", props -> new Item(props.pickaxe(ECHO_TOOL_MATERIAL, 1.0F, -2.8F).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<HoeItem> ECHO_HOE = ITEMS.registerItem(
            "echo_hoe",
            properties -> new HoeItem(ECHO_TOOL_MATERIAL, -4.0F, 0.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<Item> ECHO_SPEAR = ITEMS.registerItem("echo_spear", props -> new Item(props.spear(ECHO_TOOL_MATERIAL, 1.15F, 1.2F, 0.4F, 2.5F, 9.0F, 5.5F, 5.1F, 8.75F, 4.6F).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));

    public static final DeferredItem<Item> ECHO_HELMET = ITEMS.registerItem("echo_helmet", properties -> new EchoArmorItem(ECHO_ARMOR_MATERIAL, ArmorType.HELMET, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<Item> ECHO_CHESTPLATE = ITEMS.registerItem("echo_chestplate", properties -> new EchoArmorItem(ECHO_ARMOR_MATERIAL, ArmorType.CHESTPLATE, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<Item> ECHO_LEGGINGS = ITEMS.registerItem("echo_leggings", properties -> new EchoArmorItem(ECHO_ARMOR_MATERIAL, ArmorType.LEGGINGS, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));
    public static final DeferredItem<Item> ECHO_BOOTS = ITEMS.registerItem("echo_boots", properties -> new EchoArmorItem(ECHO_ARMOR_MATERIAL, ArmorType.BOOTS, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 7)).fireResistant()));

    public static final DeferredItem<BlockItem> T_ONE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_ONE_AUGSTATION);
    public static final DeferredItem<BlockItem> T_TWO_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_TWO_AUGSTATION);
    public static final DeferredItem<BlockItem> T_THREE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_THREE_AUGSTATION);

    private ModItems() {
    }
}
