package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.ExternalView;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class Networking {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(ExternalView.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION,
                    NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals),// client accepts
                    NetworkRegistry.acceptMissingOr(PROTOCOL_VERSION::equals)// server accepts
            );

    private static int packetId = 0;

    public static void register() {
        CHANNEL.messageBuilder(AttributePayload.class, packetId++)
                .encoder(AttributePayload::encode)
                .decoder(AttributePayload::decode)
                .consumerMainThread(AttributePayload::handle)
                .add();

        ExternalView.LOGGER.info("Network registered");
    }
}