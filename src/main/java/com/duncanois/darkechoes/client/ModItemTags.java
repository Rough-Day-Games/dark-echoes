package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public static TagKey<Item> AWAKENING_ITEMS = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "awakening_items"));
    public static TagKey<Item> AUGMENTABLE_GEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "augmentable_gear"));
    public static TagKey<Item> TIER_ONE_GEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "gear/tier_one"));
    public static TagKey<Item> TIER_TWO_GEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "gear/tier_two"));
    public static TagKey<Item> TIER_THREE_GEAR = TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "gear/tier_three"));
    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DarkEchoes.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AWAKENING_ITEMS).addAll(List.of(
                ModItems.RESONANCE_CRYSTAL.get(),
                Items.ECHO_SHARD
        ));

        tag(TIER_ONE_GEAR).addAll(List.of(
                Items.WOODEN_SWORD,
                Items.WOODEN_PICKAXE,
                Items.WOODEN_AXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_HOE,
                Items.LEATHER_HELMET,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_BOOTS,
                Items.STONE_SWORD,
                Items.STONE_PICKAXE,
                Items.STONE_AXE,
                Items.STONE_SHOVEL,
                Items.STONE_HOE,
                Items.CHAINMAIL_HELMET,
                Items.CHAINMAIL_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS,
                Items.CHAINMAIL_BOOTS,
                Items.COPPER_SWORD,
                Items.COPPER_PICKAXE,
                Items.COPPER_AXE,
                Items.COPPER_SHOVEL,
                Items.COPPER_HOE,
                Items.COPPER_HELMET,
                Items.COPPER_CHESTPLATE,
                Items.COPPER_LEGGINGS,
                Items.COPPER_BOOTS
        ));

        tag(TIER_TWO_GEAR).addAll(List.of(
                Items.IRON_SWORD,
                Items.IRON_PICKAXE,
                Items.IRON_AXE,
                Items.IRON_SHOVEL,
                Items.IRON_HOE,
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS,
                Items.GOLDEN_SWORD,
                Items.GOLDEN_PICKAXE,
                Items.GOLDEN_AXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_HOE,
                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS,
                Items.DIAMOND_SWORD,
                Items.DIAMOND_PICKAXE,
                Items.DIAMOND_AXE,
                Items.DIAMOND_SHOVEL,
                Items.DIAMOND_HOE,
                Items.DIAMOND_HELMET,
                Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS,
                Items.DIAMOND_BOOTS
        ));

        tag(TIER_THREE_GEAR).addAll(List.of(
                Items.NETHERITE_SWORD,
                Items.NETHERITE_PICKAXE,
                Items.NETHERITE_AXE,
                Items.NETHERITE_SHOVEL,
                Items.NETHERITE_HOE,
                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS,
                ModItems.ECHO_SWORD.get(),
                ModItems.ECHO_AXE.get(),
                ModItems.ECHO_HELMET.get(),
                ModItems.ECHO_CHESTPLATE.get(),
                ModItems.ECHO_LEGGINGS.get(),
                ModItems.ECHO_BOOTS.get()
        ));

        tag(AUGMENTABLE_GEAR).addTag(
                TIER_ONE_GEAR
        );
        tag(AUGMENTABLE_GEAR).addTag(
                TIER_TWO_GEAR
        );
        tag(AUGMENTABLE_GEAR).addTag(
                TIER_THREE_GEAR
        );
    }
}
