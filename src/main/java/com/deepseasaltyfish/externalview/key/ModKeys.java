package com.deepseasaltyfish.externalview.key;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import org.lwjgl.glfw.GLFW;

public class ModKeys {
    public static final KeyMapping ZOOM_OUT = new KeyMapping(
            "key.externalview.zoom_out.description",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PAGE_UP,
            "key.externalview.category"
    );
    public static final KeyMapping ZOOM_IN = new KeyMapping(
            "key.externalview.zoom_in.description",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_PAGE_DOWN,
            "key.externalview.category"
    );
    public static final KeyMapping TURN_LONG_HAND = new KeyMapping(
            "key.externalview.turn_long_hand",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_K,
            "key.externalview.category"
    );

//1.21.9
//    public static final KeyMapping ZOOM_OUT = new KeyMapping(
//            "key.externalview.zoom_out.description",
//            InputConstants.getKey(GLFW.GLFW_KEY_PAGE_UP, -1),
//            "key.externalview.category"
//    );
//
//    public static final KeyMapping ZOOM_IN = new KeyMapping(
//            "key.externalview.zoom_in.description",
//            InputConstants.getKey(GLFW.GLFW_KEY_PAGE_DOWN, -1),
//            "key.externalview.category"
//    );
//
//    public static final KeyMapping TURN_LONG_HAND = new KeyMapping(
//            "key.externalview.turn_long_hand",
//            InputConstants.getKey(GLFW.GLFW_KEY_K, -1),
//            "key.externalview.category"
//    );

    public static void registerKeys(RegisterKeyMappingsEvent event) {
        event.register(ZOOM_OUT);
        event.register(ZOOM_IN);
        event.register(TURN_LONG_HAND);
    }
}