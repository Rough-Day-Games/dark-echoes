package com.duncanois.darkechoes.client;

import com.duncanois.darkechoes.registry.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.LootTableSubProvider;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.function.BiConsumer;

public class LootTables implements LootTableSubProvider {
    public LootTables(HolderLookup.Provider provider) {

    }

    @Override
    public void generate(BiConsumer<ResourceKey<LootTable>, LootTable.Builder> biConsumer) {
        biConsumer.accept(
                ResourceKey.create(
                        Registries.LOOT_TABLE,
                        Identifier.fromNamespaceAndPath("minecraft", "entities/warden")
                ),
                LootTable.lootTable()
                        .withPool(LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(Items.SCULK_CATALYST))
                        )
                        .withPool(LootPool.lootPool()
//                                TODO should the resonance crystal drop rate increase with the looting enchantment? yes
                                .when(LootItemRandomChanceCondition.randomChance(0.1F))
                                .setRolls(ConstantValue.exactly(1))
                                .add(LootItem.lootTableItem(ModItems.RESONANCE_CRYSTAL))
                        )
        );
    }
}
