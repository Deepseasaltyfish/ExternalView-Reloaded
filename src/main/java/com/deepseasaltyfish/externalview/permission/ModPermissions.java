package com.deepseasaltyfish.externalview.permission;

import com.deepseasaltyfish.externalview.ExternalView;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.server.permission.PermissionAPI;
import net.minecraftforge.server.permission.nodes.PermissionNode;
import net.minecraftforge.server.permission.nodes.PermissionTypes;

public class ModPermissions {

    public static final PermissionNode<Boolean> REACH =
            new PermissionNode<>(
                    ExternalView.MOD_ID,
                    "reach",
                    PermissionTypes.BOOLEAN,
                    (player, uuid, contexts) ->
                            player != null && player.hasPermissions(2)
            );

    public static boolean canReach(ServerPlayer player) {
        return PermissionAPI.getPermission(player, REACH);
    }
}