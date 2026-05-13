package com.deepseasaltyfish.externalview.event.client;

import com.deepseasaltyfish.externalview.ExternalView;
import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.mixin.client.ActiveRenderInfoInvoker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityViewRenderEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(
        modid = ExternalView.MOD_ID,
        value = Dist.CLIENT
)
public class CameraAdjustHandler {

    private static double additionalDistance = 0.0D;

    @SubscribeEvent
    public static void onCameraSetup(EntityViewRenderEvent.CameraSetup event) {

        Minecraft mc = Minecraft.getInstance();

        ClientPlayerEntity player = mc.player;

        if (player == null || mc.level == null) {
            additionalDistance = 0.0D;
            return;
        }

        PointOfView pov = mc.options.getCameraType();

        if (pov.isFirstPerson()) {
            additionalDistance = 0.0D;
            return;
        }

        double step = Configs.getCameraSpeed();
        double maxDist = Configs.getViewDistance();

        if (ModKeys.ZOOM_OUT.isDown()) {

            additionalDistance += step;

            if (additionalDistance > maxDist) {
                additionalDistance = maxDist;
            }
        }

        if (ModKeys.ZOOM_IN.isDown()) {

            additionalDistance -= step;

            if (additionalDistance < 0.0D) {
                additionalDistance = 0.0D;
            }
        }

        ActiveRenderInfo info = event.getInfo();

        Vector3d vanillaPos = info.getPosition();

        if (additionalDistance <= 0.0D) {

            ((ActiveRenderInfoInvoker) info)
                    .invokeSetPosition(
                            vanillaPos.x,
                            vanillaPos.y,
                            vanillaPos.z
                    );

            return;
        }

        double partial = event.getRenderPartialTicks();

        double x = player.xo + (player.getX() - player.xo) * partial;
        double y = player.yo + (player.getY() - player.yo) * partial;
        double z = player.zo + (player.getZ() - player.zo) * partial;

        double eyeY = y + player.getEyeHeight();

        float yaw = event.getYaw();
        float pitch = event.getPitch();

        float yawRad = (float) Math.toRadians(yaw);
        float pitchRad = (float) Math.toRadians(pitch);

        double lookX = -Math.sin(yawRad) * Math.cos(pitchRad);
        double lookY = -Math.sin(pitchRad);
        double lookZ = Math.cos(yawRad) * Math.cos(pitchRad);

        double direction = -1.0D;

        Vector3d start = new Vector3d(x, eyeY, z);

        double vanillaDistance = vanillaPos.distanceTo(start);

        double targetDistance = vanillaDistance + additionalDistance;

        Vector3d end = start.add(
                lookX * targetDistance * direction,
                lookY * targetDistance * direction,
                lookZ * targetDistance * direction
        );

        double finalDistance = targetDistance;

        if (Configs.enableCollision()) {

            RayTraceContext context = new RayTraceContext(
                    start,
                    end,
                    RayTraceContext.BlockMode.COLLIDER,
                    RayTraceContext.FluidMode.NONE,
                    player
            );

            BlockRayTraceResult hit = mc.level.clip(context);

            if (hit.getType() != RayTraceResult.Type.MISS) {

                finalDistance =
                        hit.getLocation().distanceTo(start) - 0.2D;

                if (finalDistance < 0.0D) {
                    finalDistance = 0.0D;
                }
            }
        }

        Vector3d cameraPos = start.add(
                lookX * finalDistance * direction,
                lookY * finalDistance * direction,
                lookZ * finalDistance * direction
        );

        ((ActiveRenderInfoInvoker) info)
                .invokeSetPosition(
                        cameraPos.x,
                        cameraPos.y,
                        cameraPos.z
                );
    }

    public static double getDistance() {
        return additionalDistance;
    }
}