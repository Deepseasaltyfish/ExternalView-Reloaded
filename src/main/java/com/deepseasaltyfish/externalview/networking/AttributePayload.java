package com.deepseasaltyfish.externalview.networking;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class AttributePayload {

    private final double value;

    public AttributePayload(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

    // encode
    public static void encode(AttributePayload msg, PacketBuffer buf) {
        buf.writeDouble(msg.value);
    }

    // decode
    public static AttributePayload decode(PacketBuffer buf) {
        return new AttributePayload(buf.readDouble());
    }

    // handle
    public static void handle(AttributePayload msg, Supplier<NetworkEvent.Context> ctxSupplier) {

        NetworkEvent.Context ctx = ctxSupplier.get();

        ctx.enqueueWork(() -> {
            ServerPayloadHandler.handleData(msg, ctx);
        });

        ctx.setPacketHandled(true);
    }
}