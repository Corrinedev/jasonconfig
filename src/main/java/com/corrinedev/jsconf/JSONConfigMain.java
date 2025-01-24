package com.corrinedev.jsconf;

import com.corrinedev.jsconf.api.Config;
import com.corrinedev.jsconf.api.ConfigValue;
import com.mojang.logging.LogUtils;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(JSONConfigMain.MODID)
public class JSONConfigMain {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "jsconf";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();

    public JSONConfigMain() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        Config CONF = new Config("jsonConfigConfig");
        CONF.addValue(new ConfigValue<Integer>(5, "integerTest"));
        CONF.addValue(new ConfigValue<String>("eee eee 123", "stringTest"));
        CONF.addValue(new ConfigValue<Float>(5.1256f, "floatTest"));
        ArrayList<String> strArry = new ArrayList<>();
        strArry.add("dingus1");
        strArry.add("dingus2");
        strArry.add("dingus3");
        strArry.add("dingus4");
        strArry.add("dingus5");
        CONF.addValue(new ConfigValue<List<String>>(strArry, "arrayTest"));
        CONF.addValue(new ConfigValue<Vec3>(Vec3.ZERO, "vec3Test"));

        CONF.register();
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
        }
    }
}
