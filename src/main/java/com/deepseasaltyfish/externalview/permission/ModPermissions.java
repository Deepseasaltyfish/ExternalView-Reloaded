package com.deepseasaltyfish.externalview.permission;

import net.neoforged.neoforge.server.permission.nodes.PermissionNode;
import net.neoforged.neoforge.server.permission.nodes.PermissionTypes;

public class ModPermissions {

    public static final PermissionNode<Boolean> REACH = new PermissionNode<>(
            "externalview",
            "reach",
            PermissionTypes.BOOLEAN,
            (player, uuid, context) ->
                    player != null && player.server.getPlayerList().isOp(player.getGameProfile())
    );

}