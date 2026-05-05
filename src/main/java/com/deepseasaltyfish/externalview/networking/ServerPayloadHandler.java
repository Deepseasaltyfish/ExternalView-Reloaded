package com.deepseasaltyfish.externalview.networking;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class ServerPayloadHandler {
    public static void handleData(final AttributePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player instanceof ServerPlayer serverPlayer) {
                if (serverPlayer.hasPermissions(2)) {//op permission
                    float value = payload.value();
                    serverPlayer.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).setBaseValue(value);
                    serverPlayer.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(value);
                }
            }
        });
    }
}