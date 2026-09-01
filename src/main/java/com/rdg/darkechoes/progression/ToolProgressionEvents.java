package com.rdg.darkechoes.progression;

import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.AugmentHelper;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.level.block.BreakBlockEvent;

public final class ToolProgressionEvents {
    private ToolProgressionEvents() {
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack tool = event.getEntity().getMainHandItem();
        if (AugmentHelper.has(tool, AugmentEffectComponents.PREVENT_GEAR_BREAK) && tool.getDamageValue() >= tool.getMaxDamage()) {
            event.setNewSpeed(0);
        }
        event.setNewSpeed((float) (event.getNewSpeed() * ToolProgression.miningSpeedMultiplier(tool, event.getState())));
    }

    @SubscribeEvent
    public static void onBreakBlock(BreakBlockEvent event) {
        if (!event.getLevel().isClientSide() && !event.isCanceled()) {
            ItemStack tool = event.getPlayer().getMainHandItem();
            ToolProgression.recordBlockBreak(tool, event.getState());
        }
    }
}
