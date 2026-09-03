package com.rdg.darkechoes.client;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.progression.ToolProgression;
import com.rdg.darkechoes.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.data.ItemTagsProvider;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModItemTags extends ItemTagsProvider {
    public static TagKey<Item> AWAKENING_ITEMS = create("awakening_items");
    public static TagKey<Item> AUGMENT_SOURCES = create("augment_sources");
    public static TagKey<Item> ECHO_SENSE_COMPATIBLE = create("echo_sense_compatible");
    public static TagKey<Item> MALLEABLE_SOURCES = create("augment_sources/malleable");
    public static TagKey<Item> MAGIC_REBORN_SOURCES = create("augment_sources/magic_reborn");
    public static TagKey<Item> FLEXIBLE_SOURCES = create("flexible_sources");
    public static TagKey<Item> EARTH_SHATTERER_SOURCES = create("earth_shatterer_sources");
    public static TagKey<Item> HEAVENS_AUGMENT_SOURCES = create("heavens_augment_sources");
    public static TagKey<Item> ECHO_SENSE_SOURCES = create("echo_sense_sources");
    public static TagKey<Item> LOW_GRAVITY_SOURCES = create("low_gravity_sources");

    public static TagKey<Item> AUGMENTABLE_GEAR = create("augmentable");
    public static TagKey<Item> AUGMENTABLE_TOOL = create("augmentable/tool");
    public static TagKey<Item> AUGMENTABLE_WEAPON = create("augmentable/weapon");
    public static TagKey<Item> AUGMENTABLE_ARMOR = create("augmentable/armor");
    public static TagKey<Item> AUGMENTABLE_CHESTPLATES = create("augmentable/armor/chestplates");
    public static TagKey<Item> AUGMENTABLE_BOOTS = create("augmentable/armor/boots");

    public static TagKey<Item> TIER_ONE_ARMOR = create("armor/tier_one");
    public static TagKey<Item> TIER_TWO_ARMOR = create("armor/tier_two");
    public static TagKey<Item> TIER_THREE_ARMOR = create("armor/tier_three");

    public static TagKey<Item> TIER_ONE_TOOL = create("tool/tier_one");
    public static TagKey<Item> TIER_ONE_WEAPON = create("weapon/tier_one");
    public static TagKey<Item> TIER_TWO_TOOL = create("tool/tier_two");
    public static TagKey<Item> TIER_TWO_WEAPON = create("weapon/tier_two");
    public static TagKey<Item> TIER_THREE_TOOL = create("tool/tier_three");
    public static TagKey<Item> TIER_THREE_WEAPON = create("weapon/tier_three");

    public static TagKey<Item> TIER_ONE_GEAR = create("gear/tier_one");
    public static TagKey<Item> TIER_TWO_GEAR = create("gear/tier_two");
    public static TagKey<Item> TIER_THREE_GEAR = create("gear/tier_three");

    public ModItemTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, DarkEchoes.MOD_ID);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(AWAKENING_ITEMS).addAll(List.of(
                ModItems.RESONANCE_CRYSTAL.get(),
                Items.ECHO_SHARD
        ));

        tag(AUGMENT_SOURCES).addAll(List.of(
                Items.WRITABLE_BOOK,
                Items.ECHO_SHARD
        ));

        tag(ECHO_SENSE_COMPATIBLE).add(
                ModItems.ECHO_HELMET.get()
        );

        tag(MALLEABLE_SOURCES).add(Items.WRITABLE_BOOK);
        tag(MAGIC_REBORN_SOURCES).add(Items.ECHO_SHARD);

        tag(AUGMENTABLE_CHESTPLATES).addAll(List.of(
                Items.LEATHER_CHESTPLATE,
                Items.COPPER_CHESTPLATE,
                Items.CHAINMAIL_CHESTPLATE,
                Items.IRON_CHESTPLATE,
                Items.GOLDEN_CHESTPLATE,
                Items.DIAMOND_CHESTPLATE,
                Items.NETHERITE_CHESTPLATE,
                ModItems.ECHO_CHESTPLATE.get()
        ));

        tag(AUGMENTABLE_BOOTS).addAll(List.of(
                Items.LEATHER_BOOTS,
                Items.COPPER_BOOTS,
                Items.CHAINMAIL_BOOTS,
                Items.IRON_BOOTS,
                Items.GOLDEN_BOOTS,
                Items.DIAMOND_BOOTS,
                Items.NETHERITE_BOOTS,
                ModItems.ECHO_BOOTS.get()
        ));

        tag(TIER_ONE_ARMOR).addAll(List.of(
                Items.LEATHER_HELMET,
                Items.LEATHER_CHESTPLATE,
                Items.LEATHER_LEGGINGS,
                Items.LEATHER_BOOTS,
                Items.CHAINMAIL_HELMET,
                Items.CHAINMAIL_CHESTPLATE,
                Items.CHAINMAIL_LEGGINGS,
                Items.CHAINMAIL_BOOTS,
                Items.COPPER_HELMET,
                Items.COPPER_CHESTPLATE,
                Items.COPPER_LEGGINGS,
                Items.COPPER_BOOTS
        ));

        tag(TIER_TWO_ARMOR).addAll(List.of(
                Items.IRON_HELMET,
                Items.IRON_CHESTPLATE,
                Items.IRON_LEGGINGS,
                Items.IRON_BOOTS,
                Items.GOLDEN_HELMET,
                Items.GOLDEN_CHESTPLATE,
                Items.GOLDEN_LEGGINGS,
                Items.GOLDEN_BOOTS,
                Items.DIAMOND_HELMET,
                Items.DIAMOND_CHESTPLATE,
                Items.DIAMOND_LEGGINGS,
                Items.DIAMOND_BOOTS
        ));

        tag(TIER_THREE_ARMOR).addAll(List.of(
                Items.NETHERITE_HELMET,
                Items.NETHERITE_CHESTPLATE,
                Items.NETHERITE_LEGGINGS,
                Items.NETHERITE_BOOTS,
                ModItems.ECHO_HELMET.get(),
                ModItems.ECHO_CHESTPLATE.get(),
                ModItems.ECHO_LEGGINGS.get(),
                ModItems.ECHO_BOOTS.get()
        ));

        tag(TIER_ONE_TOOL).addAll(List.of(
                Items.WOODEN_PICKAXE,
                Items.WOODEN_AXE,
                Items.WOODEN_SHOVEL,
                Items.WOODEN_HOE,
                Items.STONE_PICKAXE,
                Items.STONE_AXE,
                Items.STONE_SHOVEL,
                Items.STONE_HOE,
                Items.COPPER_PICKAXE,
                Items.COPPER_AXE,
                Items.COPPER_SHOVEL,
                Items.COPPER_HOE
        ));

        tag(TIER_TWO_TOOL).addAll(List.of(
                Items.IRON_PICKAXE,
                Items.IRON_AXE,
                Items.IRON_SHOVEL,
                Items.IRON_HOE,
                Items.GOLDEN_PICKAXE,
                Items.GOLDEN_AXE,
                Items.GOLDEN_SHOVEL,
                Items.GOLDEN_HOE,
                Items.DIAMOND_PICKAXE,
                Items.DIAMOND_AXE,
                Items.DIAMOND_SHOVEL,
                Items.DIAMOND_HOE
        ));

        tag(TIER_THREE_TOOL).addAll(List.of(
                Items.NETHERITE_PICKAXE,
                Items.NETHERITE_AXE,
                Items.NETHERITE_SHOVEL,
                Items.NETHERITE_HOE,
                ModItems.ECHO_AXE.get(),
                ModItems.ECHO_PICKAXE.get(),
                ModItems.ECHO_SHOVEL.get(),
                ModItems.ECHO_HOE.get()
        ));

        tag(TIER_ONE_WEAPON).addAll(List.of(
                Items.WOODEN_SWORD,
                Items.STONE_SWORD,
                Items.COPPER_SWORD
        ));

        tag(TIER_TWO_WEAPON).addAll(List.of(
                Items.IRON_SWORD,
                Items.GOLDEN_SWORD,
                Items.DIAMOND_SWORD
        ));

        tag(TIER_THREE_WEAPON).addAll(List.of(
                Items.NETHERITE_SWORD,
                ModItems.ECHO_SWORD.get()
        ));

        tag(AUGMENTABLE_ARMOR).addTags(
                TIER_ONE_ARMOR,
                TIER_TWO_ARMOR,
                TIER_THREE_ARMOR
        );
