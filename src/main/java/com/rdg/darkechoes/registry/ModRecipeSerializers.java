package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
//import com.rdg.darkechoes.progression.EchoFusionRecipe;
//import com.rdg.darkechoes.recipes.AugmentRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class ModRecipeSerializers {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, DarkEchoes.MOD_ID);

//    public static final Supplier<RecipeSerializer<AugmentRecipe>> AUGMENTING = SERIALIZERS.register("augmenting", () -> new RecipeSerializer<>(AugmentRecipe.CODEC, AugmentRecipe.STREAM_CODEC));

    private ModRecipeSerializers() {
    }
}
