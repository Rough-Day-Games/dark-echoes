package com.duncanois.darkechoes.combat;

import com.duncanois.darkechoes.DarkEchoes;
import com.duncanois.darkechoes.config.CombatConfig;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public final class CombatRules {
    private static volatile Snapshot snapshot = Snapshot.EMPTY;

    private CombatRules() {
    }

    public static void reload() {
        List<DamageRule> incoming = parseDamageRules(
                CombatConfig.INCOMING_DAMAGE_MULTIPLIERS.get(), "incomingDamageMultipliers");
        List<DamageRule> outgoing = parseDamageRules(
                CombatConfig.OUTGOING_DAMAGE_MULTIPLIERS.get(), "outgoingDamageMultipliers");
        List<ItemRule> items = parseItemRules(
                CombatConfig.ITEM_DAMAGE_MULTIPLIERS.get(), "itemDamageMultipliers");

        snapshot = new Snapshot(incoming, outgoing, items);
        DarkEchoes.LOGGER.info("Loaded Dark Echoes combat rules: {} incoming, {} outgoing, {} item",
                incoming.size(), outgoing.size(), items.size());
    }

    public static double incomingMultiplier(Entity target, DamageSource source) {
        return resolveDamageMultiplier(snapshot.incoming(), target, source);
    }

    public static double outgoingMultiplier(Entity attacker, DamageSource source) {
        return resolveDamageMultiplier(snapshot.outgoing(), attacker, source);
    }

    public static double itemMultiplier(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return 1.0D;
        }

        Identifier itemId = BuiltInRegistries.ITEM.getKey(stack.getItem());
        double result = 1.0D;
        for (ItemRule rule : snapshot.items()) {
            boolean matches = rule.selector().wildcard()
                    || (rule.selector().tag()
                    ? stack.is(TagKey.create(Registries.ITEM, rule.selector().id()))
                    : rule.selector().id().equals(itemId));
            if (matches) {
                result = rule.multiplier();
            }
        }
        return result;
    }

    private static double resolveDamageMultiplier(List<DamageRule> rules, Entity entity, DamageSource source) {
        if (entity == null || source == null) {
            return 1.0D;
        }

        Identifier entityId = BuiltInRegistries.ENTITY_TYPE.getKey(entity.getType());
        Identifier sourceId = source.typeHolder().unwrapKey().map(key -> key.identifier()).orElse(null);
        double result = 1.0D;
        for (DamageRule rule : rules) {
            boolean entityMatches = rule.entity().wildcard()
                    || (rule.entity().tag()
                    ? BuiltInRegistries.ENTITY_TYPE.wrapAsHolder(entity.getType()).is(
                    TagKey.create(Registries.ENTITY_TYPE, rule.entity().id()))
                    : rule.entity().id().equals(entityId));
            boolean sourceMatches = rule.source().wildcard()
                    || (rule.source().tag()
                    ? source.is(TagKey.create(Registries.DAMAGE_TYPE, rule.source().id()))
                    : rule.source().id().equals(sourceId));
            if (entityMatches && sourceMatches) {
                result = rule.multiplier();
            }
        }
        return result;
    }

    private static List<DamageRule> parseDamageRules(List<? extends String> values, String configName) {
        List<DamageRule> rules = new ArrayList<>();
        for (String value : values) {
            int equals = value.lastIndexOf('=');
            int separator = value.indexOf('|');
            if (separator <= 0 || separator >= equals - 1) {
                warnInvalid(configName, value);
                continue;
            }

            Selector entity = parseSelector(value.substring(0, separator));
            Selector source = parseSelector(value.substring(separator + 1, equals));
            Double multiplier = parseMultiplier(value.substring(equals + 1));
            if (entity == null || source == null || multiplier == null) {
                warnInvalid(configName, value);
                continue;
            }
            rules.add(new DamageRule(entity, source, multiplier));
        }
        return List.copyOf(rules);
    }

    private static List<ItemRule> parseItemRules(List<? extends String> values, String configName) {
        List<ItemRule> rules = new ArrayList<>();
        for (String value : values) {
            int equals = value.lastIndexOf('=');
            if (equals <= 0) {
                warnInvalid(configName, value);
                continue;
            }

            Selector item = parseSelector(value.substring(0, equals));
            Double multiplier = parseMultiplier(value.substring(equals + 1));
            if (item == null || multiplier == null) {
                warnInvalid(configName, value);
                continue;
            }
            rules.add(new ItemRule(item, multiplier));
        }
        return List.copyOf(rules);
    }

    private static Selector parseSelector(String value) {
        String text = value.trim();
        if (text.equals("*")) {
            return new Selector(null, false, true);
        }
        boolean tag = text.startsWith("#");
        Identifier id = Identifier.tryParse(tag ? text.substring(1) : text);
        return id == null ? null : new Selector(id, tag, false);
    }

    private static Double parseMultiplier(String value) {
        try {
            double multiplier = Double.parseDouble(value.trim());
            return Double.isFinite(multiplier) && multiplier >= 0.0D ? multiplier : null;
        } catch (NumberFormatException ignored) {
            return null;
        }
    }

    private static void warnInvalid(String configName, String value) {
        DarkEchoes.LOGGER.warn("Ignoring invalid {} rule: {}", configName, value);
    }

    private record Selector(Identifier id, boolean tag, boolean wildcard) {
    }

    private record DamageRule(Selector entity, Selector source, double multiplier) {
    }

    private record ItemRule(Selector selector, double multiplier) {
    }

    private record Snapshot(List<DamageRule> incoming, List<DamageRule> outgoing, List<ItemRule> items) {
        private static final Snapshot EMPTY = new Snapshot(List.of(), List.of(), List.of());
    }
}

