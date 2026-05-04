package com.deepseasaltyfish.externalview.event.client;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ViewportEvent;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = "externalview", value = Dist.CLIENT)
public class CameraAdjustHandler {
    private static final Logger LOGGER = ExternalView.LOGGER;
    private static double additionalDistance = 0.0;
    public static boolean shouldAdjust = false;
    public static Vec3 additionalOffset = Vec3.ZERO;

    @SubscribeEvent
    public static void onComputeCameraAngles(ViewportEvent.ComputeCameraAngles event) {
        var mc = Minecraft.getInstance();
        var player = mc.player;
        var options = mc.options;

        if (player == null || options.getCameraType().isFirstPerson()) {
            if (additionalDistance != 0.0) {
                additionalDistance = 0.0;
                shouldAdjust = false;
                additionalOffset = Vec3.ZERO;
            }
            return;
        }

        double step = Configs.getCameraSpeed();
        double maxDist = Configs.getViewDistance();

        if (ModKeys.ZOOM_OUT.isDown()) {
            additionalDistance += step;
            if (additionalDistance > maxDist) additionalDistance = maxDist;
        }
        if (ModKeys.ZOOM_IN.isDown()) {
            additionalDistance -= step;
            if (additionalDistance < 0.0) additionalDistance = 0.0;
        }

        if (additionalDistance <= 0.0) {
            shouldAdjust = false;
            additionalOffset = Vec3.ZERO;
            return;
        }

        Vec3 lookVec = player.getLookAngle();
        float direction = (options.getCameraType() == CameraType.THIRD_PERSON_BACK) ? -1.0F : 1.0F;
        additionalOffset = lookVec.scale(direction * additionalDistance);
        shouldAdjust = additionalDistance > 0.0;
    }

    public static double getDistance() {
        return additionalDistance;
    }
}