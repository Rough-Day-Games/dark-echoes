package com.rdg.darkechoes.registry;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.client.WardenLootModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModCodec {
    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> GLOBAL_LOOT_MOD_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, DarkEchoes.MOD_ID);

    public static final Supplier<MapCodec<WardenLootModifier>> WARDEN_LOOT_MODIFIER =
            GLOBAL_LOOT_MOD_SERIALIZERS.register("warden_loot_modifier", () -> WardenLootModifier.CODEC);
}
