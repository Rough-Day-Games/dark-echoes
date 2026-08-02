package com.duncanois.darkechoes.progression;

import com.duncanois.darkechoes.registry.ModItems;
import com.duncanois.darkechoes.registry.ModRecipes;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.PlacementInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleSmithingRecipe;
import net.minecraft.world.item.crafting.SmithingRecipeInput;
import net.minecraft.world.item.crafting.display.RecipeDisplay;
import net.minecraft.world.item.crafting.display.SlotDisplay;
import net.minecraft.world.item.crafting.display.SmithingRecipeDisplay;

import java.util.List;
import java.util.Optional;

public final class EchoFusionRecipe extends SimpleSmithingRecipe {
    public static final EchoFusionRecipe INSTANCE = new EchoFusionRecipe();
    public static final MapCodec<EchoFusionRecipe> MAP_CODEC = MapCodec.unit(INSTANCE);
    public static final StreamCodec<RegistryFriendlyByteBuf, EchoFusionRecipe> STREAM_CODEC = StreamCodec.unit(INSTANCE);

    private static final Ingredient BASE = Ingredient.of(
            Items.DIAMOND_SWORD,
            Items.DIAMOND_AXE,
            Items.DIAMOND_HELMET,
            Items.DIAMOND_CHESTPLATE,
            Items.DIAMOND_LEGGINGS,
            Items.DIAMOND_BOOTS);

    private EchoFusionRecipe() {
        super(new Recipe.CommonInfo(true));
    }

    @Override
    public boolean matches(SmithingRecipeInput input, net.minecraft.world.level.Level level) {
        return input.template().isEmpty()
                && BASE.test(input.base())
                && input.addition().is(ModItems.RESONANCE_CRYSTAL.get());
    }

    @Override
    public ItemStack assemble(SmithingRecipeInput input) {
        return Progression.fuse(input.base());
    }

    @Override
    public Optional<Ingredient> templateIngredient() {
        return Optional.empty();
    }

    @Override
    public Ingredient baseIngredient() {
        return BASE;
    }

    @Override
    public Optional<Ingredient> additionIngredient() {
        return Optional.of(Ingredient.of(ModItems.RESONANCE_CRYSTAL.get()));
    }

    @Override
    public RecipeSerializer<EchoFusionRecipe> getSerializer() {
        return ModRecipes.ECHO_FUSION.get();
    }

    @Override
    protected PlacementInfo createPlacementInfo() {
        return PlacementInfo.createFromOptionals(
                List.of(Optional.empty(), Optional.of(BASE), additionIngredient()));
    }

    @Override
    public List<RecipeDisplay> display() {
        ItemStack result = Progression.fuse(new ItemStack(Items.DIAMOND_SWORD));
        return List.of(new SmithingRecipeDisplay(
                SlotDisplay.Empty.INSTANCE,
                BASE.display(),
                additionIngredient().orElseThrow().display(),
                new SlotDisplay.ItemStackSlotDisplay(ItemStackTemplate.fromNonEmptyStack(result)),
                new SlotDisplay.ItemSlotDisplay(Items.SMITHING_TABLE)));
    }
}
