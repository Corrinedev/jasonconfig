package com.corrinedev.jsconf.api;

import com.corrinedev.jsconf.JSConf;
import com.google.common.reflect.TypeToken;
import com.google.gson.JsonElement;
import net.minecraftforge.common.ForgeConfigSpec;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import static com.corrinedev.jsconf.api.Config.GSON;

public class ConfigValue<E> {
    public final String element;
    private E value;
    private final Type type;
    public ConfigValue(E value, String element, Type type) {
        this.value = value;
        this.element = element;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public ConfigValue(E value, String element, String confToAdd, Type type) {
        this(value, element, type);
        JSConf.CONFIGS.get(confToAdd).addValue(this);
    }
    public ConfigValue(E value, String element, Config confToAdd, Type type) {
        this(value, element, type);
        confToAdd.addValue(this);
    }
    public JsonElement getAsJson() {
        return GSON.toJsonTree(value, type);
    }
    public ConfigValue<E> set(E val) {
        this.value = val;
        return this;
    }
    public E get() {
        return this.value;
    }

    @Override
    public String toString() {
        return "ConfigValue{" +
                "element='" + element + '\'' +
                ", value=" + value +
                '}';
    }
}
