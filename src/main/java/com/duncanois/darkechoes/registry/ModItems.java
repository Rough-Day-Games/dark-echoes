package com.duncanois.darkechoes.registry;

import com.duncanois.darkechoes.DarkEchoes;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DarkEchoes.MOD_ID);

    public static final DeferredItem<Item> RESONANCE_CRYSTAL = ITEMS.registerSimpleItem("resonance_crystal");
    public static final DeferredItem<Item> ECHO_DIAMOND_SWORD = ITEMS.registerSimpleItem(
            "echo_diamond_sword", properties -> echo(properties).sword(ToolMaterial.DIAMOND, 3.0F, -2.4F));
    public static final DeferredItem<AxeItem> ECHO_DIAMOND_AXE = ITEMS.registerItem(
            "echo_diamond_axe",
            properties -> new AxeItem(ToolMaterial.DIAMOND, 5.0F, -3.0F, properties),
            ModItems::echo);
    public static final DeferredItem<Item> ECHO_DIAMOND_HELMET = ITEMS.registerSimpleItem(
            "echo_diamond_helmet", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.HELMET));
    public static final DeferredItem<Item> ECHO_DIAMOND_CHESTPLATE = ITEMS.registerSimpleItem(
            "echo_diamond_chestplate", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.CHESTPLATE));
    public static final DeferredItem<Item> ECHO_DIAMOND_LEGGINGS = ITEMS.registerSimpleItem(
            "echo_diamond_leggings", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.LEGGINGS));
    public static final DeferredItem<Item> ECHO_DIAMOND_BOOTS = ITEMS.registerSimpleItem(
            "echo_diamond_boots", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.BOOTS));

    public static final DeferredItem<BlockItem> T_ONE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_ONE_AUGSTATION);
    public static final DeferredItem<BlockItem> T_TWO_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_TWO_AUGSTATION);
    public static final DeferredItem<BlockItem> T_THREE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_THREE_AUGSTATION);

    private ModItems() {
    }

    private static Item.Properties echo(Item.Properties properties) {
        return properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }
}
