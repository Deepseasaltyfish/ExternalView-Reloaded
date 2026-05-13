package com.deepseasaltyfish.externalview.config;

import com.deepseasaltyfish.externalview.ExternalView;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.config.ModConfig;
import org.apache.commons.lang3.tuple.Pair;

public class Configs {

    public static final ForgeConfigSpec SPEC;
    public static final Configs INSTANCE;

    static {
        Pair<Configs, ForgeConfigSpec> pair = new ForgeConfigSpec.Builder().configure(Configs::new);
        INSTANCE = pair.getLeft();
        SPEC = pair.getRight();
    }

    private final ForgeConfigSpec.DoubleValue cameraSpeed;
    private final ForgeConfigSpec.DoubleValue viewDistance;
    private final ForgeConfigSpec.DoubleValue reach;
    private final ForgeConfigSpec.DoubleValue defaultReach;

    private final ForgeConfigSpec.BooleanValue cameraCollision;

    private final ForgeConfigSpec.ConfigValue<String> blockReachCommand;
//    private final ForgeConfigSpec.ConfigValue<String> entityReachCommand;

    private static final String DEFAULT_BLOCK_CMD =
            "attribute @s forge:reach_distance base set {value}";

//    private static final String DEFAULT_ENTITY_CMD =
//            "attribute @s forge:reach_distance base set {value}";

    private Configs(ForgeConfigSpec.Builder builder) {
        builder.comment("Camera settings").push("camera");
        cameraSpeed = builder
                .comment("Camera Speed, from 0.01 to 10, default = 0.5")
                .translation("externalview.cfg.camspeed")
                .defineInRange("cameraSpeed", 0.5, 0.01, 10.0);
        viewDistance = builder
                .comment("View Distance, from 1 to 128, default = 56")
                .translation("externalview.cfg.jenya")
                .defineInRange("viewDistance", 56.0, 1.0, 128.0);
        cameraCollision = builder
                .comment("Enable camera collision (prevent clipping through blocks), default = true")
                .translation("externalview.cfg.cameraCollision")
                .define("cameraCollision", true);
        builder.pop();

        builder.comment("Reach settings").push("reach");
        reach = builder
                .comment("Reach Distance, from 1 to 64, default = 40")
                .translation("externalview.cfg.reach")
                .defineInRange("reach", 40.0, 1.0, 64.0);
        defaultReach = builder
                .comment("Default Reach Distance, from 1 to 64, default = 5 (vanilla is 5.0, changing may cause issues)")
                .translation("externalview.cfg.defaultreach")
                .defineInRange("defaultReach", 5.0, 1.0, 64.0);
        blockReachCommand = builder
                .comment("Command to set reach distance, use {value} as placeholder, do not change")
                .define("blockReachCommand",
                        DEFAULT_BLOCK_CMD);
//        entityReachCommand = builder
//                .comment("Command to set entity interaction range, use {value} as placeholder, do not change")
//                .define("entityReachCommand",
//                        DEFAULT_ENTITY_CMD);
        builder.pop();
    }

    public static void register(ModLoadingContext modLoadingContext) {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SPEC);
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

    public static double getDefaultReach() {
        return INSTANCE.defaultReach.get();
    }

    public static boolean enableCollision() {
        return INSTANCE.cameraCollision.get();
    }

    public static String getBlockReachCommand(double value) {
        String raw = INSTANCE.blockReachCommand.get();

        if (!isValidCommand(raw)) {
            raw = DEFAULT_BLOCK_CMD;
            ExternalView.LOGGER.warn("Invalid block reach command, fallback to default.");
        }

        return raw.replace("{value}", String.valueOf(value));
    }

//    public static String getEntityReachCommand(double value) {
//        String raw = INSTANCE.entityReachCommand.get();
//        if (!isValidCommand(raw)) {
//            raw = DEFAULT_ENTITY_CMD;
//            ExternalView.LOGGER.warn("Invalid entity reach command, fallback to default.");
//        }
//        return raw.replace("{value}", String.valueOf(value));
//    }

    private static boolean isValidCommand(String cmd) {
        if (cmd == null) return false;
        cmd = cmd.trim();
        if (cmd.isEmpty()) return false;
        if (!cmd.contains("{value}")) return false;
        if (!cmd.contains(" ")) return false;
        return true;
    }
}