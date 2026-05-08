package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.network.NetworkEvent;

public class ServerPayloadHandler {

    public static void handleData(AttributePayload payload, NetworkEvent.Context context) {

        ServerPlayer player = context.getSender();

        if (player == null) {
            return;
        }

        // OP level 2
        boolean allowed = ModPermissions.canReach(player);

        double value;

        if (allowed) {
            value = payload.getValue();
        } else {
            value = Configs.getDefaultReach();
        }

        if (player.getAttribute(ForgeMod.BLOCK_REACH.get()) != null) {
            player.getAttribute(ForgeMod.BLOCK_REACH.get()).setBaseValue(value);
        }

        if (player.getAttribute(ForgeMod.ENTITY_REACH.get()) != null) {
            player.getAttribute(ForgeMod.ENTITY_REACH.get()).setBaseValue(value);
        }
    }
}