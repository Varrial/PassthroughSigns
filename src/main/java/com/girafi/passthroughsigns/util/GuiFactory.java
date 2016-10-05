package com.girafi.passthroughsigns.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
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
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    public static class GuiPassthroughSignsConfig extends GuiConfig {
        public GuiPassthroughSignsConfig(GuiScreen parentScreen) {
            super(parentScreen, null /*getConfigElements()*/, Reference.MOD_ID, true, true, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
        }

       /* private static List<IConfigElement> getConfigElements() {
            List<IConfigElement> list = new ArrayList<IConfigElement>();

            List<IConfigElement> mobDrops = new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_MOB_DROPS)).getChildElements();
            List<IConfigElement> modSupport = new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_MOD_SUPPORT_ENABLING)).getChildElements();
            List<IConfigElement> rightClickHarvesting = new ConfigElement(ConfigurationHandler.config.getCategory(ConfigurationHandler.CATEGORY_RIGHT_CLICK_HARVESTING)).getChildElements();


            list.add(new DummyConfigElement.DummyCategoryElement("Mob Drops", Reference.MOD_ID + ".config.category.mobDrops", mobDrops));
            list.add(new DummyConfigElement.DummyCategoryElement("Mod Support", Reference.MOD_ID + ".config.category.modSupport", modSupport));
            list.add(new DummyConfigElement.DummyCategoryElement("Right Click Harvesting", Reference.MOD_ID + ".config.category.rightClickHarvesting", rightClickHarvesting));

            return list;
        }*/
    }
}