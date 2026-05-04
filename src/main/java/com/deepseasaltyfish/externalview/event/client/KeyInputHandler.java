// src/main/java/com/frozerain/externalview/event/client/ClientKeyHandler.java
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
public class KeyInputHandler {
    private static boolean longHandEnabled = false;   // 记录当前是否处于长手模式

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        if (ModKeys.TURN_LONG_HAND.consumeClick()) {
            double reachValue;
            if (!longHandEnabled) {
                reachValue = Configs.getReach();
            } else {
                reachValue = 5.0;
            }
            PacketDistributor.sendToServer(new AttributePayload((int) Math.round(reachValue)));
            longHandEnabled = !longHandEnabled;
            ExternalView.LOGGER.info("Long hand toggled, reach set to: " + reachValue);
        }
    }
}