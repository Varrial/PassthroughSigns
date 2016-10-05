package com.girafi.passthroughsigns;

import com.girafi.passthroughsigns.util.ConfigurationHandler;
import com.girafi.passthroughsigns.util.PassableHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static com.girafi.passthroughsigns.util.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, acceptedMinecraftVersions = "[1.10,1.11)", dependencies = DEPENDENCIES, guiFactory = GUI_FACTORY_CLASS)
public class PassthroughSigns {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "PassthroughSigns.cfg"));
        MinecraftForge.EVENT_BUS.register(new ConfigurationHandler());
        MinecraftForge.EVENT_BUS.register(new PassableHandler());
    }
}