//
//        tag(AUGMENTABLE_ARMOR).addTag(
//                TIER_ONE_ARMOR
//        );
//        tag(AUGMENTABLE_ARMOR).addTag(
//                TIER_TWO_ARMOR
//        );
//        tag(AUGMENTABLE_ARMOR).addTag(
//                TIER_THREE_ARMOR
//        );

        tag(AUGMENTABLE_TOOL).addTags(
                TIER_ONE_TOOL,
                TIER_TWO_TOOL,
                TIER_THREE_TOOL
        );

        tag(AUGMENTABLE_WEAPON).addTags(
                TIER_ONE_WEAPON,
                TIER_TWO_WEAPON,
                TIER_THREE_WEAPON
        );
//        tag(AUGMENTABLE_TOOL).addTag(
//                TIER_TWO_TOOL
//        );
//        tag(AUGMENTABLE_TOOL).addTag(
//                TIER_THREE_TOOL
//        );

        tag(TIER_ONE_GEAR).addTags(
                TIER_ONE_ARMOR,
                TIER_ONE_TOOL,
                TIER_ONE_WEAPON
        );
//        tag(TIER_ONE_GEAR).addTag(
//                TIER_ONE_TOOL
//        );
        tag(TIER_TWO_GEAR).addTags(
                TIER_TWO_ARMOR,
                TIER_TWO_TOOL,
                TIER_TWO_WEAPON
        );
//        tag(TIER_TWO_GEAR).addTag(
//                TIER_TWO_TOOL
//        );
        tag(TIER_THREE_GEAR).addTags(
                TIER_THREE_ARMOR,
                TIER_THREE_TOOL,
                TIER_THREE_WEAPON
        );
//        tag(TIER_THREE_GEAR).addTag(
//                TIER_THREE_TOOL
//        );

        tag(ToolProgression.PICKAXES).addTag(
                ItemTags.PICKAXES
        );

        tag(ToolProgression.SHOVELS).addTag(
                ItemTags.SHOVELS
        );

        tag(ToolProgression.AXES).addTag(
                ItemTags.AXES
        );

        tag(ToolProgression.HOES).addTag(
                ItemTags.HOES
        );

        tag(AUGMENTABLE_GEAR).addTags(
                AUGMENTABLE_ARMOR,
                AUGMENTABLE_TOOL,
                AUGMENTABLE_WEAPON
        );
//        tag(AUGMENTABLE_GEAR).addTag(
//                AUGMENTABLE_TOOL
//        );

    }
        public static TagKey<Item> create(String key) {
            return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, key));
        }
}
