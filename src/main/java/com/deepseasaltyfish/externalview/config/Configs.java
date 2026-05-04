package com.deepseasaltyfish.externalview.config;

import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class Configs {
    public static final ModConfigSpec SPEC;
    public static final Configs INSTANCE;

    static {
        Pair<Configs, ModConfigSpec> pair = new ModConfigSpec.Builder().configure(Configs::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private final ModConfigSpec.DoubleValue cameraSpeed;
    private final ModConfigSpec.DoubleValue viewDistance;
    private final ModConfigSpec.DoubleValue reach;

    private Configs(ModConfigSpec.Builder builder) {
        builder.comment("Camera settings").push("camera");
        cameraSpeed = builder
                .comment("Camera Speed, from 0.01 to 10, default = 0.5")
                .translation("externalview.cfg.camspeed")
                .defineInRange("cameraSpeed", 0.5, 0.01, 10.0);
        viewDistance = builder
                .comment("View Distance, from 1 to 128, default = 56")
                .translation("externalview.cfg.jenya")
                .defineInRange("viewDistance", 56.0, 1.0, 128.0);
        builder.pop();
        builder.comment("Gameplay settings").push("gameplay");
        reach = builder
                .comment("Reach Distance, from 1 to 1024, default = 40")
                .translation("externalview.cfg.reach")
                .defineInRange("reach", 40.0, 1.0, 1024.0);
        builder.pop();
    }

    public static double getCameraSpeed() {
        return INSTANCE.cameraSpeed.get();
    }

    public static double getViewDistance() {
        return INSTANCE.viewDistance.get();
    }

    public static double getReach() {
        return INSTANCE.reach.get();
    }

    public static void register(ModContainer container) {
        container.registerConfig(ModConfig.Type.COMMON, SPEC);
    }
}