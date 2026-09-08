package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.sound.SoundEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent;
import net.neoforged.neoforge.event.entity.player.UseItemOnBlockEvent;

@EventBusSubscriber(modid = DarkEchoes.MOD_ID)
public class LimboEvents {
    @SubscribeEvent
    public static void onItemUse(UseItemOnBlockEvent event) {
        ItemStack itemStack = event.getItemStack();
        UseItemOnBlockEvent.UsePhase phase = event.getUsePhase();
        if (AugmentHelper.has(itemStack, AugmentEffectComponents.PREVENT_GEAR_BREAK) && itemStack.getDamageValue() >= itemStack.getMaxDamage() && phase == UseItemOnBlockEvent.UsePhase.ITEM_AFTER_BLOCK) {
            event.setCanceled(true);
        }
    }

//    @SubscribeEvent
//    public static void onLivingSound() {
//
//    }
}
