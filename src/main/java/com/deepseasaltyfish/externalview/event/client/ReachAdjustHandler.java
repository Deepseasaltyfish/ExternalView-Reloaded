package com.deepseasaltyfish.externalview.event.client;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.AttributePayload;
import com.deepseasaltyfish.externalview.networking.Networking;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ExternalView.MOD_ID, value = Dist.CLIENT)
public class ReachAdjustHandler {

    private static boolean longHandEnabled = false;

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {

        if (event.phase != TickEvent.Phase.END) {
            return;
        }

        if (!ModKeys.TURN_LONG_HAND.consumeClick()) {
            return;
        }

        Minecraft mc = Minecraft.getInstance();

        var conn = mc.getConnection();

        if (mc.player == null || conn == null) {
            return;
        }

        double reachValue = longHandEnabled
                ? Configs.getDefaultReach()
                : Configs.getReach();

        boolean usedPayload = false;

        if (Networking.CHANNEL.isRemotePresent(conn.getConnection())) {

            Networking.CHANNEL.sendToServer(
                    new AttributePayload(reachValue)
            );

            usedPayload = true;

        } else {

            String cmd1 = Configs.getBlockReachCommand(reachValue);
            String cmd2 = Configs.getEntityReachCommand(reachValue);

            mc.player.connection.sendCommand(cmd1);
            mc.player.connection.sendCommand(cmd2);
        }

        longHandEnabled = !longHandEnabled;

        if (usedPayload) {
            ExternalView.LOGGER.info("Reach set via payload: {}", reachValue);
        } else {
            ExternalView.LOGGER.info("Reach set via command: {}", reachValue);
        }
    }
}