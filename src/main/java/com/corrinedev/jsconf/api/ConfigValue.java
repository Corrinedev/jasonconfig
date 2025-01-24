package com.corrinedev.jsconf.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static com.corrinedev.jsconf.api.Config.GSON;

public class ConfigValue<E> {
    public final String element;
    private E value;
    public ConfigValue(E value, String element) {
        this.value = value;
        this.element = element;
    }
    public ConfigValue(E value, String element, String confToAdd) {
        this.value = value;
        this.element = element;
        LoadedConfigs.CONFIGS.get(confToAdd).addValue(this);
    }
    public ConfigValue(E value, String element, Config confToAdd) {
        this.value = value;
        this.element = element;
        confToAdd.addValue(this);
    }
    public JsonElement getAsJson() {
        return GSON.toJsonTree(value, value.getClass());
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
