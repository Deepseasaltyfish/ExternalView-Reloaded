package com.deepseasaltyfish.externalview.permission;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.PermissionAPI;

public class ModPermissions {

    public static final String REACH = "externalview.reach";

    public static void register() {
        PermissionAPI.registerNode(
                REACH,
                DefaultPermissionLevel.OP,
                "Allows changing reach distance"
        );
    }

    public static boolean canReach(ServerPlayerEntity player) {
        return PermissionAPI.hasPermission(player, REACH);
    }
}