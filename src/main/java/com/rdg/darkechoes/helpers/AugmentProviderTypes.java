package com.rdg.darkechoes.helpers;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

public interface AugmentProviderTypes {
    static MapCodec<? extends AugmentProvider> bootstrap(Registry<MapCodec<? extends AugmentProvider>> registry) {
        return Registry.register(registry, "single", SingleAugment.CODEC);
    }
}
