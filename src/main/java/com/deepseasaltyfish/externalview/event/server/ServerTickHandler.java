package com.deepseasaltyfish.externalview.event.server;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.server.permission.PermissionAPI;

@Mod.EventBusSubscriber(modid = ExternalView.MOD_ID)
public class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {

            boolean allowed =
                    PermissionAPI.getPermission(player, ModPermissions.REACH);

            var blockAttr =
                    player.getAttribute(ForgeMod.BLOCK_REACH.get());

            var entityAttr =
                    player.getAttribute(ForgeMod.ENTITY_REACH.get());

            if (blockAttr == null || entityAttr == null) {
                continue;
            }

            double current = blockAttr.getBaseValue();

            if (!allowed && current > Configs.getDefaultReach()) {

                blockAttr.setBaseValue(
                        Configs.getDefaultReach()
                );

                entityAttr.setBaseValue(
                        Configs.getDefaultReach()
                );
            }
        }
    }
}