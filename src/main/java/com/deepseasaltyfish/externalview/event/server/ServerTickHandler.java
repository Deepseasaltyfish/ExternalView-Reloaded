package com.deepseasaltyfish.externalview.event.server;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.server.permission.PermissionAPI;

@EventBusSubscriber(modid = ExternalView.MOD_ID)
public class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(net.neoforged.neoforge.event.tick.ServerTickEvent.Post event) {
        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {

            boolean allowed = PermissionAPI.getPermission(player, ModPermissions.REACH);

            var blockAttr = player.getAttribute(Attributes.BLOCK_INTERACTION_RANGE);
            var entityAttr = player.getAttribute(Attributes.ENTITY_INTERACTION_RANGE);

            if (blockAttr == null || entityAttr == null) continue;

            double current = blockAttr.getBaseValue();

            if (!allowed && current > Configs.getDefaultReach()) {
                blockAttr.setBaseValue(Configs.getDefaultReach());
                entityAttr.setBaseValue(Configs.getDefaultReach());
            }
        }
    }
}
