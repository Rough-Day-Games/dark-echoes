package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.DarkEchoes;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceWithEnchantedBonusCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class GlobalLootModProvider extends GlobalLootModifierProvider {
    public GlobalLootModProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, DarkEchoes.MOD_ID);
    }

    @Override
    protected void start() {
        this.add(
                "warden_loot_modifier",
                new WardenLootModifier(new LootItemCondition[]{
                        LootItemRandomChanceWithEnchantedBonusCondition.randomChanceAndLootingBoost(registries, 0.2F, 0.1F).build(),
                        LootTableIdCondition.builder(Identifier.parse("minecraft:entities/warden")).build()
                }, LootModifier.DEFAULT_PRIORITY)
        );
    }
}
