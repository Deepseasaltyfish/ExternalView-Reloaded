package com.deepseasaltyfish.externalview;

import com.deepseasaltyfish.externalview.config.Configs;
import com.deepseasaltyfish.externalview.key.ModKeys;
import com.deepseasaltyfish.externalview.networking.Networking;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.bus.api.IEventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ExternalView.MOD_ID)
public class ExternalView {
    public static final String MOD_ID = "externalview";
    public static final String NAME = "External View Mod";
    public static final String VERSION = "2.1";
    public static final Logger LOGGER = LogManager.getLogger();

    public ExternalView(ModContainer container, IEventBus modBus) {
        Configs.register(container);
        modBus.addListener(ModKeys::registerKeys);
        modBus.addListener(Networking::register);
        LOGGER.info("External View Mod initialized");
    }
}