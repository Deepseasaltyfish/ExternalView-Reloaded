package com.deepseasaltyfish.externalview;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.Networking;
import com.deepseasaltyfish.externalview.permission.ModPermissions;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.server.permission.events.PermissionGatherEvent;
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
        LOGGER.info("External View Mod initialized");
    }

    @Mod.EventBusSubscriber(modid = ExternalView.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public class ClientModEvents {
        @SubscribeEvent
        public static void registerKeys(RegisterKeyMappingsEvent event) {
            ModKeys.registerKeys(event);
        }
    }

    @SubscribeEvent
    public void onPermissionRegister(PermissionGatherEvent.Nodes event) {
        event.addNodes(ModPermissions.REACH);
    }
}