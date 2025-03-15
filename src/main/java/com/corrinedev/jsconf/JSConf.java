package com.corrinedev.jsconf;

import com.corrinedev.jsconf.api.Config;
import com.corrinedev.jsconf.api.SimpleExampleConfigClass;
import com.mojang.logging.LogUtils;
import cpw.mods.modlauncher.Launcher;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLLoader;
import net.minecraftforge.forgespi.Environment;
import org.apache.logging.log4j.core.lookup.EnvironmentLookup;
import org.slf4j.Logger;

import java.util.LinkedHashMap;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JSConf.MODID)
public class JSConf {

    public static LinkedHashMap<String, Config> CONFIGS = new LinkedHashMap<>();

    // Define mod id in a common place for everything to reference
    public static final String MODID = "jsconf";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public JSConf() {
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        ///Example Config Creation
        if(!FMLLoader.isProduction()) SimpleExampleConfigClass.init();
    }

}
