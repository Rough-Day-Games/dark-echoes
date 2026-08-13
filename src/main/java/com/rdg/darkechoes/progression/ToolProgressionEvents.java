package com.rdg.darkechoes.progression;

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
        event.setNewSpeed((float) (event.getNewSpeed() * ToolProgression.miningSpeedMultiplier(tool, event.getState())));
    }

    @SubscribeEvent
    public static void onBreakBlock(BreakBlockEvent event) {
        if (!event.getLevel().isClientSide() && !event.isCanceled()) {
            ToolProgression.recordBlockBreak(event.getPlayer().getMainHandItem(), event.getState());
        }
    }
}
