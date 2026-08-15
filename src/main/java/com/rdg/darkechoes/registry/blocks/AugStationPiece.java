package com.rdg.darkechoes.registry.blocks;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public enum AugStationPiece implements StringRepresentable {
    BOTTOM_LEFT(Direction.DOWN, Direction.WEST),
    BOTTOM_RIGHT(Direction.DOWN, Direction.EAST),
    TOP_LEFT(Direction.UP, Direction.WEST),
    TOP_RIGHT(Direction.UP, Direction.EAST);

    private final List<Direction> directionsToOther;

    AugStationPiece(Direction yAxis, Direction xAxis) {
        this.directionsToOther = List.of(yAxis, xAxis);
    }

    public List<Direction> getDirectionsToOther() {
        return directionsToOther;
    }

    @Override
    public String getSerializedName() {
        return switch (this) {
            case BOTTOM_LEFT -> "bottom_left";
            case BOTTOM_RIGHT -> "bottom_right";
            case TOP_LEFT -> "top_left";
            case TOP_RIGHT -> "top_right";
        };
    }


    @Override
    public String toString() {
        return this.getSerializedName();
    }

    public AugStationPiece getAugStationBottomPiece() {
        return this == BOTTOM_LEFT ? BOTTOM_RIGHT : BOTTOM_LEFT;
    }

    public AugStationPiece getAugStationTopPiece() {
        return this == TOP_LEFT ? TOP_RIGHT : TOP_LEFT;
    }
}
