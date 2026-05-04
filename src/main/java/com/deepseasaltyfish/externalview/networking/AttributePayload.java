package com.deepseasaltyfish.externalview.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import com.deepseasaltyfish.externalview.ExternalView;

public record AttributePayload(int value) implements CustomPacketPayload {
    public static final Type<AttributePayload> TYPE = new Type<>(ResourceLocation.fromNamespaceAndPath(ExternalView.MOD_ID, "attribute"));
    public static final StreamCodec<FriendlyByteBuf, AttributePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, AttributePayload::value,
            AttributePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}