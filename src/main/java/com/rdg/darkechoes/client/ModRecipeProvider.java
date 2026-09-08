package com.rdg.darkechoes.client;

//import com.rdg.darkechoes.recipes.AugmentRecipeBuilder;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.ModItems;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider {
    protected ModRecipeProvider(HolderLookup.Provider registries, RecipeOutput output) {
        super(registries, output);
    }

    @Override
    protected void buildRecipes() {
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_SWORD), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_SWORD.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_SWORD.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_PICKAXE), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.TOOLS, ModItems.ECHO_PICKAXE.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_PICKAXE.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_AXE), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.TOOLS, ModItems.ECHO_AXE.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_AXE.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_SHOVEL), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.TOOLS, ModItems.ECHO_SHOVEL.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_SHOVEL.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_HOE), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.TOOLS, ModItems.ECHO_HOE.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_HOE.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_SPEAR), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_SPEAR.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_SPEAR.getRegisteredName());

        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_HELMET), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_HELMET.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_HELMET.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_CHESTPLATE), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_CHESTPLATE.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_CHESTPLATE.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_LEGGINGS), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_LEGGINGS.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_LEGGINGS.getRegisteredName());
        SmithingTransformRecipeBuilder.smithing(Ingredient.of(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE), Ingredient.of(Items.DIAMOND_BOOTS), Ingredient.of(ModItems.RESONANCE_CRYSTAL), RecipeCategory.COMBAT, ModItems.ECHO_BOOTS.get()).unlocks("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())).save(output, ModItems.ECHO_BOOTS.getRegisteredName());

        ShapedRecipeBuilder.shaped(
                        this.registries.lookupOrThrow(Registries.ITEM),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModItems.T_ONE_AUGSTATION.get()
                )
                .pattern("LTL")
                .pattern("LML")
                .pattern("PSP")
                .define('L', ModItemTags.STRIPPED_LOGS)
                .define('T', ItemTags.LOGS)
                .define('M', Items.COPPER_INGOT)
                .define('P', ItemTags.PLANKS)
                .define('S', Items.SMITHING_TABLE)
                .group(DarkEchoes.MOD_ID)
                .unlockedBy("smithing_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.SMITHING_TABLE))
                .save(output);
        ShapedRecipeBuilder.shaped(
                        this.registries.lookupOrThrow(Registries.ITEM),
                        RecipeCategory.BUILDING_BLOCKS,
                        ModItems.T_TWO_AUGSTATION.get()
                )
                .pattern("LTL")
                .pattern("LML")
                .pattern("PSP")
                .define('L', ModItemTags.STRIPPED_LOGS)
                .define('T', ItemTags.LOGS)
                .define('M', Items.DIAMOND)
                .define('P', Items.SMOOTH_STONE)
                .define('S', Items.SMITHING_TABLE)
                .group(DarkEchoes.MOD_ID)
                .unlockedBy("smithing_table", InventoryChangeTrigger.TriggerInstance.hasItems(Items.DIAMOND))
                .save(output);

        ShapedRecipeBuilder.shaped(
                        this.registries.lookupOrThrow(Registries.ITEM),
                        RecipeCategory.MISC,
                        ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get(), 2
                )
                .pattern("DDD")
                .pattern("DTD")
                .pattern("DED")
                .define('D', Items.DEEPSLATE)
                .define('T', ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get())
                .define('E', Items.ECHO_SHARD)
                .group(DarkEchoes.MOD_ID)
                .unlockedBy("echo_smithing_template", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ECHO_UPGRADE_SMITHING_TEMPLATE.get()))
                .save(output);
    }

    public static class Runner extends RecipeProvider.Runner {

        protected Runner(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> registries) {
            super(packOutput, registries);
        }

        @Override
        protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, RecipeOutput output) {
            return new ModRecipeProvider(provider, output);
        }

        @Override
        public String getName() {
            return "Dark Echoes Recipe Provider";
        }
    }
}
