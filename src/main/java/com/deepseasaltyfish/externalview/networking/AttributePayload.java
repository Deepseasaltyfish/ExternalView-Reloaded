package com.deepseasaltyfish.externalview.networking;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class AttributePayload {

    private final double value;

    public AttributePayload(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    public static void encode(AttributePayload msg, FriendlyByteBuf buf) {
        buf.writeDouble(msg.value);
    }

    public static AttributePayload decode(FriendlyByteBuf buf) {
        return new AttributePayload(buf.readDouble());
    }

    public static void handle(AttributePayload msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPayloadHandler.handleData(msg, ctx.get());
        });

        ctx.get().setPacketHandled(true);
    }
}