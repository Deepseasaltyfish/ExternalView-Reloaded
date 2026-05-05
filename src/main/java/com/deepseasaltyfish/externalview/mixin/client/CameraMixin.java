package com.deepseasaltyfish.externalview.mixin.client;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.event.client.CameraAdjustHandler;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {

    @Invoker("setPosition")
    public abstract void invoke_setPosition(double x, double y, double z);

    @Inject(method = "setup", at = @At("TAIL"))
    private void onSetupTail(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        double dist = CameraAdjustHandler.getDistance();
        if (!CameraAdjustHandler.shouldAdjust || dist <= 0.0) return;

        Camera camera = (Camera) (Object) this;

        Vec3 currentPos = camera.getPosition();

        float yaw = camera.getYRot();
        float pitch = camera.getXRot();

        double radYaw = Math.toRadians(yaw);
        double radPitch = Math.toRadians(pitch);

        double x = -Math.sin(radYaw) * Math.cos(radPitch);
        double y = -Math.sin(radPitch);
        double z =  Math.cos(radYaw) * Math.cos(radPitch);

        Vec3 lookVec = new Vec3(x, y, z);

        float direction = -1.0F;

        Vec3 offset = lookVec.scale(direction * dist);

        Vec3 newPos = currentPos.add(offset);

        if (Configs.enableCollision()) {
            Vec3 start = currentPos;
            Vec3 end = newPos;

            var result = level.clip(new net.minecraft.world.level.ClipContext(
                    start,
                    end,
                    net.minecraft.world.level.ClipContext.Block.VISUAL,
                    net.minecraft.world.level.ClipContext.Fluid.NONE,
                    camera.getEntity()
            ));

            if (result.getType() != net.minecraft.world.phys.HitResult.Type.MISS) {
                double hitDist = result.getLocation().distanceTo(start);
                double safeDist = Math.max(0.0, hitDist - 0.1);

                newPos = start.add(lookVec.scale(direction * safeDist));
            }
        }

        invoke_setPosition(newPos.x, newPos.y, newPos.z);
    }
}