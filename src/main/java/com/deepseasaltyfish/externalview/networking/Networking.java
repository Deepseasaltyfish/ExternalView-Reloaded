package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.ExternalView;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

public class Networking {
    public static void register(final RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar(ExternalView.MOD_ID).optional();
        registrar.playToServer(
                AttributePayload.TYPE,
                AttributePayload.CODEC,
                ServerPayloadHandler::handleData
        );
        ExternalView.LOGGER.info("Network payload registered");
    }
}