package com.deepseasaltyfish.externalview.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class ModKeys {

    public static final KeyBinding ZOOM_OUT = new KeyBinding(
            "key.externalview.zoom_out.description",
            GLFW.GLFW_KEY_PAGE_UP,
            "key.externalview.category"
    );

    public static final KeyBinding ZOOM_IN = new KeyBinding(
            "key.externalview.zoom_in.description",
            GLFW.GLFW_KEY_PAGE_DOWN,
            "key.externalview.category"
    );

    public static final KeyBinding TURN_LONG_HAND = new KeyBinding(
            "key.externalview.turn_long_hand",
            GLFW.GLFW_KEY_K,
            "key.externalview.category"
    );

    public static void registerKeyBindings() {
        ClientRegistry.registerKeyBinding(ZOOM_OUT);
        ClientRegistry.registerKeyBinding(ZOOM_IN);
        ClientRegistry.registerKeyBinding(TURN_LONG_HAND);
    }
}