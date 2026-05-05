package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.event.client.ReachAdjustHandler;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import net.neoforged.neoforge.server.permission.PermissionAPI;

public class ServerPayloadHandler {
    public static void handleData(final AttributePayload payload, final IPayloadContext context) {
        context.enqueueWork(() -> {
            var player = context.player();
            if (player instanceof ServerPlayer serverPlayer) {
                boolean allowed = PermissionAPI.getPermission(serverPlayer, ModPermissions.REACH);

                if (allowed) {
                    double value = payload.value();
                    serverPlayer.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).setBaseValue(value);
                    serverPlayer.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(value);
                } else {
                    serverPlayer.getAttribute(Attributes.BLOCK_INTERACTION_RANGE).setBaseValue(Configs.getDefaultReach());
                    serverPlayer.getAttribute(Attributes.ENTITY_INTERACTION_RANGE).setBaseValue(Configs.getDefaultReach());
                }
            }
        });
    }
}