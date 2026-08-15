package com.rdg.darkechoes.registry.blocks;

import net.minecraft.world.level.block.state.properties.EnumProperty;

public class ModBlockStateProperties {
    public static final EnumProperty<AugStationPiece> AUG_STATION_PIECE;

    static {
        AUG_STATION_PIECE = EnumProperty.create("aug_station_piece", AugStationPiece.class);
    }
}
