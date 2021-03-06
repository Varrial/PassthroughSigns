package com.girafi.passthroughsigns;

import com.girafi.passthroughsigns.api.PassthroughSignsAPI;
import com.girafi.passthroughsigns.util.ConfigurationHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(value = PassthroughSigns.MOD_ID)
public class PassthroughSigns {
    public static final String MOD_ID = "passthroughsigns";

    public PassthroughSigns() {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::handleIMCMessages);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ConfigurationHandler.spec);
    }

    public void handleIMCMessages(InterModProcessEvent event) {
        event.getIMCStream("registerPassable"::equals).forEach(m -> PassthroughSignsAPI.setCanBePassed(m.getMethod()));
    }
}