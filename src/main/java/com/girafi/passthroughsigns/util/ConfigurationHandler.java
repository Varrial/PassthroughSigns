package com.girafi.passthroughsigns.util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

public class ConfigurationHandler {
    static Configuration config;
    static boolean shouldWallSignBePassable;
    static boolean shouldBannerBePassable;
    static boolean shouldItemFrameBePassable;
    static boolean shouldPaintingsBePassable;


    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        shouldWallSignBePassable = config.getBoolean("Should wall signs be passable", CATEGORY_GENERAL, true, "Whether wall signs should be passable or not.");
        shouldBannerBePassable = config.getBoolean("Should banners be passable", CATEGORY_GENERAL, false, "Whether banners should be passable or not.");
        shouldItemFrameBePassable = config.getBoolean("Should items frames be passable", CATEGORY_GENERAL, false, "Whether item frames should be passable or not.");
        shouldPaintingsBePassable = config.getBoolean("Should paintings be passable", CATEGORY_GENERAL, false, "Whether paintings should be passable or not.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}