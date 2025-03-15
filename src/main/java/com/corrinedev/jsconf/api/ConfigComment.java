package com.corrinedev.jsconf.api;

public class ConfigComment extends ConfigValue<Character>{
    public ConfigComment(String comment) {
        super(' ', comment, Character.class);
    }

    public ConfigComment(String comment, String confToAdd) {
        super(' ', comment, confToAdd, Character.class);
    }

    public ConfigComment(String comment, Config confToAdd) {
        super(' ', comment, confToAdd, Character.class);
    }

    @Override
    public Character get() {
        return ' ';
    }

    @Override
    public ConfigValue<Character> set(Character val) {
        return this;
    }
}
