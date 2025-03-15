package com.corrinedev.jsconf.api;

import com.google.common.reflect.TypeToken;
import net.minecraft.world.phys.Vec3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static com.corrinedev.jsconf.JSConf.MODID;

public class SimpleExampleConfigClass {
    public static final Config EXAMPLE_CONFIG = new Config(MODID + "-example_config");

    public static final ConfigComment DEVCOMMENT
            = new ConfigComment("This file is only generated in a Dev environment", EXAMPLE_CONFIG);

    ///The following config values are called with the {<code>EXAMPLE_CONFIG</code>} Config passed in, this automatically adds it to the Config
    public static final ConfigValue<Float> EXAMPLE_FLOAT
            = new ConfigValue<>(10f, "exampleFloat", EXAMPLE_CONFIG, new TypeToken<Float>(){}.getType());
    ///Comments are placed in order of declaration, so this comment will be below {<code>EXAMPLE_FLOAT</code>} and above {<code>EXAMPLE_LIST</code>}
    public static final ConfigComment EXAMPLE_COMMENT
            = new ConfigComment("This is a list!", EXAMPLE_CONFIG);
    public static final ConfigValue<List<String>> EXAMPLE_LIST
            = new ConfigValue<>(List.of("value1", "value2", "value3", "value4"), "exampleList", EXAMPLE_CONFIG, new TypeToken<List<String>>(){}.getType());
    public static final ConfigComment VEC3_COMMENT
            = new ConfigComment("This is a Vec3! Many Java classes that don't work in Forge configs will work here!", EXAMPLE_CONFIG);
    public static final ConfigValue<List<Vec3>> EXAMPLE_VEC3
            = new ConfigValue<>(List.of(new Vec3(123,123,123), Vec3.ZERO), "exampleVec3", EXAMPLE_CONFIG, new TypeToken<List<Vec3>>(){}.getType());
    public static final ConfigComment CLASS_COMMENT
            = new ConfigComment("This is a newly created class, records are the best way to hold data like this", EXAMPLE_CONFIG);
    public static final ConfigValue<LinkedHashMap<String, Info>> EXAMPLE
            = new ConfigValue<>(new LinkedHashMap<>(Map.of("information", new Info(1.0f, false, 8))), "example", EXAMPLE_CONFIG, new TypeToken<LinkedHashMap<String, Info>>(){}.getType());

    ///This method must be called in your mod's Main class
    ///
    /// You will need to call the {<code>register()</code>} method on the {<code>Config</code>} you declared
    /// and add any {<code>ConfigValue<?></></code>} variables you created if you did not assign them in the constructor
    public static void init()/* This method can be called anything, init(), register(), etc*/ {
        EXAMPLE_CONFIG.register();

        ///EXAMPLES OF GRABBING VALUES
        //EXPLICIT (Very similar to Forge's Config)
    }

    public record Info(float f, boolean bool, int integer) { }
}
