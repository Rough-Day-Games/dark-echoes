package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.progression.BlockProgression;
import com.rdg.darkechoes.progression.MobProgression;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.item.equipment.ArmorMaterials;
import net.minecraft.world.item.equipment.ArmorType;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(DarkEchoes.MOD_ID);

    public static final DeferredItem<Item> RESONANCE_CRYSTAL = ITEMS.registerSimpleItem("resonance_crystal");
    public static final DeferredItem<Item> ECHO_SWORD = ITEMS.registerSimpleItem(
            "echo_sword", properties -> echo(properties).sword(ToolMaterial.DIAMOND, 3.0F, -2.4F).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 10)));
    public static final DeferredItem<AxeItem> ECHO_AXE = ITEMS.registerItem(
            "echo_axe",
            properties -> new AxeItem(ToolMaterial.DIAMOND, 5.0F, -3.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 10))),
            ModItems::echo);
    public static final DeferredItem<ShovelItem> ECHO_SHOVEL = ITEMS.registerItem(
            "echo_shovel",
            properties -> new ShovelItem(ToolMaterial.DIAMOND, 5.0F, -3.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 10))),
            ModItems::echo);
    public static final DeferredItem<Item> ECHO_PICKAXE = ITEMS.registerSimpleItem(
            "echo_pickaxe", properties -> echo(properties).pickaxe(ToolMaterial.DIAMOND, 3.0F, -2.4F).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 10)));
    public static final DeferredItem<HoeItem> ECHO_HOE = ITEMS.registerItem(
            "echo_hoe",
            properties -> new HoeItem(ToolMaterial.DIAMOND, 5.0F, -3.0F, properties.component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.BLOCK_PROGRESSION, new BlockProgression("", 0, "", 0, 10))),
            ModItems::echo);

    public static final DeferredItem<Item> ECHO_HELMET = ITEMS.registerSimpleItem(
            "echo_helmet", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.HELMET).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 10)));
    public static final DeferredItem<Item> ECHO_CHESTPLATE = ITEMS.registerSimpleItem(
            "echo_chestplate", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.CHESTPLATE).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 10)));
    public static final DeferredItem<Item> ECHO_LEGGINGS = ITEMS.registerSimpleItem(
            "echo_leggings", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.LEGGINGS).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 10)));
    public static final DeferredItem<Item> ECHO_BOOTS = ITEMS.registerSimpleItem(
            "echo_boots", properties -> echo(properties).humanoidArmor(ArmorMaterials.DIAMOND, ArmorType.BOOTS).component(ModDataComponents.AUGMENT_SLOTS, 1).component(ModDataComponents.MOB_PROGRESSION, new MobProgression("", 0, "", 0, 10)));

    public static final DeferredItem<BlockItem> T_ONE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_ONE_AUGSTATION);
    public static final DeferredItem<BlockItem> T_TWO_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_TWO_AUGSTATION);
    public static final DeferredItem<BlockItem> T_THREE_AUGSTATION = ITEMS.registerSimpleBlockItem(ModBlocks.T_THREE_AUGSTATION);

    private ModItems() {
    }

    private static Item.Properties echo(Item.Properties properties) {
        return properties.component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true);
    }
}
