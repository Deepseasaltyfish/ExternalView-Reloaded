package com.deepseasaltyfish.externalview;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.Networking;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLEnvironment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExternalView.MOD_ID)
public class ExternalView {

    public static final String MOD_ID = "externalview";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExternalView() {

        Configs.register(ModLoadingContext.get());

        Networking.register();

        MinecraftForge.EVENT_BUS.register(this);

        // client only
        if (FMLEnvironment.dist == Dist.CLIENT) {
            ClientRegistry.registerKeyBinding(ModKeys.ZOOM_OUT);
            ClientRegistry.registerKeyBinding(ModKeys.ZOOM_IN);
            ClientRegistry.registerKeyBinding(ModKeys.TURN_LONG_HAND);
        }

        // permission node
        ModPermissions.register();

        LOGGER.info("External View Mod initialized");
    }
}