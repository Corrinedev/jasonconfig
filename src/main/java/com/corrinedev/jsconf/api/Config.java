package com.corrinedev.jsconf.api;

import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;

public class Config {
    private static final Logger log = LogManager.getLogger(Config.class);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public HashMap<String, ConfigValue<?>> VALUES = new HashMap<>();
    public String fileName;
    public static final Path DIR = FMLPaths.CONFIGDIR.get();

    public Config(String fileName) {
        LoadedConfigs.CONFIGS.put(fileName, this);
        this.fileName = fileName + ".json";

    }
    public ConfigValue<?> getValue(String key) {
        return VALUES.get(key);
    }

    ///This one reloads the JSON Config every time its called, not recommended except for debug purposes
    public ConfigValue<?> getValueAndUpdate(String key) {
        try {
            readConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return VALUES.get(key);
    }
    public Config update() {
        try {
            readConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    /// Place this in your mod's Main class constructor
    public void register() {
        try {
            readConfig();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Config addValue(ConfigValue<?> value) {
        this.VALUES.put(value.element, value);
        log.info("ADDED CONFIG VAL = {}", value.get() + " | TO JSON = " + value.getAsJson());
        return this;
    }

    public void readConfig() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(file.exists()) {
            FileReader reader = new FileReader(file);
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);

            for (String e : obj.keySet()) {
                JsonElement element = obj.get(e);
                ConfigValue<?> val = VALUES.get(e);

                val.set(GSON.fromJson(element, (Type) val.get().getClass()));
            }

        } else {
            firstWrite();
        }
    }

    public void firstWrite() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(!file.exists()) {
            FileWriter writer = new FileWriter(file);
            JsonObject config = new JsonObject();
            for(ConfigValue<?> value : this.VALUES.values()) {
                config.add(value.element, value.getAsJson());
            }

            System.out.println("JSON CONFIG GEN = " + GSON.toJson(config));
            System.out.println("VALUES OF CONFIG = " + VALUES);
            writer.write(GSON.toJson(config));
            writer.close();
        }
    }
}
