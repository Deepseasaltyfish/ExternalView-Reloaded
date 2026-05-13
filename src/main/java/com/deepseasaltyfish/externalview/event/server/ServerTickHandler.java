package com.deepseasaltyfish.externalview.event.server;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.server.ServerLifecycleHooks;

@Mod.EventBusSubscriber(modid = ExternalView.MOD_ID)
public class ServerTickHandler {

    @SubscribeEvent
    public static void onServerTick(TickEvent.ServerTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        for (ServerPlayerEntity player : ServerLifecycleHooks.getCurrentServer().getPlayerList().getPlayers()) {

            boolean allowed = ModPermissions.canReach(player);

            if (player.getAttribute(ForgeMod.REACH_DISTANCE.get()) == null) continue;

            double current = player.getAttribute(ForgeMod.REACH_DISTANCE.get()).getBaseValue();

            if (!allowed && current > Configs.getDefaultReach()) {
                player.getAttribute(ForgeMod.REACH_DISTANCE.get()).setBaseValue(Configs.getDefaultReach());
            }
        }
    }
}