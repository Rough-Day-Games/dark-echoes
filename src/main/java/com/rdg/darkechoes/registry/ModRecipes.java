package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
//import com.rdg.darkechoes.progression.EchoFusionRecipe;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(Registries.RECIPE_SERIALIZER, DarkEchoes.MOD_ID);

//    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<EchoFusionRecipe>> ECHO_FUSION =
//            SERIALIZERS.register("echo_fusion",
//                    () -> new RecipeSerializer<>(EchoFusionRecipe.MAP_CODEC, EchoFusionRecipe.STREAM_CODEC));

    private ModRecipes() {
    }
}
