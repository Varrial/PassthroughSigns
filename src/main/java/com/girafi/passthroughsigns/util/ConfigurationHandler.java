package com.girafi.passthroughsigns.util;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.io.File;

import static net.minecraftforge.common.config.Configuration.CATEGORY_GENERAL;

@EventBusSubscriber
public class ConfigurationHandler {
    static Configuration config;
    static boolean shouldWallSignBePassable;
    static boolean shouldBannerBePassable;
    static boolean shouldItemFrameBePassable;
    static boolean shouldPaintingsBePassable;
    static boolean turnOffItemRotation;


    public static void init(File configFile) {
        if (config == null) {
            config = new Configuration(configFile);
            loadConfiguration();
        }
    }

    private static void loadConfiguration() {
        shouldWallSignBePassable = config.getBoolean("Ignore wall signs", CATEGORY_GENERAL, true, "Whether to ignore wall signs when attached to an interactable block or not.");
        shouldBannerBePassable = config.getBoolean("Ignore banners", CATEGORY_GENERAL, false, "Whether to ignore banners when attached to an interactable block or not. ");
        shouldItemFrameBePassable = config.getBoolean("Ignore item frames", CATEGORY_GENERAL, false, "Whether to ignore item frames when attached to an interactable block or not");
        turnOffItemRotation = config.getBoolean("Turn off item rotation", CATEGORY_GENERAL, false, "Disable default behaviour of item frames rotation display, when not sneaking (Recommended when ignoring item frames is enabled)");
        shouldPaintingsBePassable = config.getBoolean("Ignore paintings", CATEGORY_GENERAL, false, "Whether to ignore paintings when attached to an interactable block or not.");

        if (config.hasChanged()) {
            config.save();
        }
    }

    @SubscribeEvent
    public static void onConfigurationChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equalsIgnoreCase(Reference.MOD_ID)) {
            loadConfiguration();
        }
    }
}