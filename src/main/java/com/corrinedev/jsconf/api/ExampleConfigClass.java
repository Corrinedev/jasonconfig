package com.corrinedev.jsconf.api;

import java.util.List;

import static com.corrinedev.jsconf.JSONConfigMain.MODID;

public class ExampleConfigClass {
    public static final Config EXAMPLE_CONFIG = new Config(MODID + "-config");
    public static final ConfigValue<Integer> EXAMPLE_INT_WITHOUT_EXPLICIT_CONFIG = new ConfigValue<>(10, "exampleInteger");
    ///The following config values are called with the {<code>EXAMPLE_CONFIG</code>} Config passed in, this automatically adds it to the Config
    public static final ConfigValue<Float> EXAMPLE_FLOAT = new ConfigValue<>(10f, "exampleFloat", EXAMPLE_CONFIG);
    ///Comments are placed in order of declaration, so this comment will be below {<code>EXAMPLE_FLOAT</code>} and above {<code>EXAMPLE_LIST</code>}
    public static final ConfigComment EXAMPLE_COMMENT = new ConfigComment("This is a list!", EXAMPLE_CONFIG);
    public static final ConfigValue<List<String>> EXAMPLE_LIST = new ConfigValue<>(List.of("value1", "value2", "value3", "value4"), "exampleList", EXAMPLE_CONFIG);


    ///This method must be called in your mod's Main class
    ///
    /// You will need to call the {<code>register()</code>} method on the {<code>Config</code>} you declared
    /// and add any {<code>ConfigValue<?></></code>} variables you created if you did not assign them in the constructor
    public static void init()/* This method can be called anything, init(), register(), etc*/ {
        /*
        Example usage of helper function, this function will not allow you to directly grab ConfigValues from the
        declared variable though, you will need to get the "key" of the value from the EXAMPLE_CONFIG's "VALUES" HashMap
        add(new ConfigValue<Integer>(5, "integerValue"));
        */
        EXAMPLE_CONFIG.addValue(EXAMPLE_INT_WITHOUT_EXPLICIT_CONFIG);
        EXAMPLE_CONFIG.register();

        ///EXAMPLE WAY OF GRABBING VALUES USING BOTH HELPER AND EXPLICIT

    }

    /*
        This is a helper you may want to create in certain situations, it will make the above shorter
        Recommended option is still the explicitly declared ConfigValue(s)
        Example usage in the init() function above
     */
    public static void add(ConfigValue<?> configEntry) {

    }
}
