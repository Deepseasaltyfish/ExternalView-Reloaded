package com.deepseasaltyfish.externalview.event.client;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.AttributePayload;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = ExternalView.MOD_ID, value = Dist.CLIENT)
public class ReachAdjustHandler {
    private static boolean longHandEnabled = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (!ModKeys.TURN_LONG_HAND.consumeClick()) return;

        var mc = net.minecraft.client.Minecraft.getInstance();
        var conn = mc.getConnection();

        if (mc.player == null || conn == null) {
            ExternalView.LOGGER.info("No connection/player, abort.");
            return;
        }

        double reachValue = longHandEnabled
                ? Configs.getDefaultReach()
                : Configs.getReach();

        boolean usedPayload = false;

        if (conn.hasChannel(AttributePayload.TYPE)) {//payload
            PacketDistributor.sendToServer(new AttributePayload(reachValue));
            usedPayload = true;
        } else {//fall back: use command
            String cmd1 = Configs.getBlockReachCommand(reachValue);
            String cmd2 = Configs.getEntityReachCommand(reachValue);
            mc.player.connection.sendCommand(cmd1);
            mc.player.connection.sendCommand(cmd2);
        }

        longHandEnabled = !longHandEnabled;

        if (usedPayload) {
            ExternalView.LOGGER.info("Reach set via payload: " + reachValue);
        } else {
            ExternalView.LOGGER.info("Reach set via command (may fail): " + reachValue);
        }
    }
}