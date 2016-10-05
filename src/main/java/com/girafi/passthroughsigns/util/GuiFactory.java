package com.girafi.passthroughsigns.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.Set;

public class GuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {
    }

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return GuiPassthroughSignsConfig.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    @Deprecated
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class GuiPassthroughSignsConfig extends GuiConfig {
        public GuiPassthroughSignsConfig(GuiScreen parentScreen) {
            super(parentScreen, new ConfigElement(ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL)).getChildElements(), Reference.MOD_ID, false, false, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
        }
    }
}