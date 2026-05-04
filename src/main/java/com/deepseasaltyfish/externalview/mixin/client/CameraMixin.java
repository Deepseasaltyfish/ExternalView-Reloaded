package com.deepseasaltyfish.externalview.mixin.client;

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
    private void onSetupTail(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {//inspired from mts, first write by deepseek, corrected by chatgpt
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

        float direction = thirdPersonReverse ? 1.0F : -1.0F;

        Vec3 offset = lookVec.scale(direction * dist);

        Vec3 newPos = currentPos.add(offset);

        invoke_setPosition(newPos.x, newPos.y, newPos.z);
    }
}