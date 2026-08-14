package com.rdg.darkechoes.helpers;

import com.rdg.darkechoes.DarkEchoes;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public record AugStationPageListener(int pageIndex) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<AugStationPageListener> TYPE = new CustomPacketPayload.Type<AugStationPageListener>(Identifier.fromNamespaceAndPath(DarkEchoes.MOD_ID, "aug_station_page_index"));

    public static final StreamCodec<ByteBuf, AugStationPageListener> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, AugStationPageListener::pageIndex, AugStationPageListener::new
    );

    public static void handle(final AugStationPageListener packet, IPayloadContext context) {
        context.player().containerMenu.setData(0, packet.pageIndex);
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
