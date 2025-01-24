package com.corrinedev.jsconf.api;

import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.fml.loading.FMLPaths;
import net.minecraftforge.registries.ForgeRegistries;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Config {
    private static final Logger log = LogManager.getLogger(Config.class);
    public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public ArrayList<ConfigValue<?>> VALUES = new ArrayList<>();
    public String fileName;
    public static final Path DIR = FMLPaths.CONFIGDIR.get();

    public Config(String fileName) {
        this.fileName = fileName + ".json";
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
        this.VALUES.add(value);
        log.info("ADDED CONFIG VAL = {}", value.value() + " | TO JSON = " + value.getAsJson());
        return this;
    }

    public void readConfig() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(file.exists()) {
            FileReader reader = new FileReader(file);
            JsonObject obj = GSON.fromJson(reader, JsonObject.class);
            System.out.println("CONFIG MAP = " + obj.asMap());
        } else {
            checkConfig();
        }
    }

    public void checkConfig() throws IOException {
        File file = DIR.resolve(this.fileName).toFile();
        if(!file.exists()) {
            FileWriter writer = new FileWriter(file);
            JsonObject Config = new JsonObject();
            for(ConfigValue<?> value : this.VALUES) {
                Config.add(value.element(), value.getAsJson());
            }

            System.out.println("JSON CONFIG GEN = " + GSON.toJson(Config));
            writer.write(GSON.toJson(Config));
            writer.close();
        }
    }
}
