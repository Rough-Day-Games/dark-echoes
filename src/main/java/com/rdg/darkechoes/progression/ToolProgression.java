package com.rdg.darkechoes.progression;

import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.config.CombatConfig;
import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public final class ToolProgression {
    private static final TagKey<Item> PICKAXES = itemTag("tool_progression/pickaxes");
    private static final TagKey<Item> AXES = itemTag("tool_progression/axes");
    private static final TagKey<Item> SHOVELS = itemTag("tool_progression/shovels");
    private static final TagKey<Item> HOES = itemTag("tool_progression/hoes");

    private ToolProgression() {
    }

    public static boolean isProgressionTool(ItemStack stack) {
        return Progression.isAwakenedTool(stack) && kind(stack) != ToolKind.NONE;
    }

    public static double miningSpeedMultiplier(ItemStack tool, BlockState state) {
        if (!canProgressAgainst(tool, state)) {
            return 1.0D;
        }
        BlockProgression progression = data(tool);
        String blockId = blockId(state);
        if (!progression.target().equals(blockId)) {
            return 1.0D;
        }
        int level = progression.level(
                CombatConfig.TOOL_BLOCKS_PER_LEVEL.getAsInt(),
                CombatConfig.MAX_TOOL_PROGRESSION_LEVEL.getAsInt());
        return 1.0D + level * CombatConfig.TOOL_MINING_SPEED_BONUS_PER_LEVEL.getAsDouble();
    }

    public static void recordBlockBreak(ItemStack tool, BlockState state) {
        if (!canProgressAgainst(tool, state)) {
            return;
        }
        BlockProgression current = data(tool);
        BlockProgression updated = current.advance(
                blockId(state),
                CombatConfig.TOOL_BLOCKS_PER_LEVEL.getAsInt(),
                CombatConfig.MAX_TOOL_PROGRESSION_LEVEL.getAsInt());
        if (!updated.equals(current)) {
            tool.set(ModDataComponents.BLOCK_PROGRESSION.get(), updated);
        }
    }

    public static void appendTooltip(ItemStack tool, List<Component> tooltip) {
        BlockProgression progression = data(tool);
        int blocksPerLevel = CombatConfig.TOOL_BLOCKS_PER_LEVEL.getAsInt();
        int maxLevel = CombatConfig.MAX_TOOL_PROGRESSION_LEVEL.getAsInt();
        if (progression.locked()) {
            int level = progression.level(blocksPerLevel, maxLevel);
            long bonus = Math.round(level * CombatConfig.TOOL_MINING_SPEED_BONUS_PER_LEVEL.getAsDouble() * 100.0D);
            Component targetName = blockName(progression.target());
            if (level >= maxLevel) {
                tooltip.add(Component.translatable("tooltip.darkechoes.tool_progression_max", targetName, level, bonus));
            } else {
                int remaining = blocksPerLevel - progression.actions() % blocksPerLevel;
                tooltip.add(Component.translatable(
                        "tooltip.darkechoes.tool_progression",
                        targetName, level, bonus, level + 1, remaining, blockName(remaining)));
            }
        } else if (!progression.pendingTarget().isEmpty()) {
            int remaining = blocksPerLevel - progression.pendingActions();
            tooltip.add(Component.translatable(
                    "tooltip.darkechoes.tool_progression_pending",
                    blockName(progression.pendingTarget()), remaining, blockName(remaining)));
        }
    }

    private static boolean canProgressAgainst(ItemStack tool, BlockState state) {
        return isProgressionTool(tool) && kind(tool).matches(state);
    }

    private static BlockProgression data(ItemStack stack) {
        return stack.getOrDefault(ModDataComponents.BLOCK_PROGRESSION.get(), BlockProgression.EMPTY);
    }

    private static String blockId(BlockState state) {
        return BuiltInRegistries.BLOCK.getKey(state.getBlock()).toString();
    }

    private static Component blockName(String id) {
        Identifier identifier = Identifier.tryParse(id);
        Block block = identifier == null ? null : BuiltInRegistries.BLOCK.getValue(identifier);
        return block == null ? Component.literal(id) : block.getName();
    }

    private static Component blockName(int count) {
        return Component.translatable("tooltip.darkechoes.block." + (count == 1 ? "one" : "many"));
    }

    private static ToolKind kind(ItemStack stack) {
        if (stack.is(PICKAXES)) {
            return ToolKind.PICKAXE;
        }
        if (stack.is(AXES)) {
            return ToolKind.AXE;
        }
        if (stack.is(SHOVELS)) {
            return ToolKind.SHOVEL;
        }
        if (stack.is(HOES)) {
            return ToolKind.HOE;
        }
        return ToolKind.NONE;
    }

    private static TagKey<Item> itemTag(String path) {
        return TagKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, path));
    }

    private enum ToolKind {
        PICKAXE(BlockTags.MINEABLE_WITH_PICKAXE),
        AXE(BlockTags.MINEABLE_WITH_AXE),
        SHOVEL(BlockTags.MINEABLE_WITH_SHOVEL),
        HOE(BlockTags.MINEABLE_WITH_HOE),
        NONE(null);

        private final TagKey<Block> blocks;

        ToolKind(TagKey<Block> blocks) {
            this.blocks = blocks;
        }

        private boolean matches(BlockState state) {
            return blocks != null && state.is(blocks);
        }
    }
}
