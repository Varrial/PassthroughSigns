package com.girafi.passthroughsigns;

import com.girafi.passthroughsigns.api.PassthroughSignsAPI;
import com.girafi.passthroughsigns.util.ConfigurationHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import java.io.File;

import static com.girafi.passthroughsigns.util.Reference.*;

@Mod(modid = MOD_ID, name = MOD_NAME, version = MOD_VERSION, dependencies = DEPENDENCIES, guiFactory = GUI_FACTORY_CLASS)
public class PassthroughSigns {

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ConfigurationHandler.init(new File(event.getModConfigurationDirectory(), "PassthroughSigns.cfg"));
    }

    @Mod.EventHandler
    public void handleIMCMessages(FMLInterModComms.IMCEvent event) {
        for (FMLInterModComms.IMCMessage message : event.getMessages()) {
            if (message.key.equalsIgnoreCase("registerPassable")) {
                PassthroughSignsAPI.setCanBePassed(message.getStringValue());
            }
        }
    }
}