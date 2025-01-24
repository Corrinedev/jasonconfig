package com.corrinedev.jsconf.api;

public class ConfigComment extends ConfigValue<Character>{
    public ConfigComment(String comment) {
        super(' ', comment);
    }

    public ConfigComment(String comment, String confToAdd) {
        super(' ', comment, confToAdd);
    }

    public ConfigComment(String comment, Config confToAdd) {
        super(' ', comment, confToAdd);
    }

    @Override
    public Character get() {
        return ' ';
    }
}
