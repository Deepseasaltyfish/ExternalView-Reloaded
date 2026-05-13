package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.ExternalView;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Networking {

    private static final String PROTOCOL_VERSION = "1";

    public static final SimpleChannel CHANNEL =
            NetworkRegistry.newSimpleChannel(
                    new ResourceLocation(ExternalView.MOD_ID, "main"),
                    () -> PROTOCOL_VERSION,

                    serverVersion ->
                            serverVersion.equals(PROTOCOL_VERSION)
                                    || serverVersion.equals(NetworkRegistry.ABSENT)
                                    || serverVersion.equals(NetworkRegistry.ACCEPTVANILLA),

                    clientVersion ->
                            clientVersion.equals(PROTOCOL_VERSION)
                                    || clientVersion.equals(NetworkRegistry.ABSENT)
                                    || clientVersion.equals(NetworkRegistry.ACCEPTVANILLA)
            );

    private static int packetId = 0;

    public static void register() {

        CHANNEL.registerMessage(
                packetId++,
                AttributePayload.class,

                AttributePayload::encode,
                AttributePayload::decode,
                AttributePayload::handle
        );

        ExternalView.LOGGER.info("Network registered");
    }
}