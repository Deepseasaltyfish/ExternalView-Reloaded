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
    private void onSetupTail(BlockGetter level, Entity entity, boolean detached, boolean thirdPersonReverse, float partialTick, CallbackInfo ci) {
        if (CameraAdjustHandler.shouldAdjust && CameraAdjustHandler.additionalOffset != Vec3.ZERO) {
            Camera camera = (Camera) (Object) this;
            Vec3 currentPos = camera.getPosition();
            Vec3 newPos = currentPos.add(CameraAdjustHandler.additionalOffset);
            invoke_setPosition(newPos.x, newPos.y, newPos.z);
        }
    }
}