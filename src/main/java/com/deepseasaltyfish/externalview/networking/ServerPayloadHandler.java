package com.deepseasaltyfish.externalview.networking;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.fml.network.NetworkEvent;

public class ServerPayloadHandler {

    public static void handleData(AttributePayload payload, NetworkEvent.Context context) {

        ServerPlayerEntity player = context.getSender();

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

        if (player.getAttribute(ForgeMod.REACH_DISTANCE.get()) != null) {
            player.getAttribute(ForgeMod.REACH_DISTANCE.get())
                    .setBaseValue(value);
        }
    }
}