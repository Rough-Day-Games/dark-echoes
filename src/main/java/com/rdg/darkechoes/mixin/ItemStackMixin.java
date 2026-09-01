package com.rdg.darkechoes.mixin;

import com.rdg.darkechoes.helpers.AugmentEffectComponents;
import com.rdg.darkechoes.helpers.AugmentHelper;
import com.rdg.darkechoes.registry.ModDataComponents;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.PatchedDataComponentMap;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TooltipProvider;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Consumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow
    public abstract boolean isBroken();

    @Shadow
    public abstract Item getItem();

    @Shadow
    public abstract void shrink(int amount);

    @Shadow
    public abstract void setDamageValue(int value);

    @Shadow
    public abstract ItemStack copy();

    @Shadow
    @Final
    private PatchedDataComponentMap components;

    @Shadow
    public abstract <T extends TooltipProvider> void addToTooltip(DataComponentType<T> type, Item.TooltipContext context, TooltipDisplay display, Consumer<Component> consumer, TooltipFlag flag);

    @Shadow
    public abstract int getMaxDamage();

    @Inject(method = "applyDamage(ILnet/minecraft/world/entity/LivingEntity;Ljava/util/function/Consumer;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setDamageValue(I)V"), cancellable = true)
    private void onApplyDamage(int newDamage, @Nullable LivingEntity player, Consumer<Item> onBreak, CallbackInfo ci) {
        if (this.components.has(ModDataComponents.FRAGILE)) {
            this.setDamageValue(newDamage + 3);
        } else if (this.components.has(ModDataComponents.WEAKENED)) {
            this.setDamageValue(newDamage + 1);
        } else {
            this.setDamageValue(newDamage);
        }
        if (this.isBroken() && AugmentHelper.has(this.copy(), AugmentEffectComponents.PREVENT_GEAR_BREAK)) {
            this.setDamageValue(this.getMaxDamage());
        } else if (this.isBroken()) {
            Item item = this.getItem();
            this.shrink(1);
            onBreak.accept(item);
        }
        ci.cancel();
    }

}
