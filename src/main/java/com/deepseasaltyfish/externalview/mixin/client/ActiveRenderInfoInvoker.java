package com.deepseasaltyfish.externalview.mixin.client;

import net.minecraft.client.renderer.ActiveRenderInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ActiveRenderInfo.class)
public interface ActiveRenderInfoInvoker {

    @Invoker("setPosition")
    void invokeSetPosition(double x, double y, double z);
}