package com.xg7plugins.xg7kits.data;

public enum ConfigType {

    CONFIG("config"),
    MESSAGES("messages"),
    DATA("data/data"),
    COMMANDS("commands"),
    SELECTOR("menus/selector"),
    SHOP("menus/shop.yml");

    private String config;

    ConfigType(String config) {
        this.config = config;
    }

    public String getConfig() {
        return this.config + ".yml";
    }

}
