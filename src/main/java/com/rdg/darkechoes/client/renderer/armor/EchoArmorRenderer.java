package com.rdg.darkechoes.client.renderer.armor;

import com.geckolib.model.DefaultedItemGeoModel;
import com.geckolib.renderer.GeoArmorRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;
import com.rdg.darkechoes.DarkEchoes;
import com.rdg.darkechoes.registry.items.EchoArmorItem;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.item.Item;

import java.util.Set;

public final class EchoArmorRenderer<R extends HumanoidRenderState & GeoRenderState> extends GeoArmorRenderer<EchoArmorItem, R> {
    public EchoArmorRenderer() {
        super(new DefaultedItemGeoModel<>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "armor/echo_armor")));
        withRenderLayer(new AutoGlowingGeoLayer<>(this));
    }

    @Override
    public void addRenderData(EchoArmorItem animatable, GeoArmorRenderer.RenderData relatedObject, R renderState, float partialTick) {
        Set<Item> wornArmor = new ObjectOpenHashSet<>(4);

        if (!(relatedObject.entity() instanceof ArmorStand)) {
            for (EquipmentSlot slot : EquipmentSlot.values()) {
                if (slot.getType() == EquipmentSlot.Type.HUMANOID_ARMOR)
                    wornArmor.add(relatedObject.entity().getItemBySlot(slot).getItem());
            }
        }
    }

}
