package com.corrinedev.jsconf.api;

import com.corrinedev.jsconf.JSConf;
import com.google.gson.*;
import net.minecraftforge.fml.loading.FMLPaths;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.LinkedHashMap;

public class Config {
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public LinkedHashMap<String, ConfigValue<?>> VALUES = new LinkedHashMap<>();
    public String fileName;
    public static final Path DIR = FMLPaths.CONFIGDIR.get();

    public Config(String fileName) {
        JSConf.CONFIGS.put(fileName, this);
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
    /// Call this at any time to reload the config
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
            JSConf.LOGGER.info("Config {} was loaded, values = {}", this.fileName, this.VALUES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Config addValue(ConfigValue<?> value) {
        this.VALUES.put(value.element, value);
        return this;
    }

    private void readConfig() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(file.exists()) {
            FileReader reader = new FileReader(file);
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);

            for (String e : obj.keySet()) {
                JsonElement element = obj.get(e);
                ConfigValue<?> val = VALUES.get(e);

                val.set(GSON.fromJson(element, val.getType()));

                JSConf.LOGGER.info("type = {}", val.get().getClass().getName());
            }

        } else {
            firstWrite();
        }
    }

    private void firstWrite() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(!file.exists()) {
            FileWriter writer = new FileWriter(file);
            JsonObject config = new JsonObject();
            for(ConfigValue<?> value : this.VALUES.values()) {
                config.add(value.element, value.getAsJson());
            }
            writer.write(GSON.toJson(config));
            writer.close();
        }
    }
}
