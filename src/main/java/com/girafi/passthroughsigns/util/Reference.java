package com.girafi.passthroughsigns.util;

import net.minecraftforge.fml.common.Loader;

public class Reference {
    public static final String MOD_ID = "passthroughsigns";
    public static final String MOD_NAME = "Passthrough Signs";
    public static final String MOD_VERSION = "%MOD_VERSION%";
    public static final String DEPENDENCIES = "required-after:forge@[13.20,)";
    public static final String GUI_FACTORY_CLASS = "com.girafi.passthroughsigns.util.GuiFactory";
    public static final boolean IS_QUARK_LOADED = Loader.isModLoaded("quark");
}