package com.corrinedev.jsconf.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import static com.corrinedev.jsconf.api.Config.GSON;

public record ConfigValue<E>(E value, String element) {

    public JsonElement getAsJson() {
        return GSON.toJsonTree(value, value.getClass());
    }
}
