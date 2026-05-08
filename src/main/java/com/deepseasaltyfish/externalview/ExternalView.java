package com.deepseasaltyfish.externalview;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.Networking;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.server.permission.events.PermissionGatherEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExternalView.MOD_ID)
@EventBusSubscriber(modid = ExternalView.MOD_ID)
public class ExternalView {
    public static final String MOD_ID = "externalview";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExternalView(ModContainer container, IEventBus modBus) {
        Configs.register(container);
        modBus.addListener(Networking::register);
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(ModKeys::registerKeys);
        }
        LOGGER.info("External View Mod initialized");
    }

    @SubscribeEvent
    public static void onPermissionRegister(PermissionGatherEvent.Nodes event) {
        event.addNodes(ModPermissions.REACH);
    }
}