package com.xg7plugins.xg7kits.data.handler;

import com.xg7plugins.xg7kits.XG7Kits;
import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.utils.Log;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class Config {

    private static final HashMap<ConfigType, FileConfiguration> configs = new HashMap<>();
    @Getter
    private static final List<FileConfiguration> menus = new ArrayList<>();

    public static void load() {

        Log.info("Loading config files...");
        File config = new File(XG7Kits.getPlugin().getDataFolder(), "config.yml");
        File commands = new File(XG7Kits.getPlugin().getDataFolder(), "commands.yml");
        File selector = new File(XG7Kits.getPlugin().getDataFolder(), "menus/selector.yml");
        File data = new File(XG7Kits.getPlugin().getDataFolder(), "data/data.yml");
        File messages = new File(XG7Kits.getPlugin().getDataFolder(), "messages.yml");
        File shop = new File(XG7Kits.getPlugin().getDataFolder(), "menus/shop.yml");

        if (!config.exists()) XG7Kits.getPlugin().saveResource("config.yml", false);
        if (!commands.exists()) XG7Kits.getPlugin().saveResource("commands.yml", false);
        if (!selector.exists()) XG7Kits.getPlugin().saveResource("menus/selector.yml", false);
        if (!data.exists()) XG7Kits.getPlugin().saveResource("data/data.yml", false);
        if (!messages.exists()) XG7Kits.getPlugin().saveResource("messages.yml", false);
        if (!shop.exists()) XG7Kits.getPlugin().saveResource("menus/shop.yml", false);

        configs.put(ConfigType.CONFIG, YamlConfiguration.loadConfiguration(config));
        configs.put(ConfigType.COMMANDS, YamlConfiguration.loadConfiguration(commands));
        configs.put(ConfigType.SELECTOR, YamlConfiguration.loadConfiguration(selector));
        configs.put(ConfigType.DATA, YamlConfiguration.loadConfiguration(data));
        configs.put(ConfigType.MESSAGES, YamlConfiguration.loadConfiguration(messages));
        configs.put(ConfigType.SHOP, YamlConfiguration.loadConfiguration(messages));

        Log.fine("Config loaded!");
    }

    public static void load(ConfigType type) {
        Log.info("Loading config " + type.getConfig() + "...");
        File file = new File(XG7Kits.getPlugin().getDataFolder(), type.getConfig());
        if (!file.exists()) XG7Kits.getPlugin().saveResource(type.getConfig(), false);
        configs.put(type, YamlConfiguration.loadConfiguration(file));
        Log.fine("Loaded!");

    }

    public static FileConfiguration getMenuFileById(String id) {
        return menus.stream().filter(file -> file.getString("id").equals(id)).findFirst().orElse(null);
    }

    public static FileConfiguration getConfig(ConfigType configType) {
        return configs.get(configType);
    }
    public static Object get(ConfigType type, String path) {
        return configs.get(type).get(path);
    }

    public static int getInt(ConfigType type, String path) {
        return configs.get(type).getInt(path);
    }

    public static String getString(ConfigType type, String path) {
        return configs.get(type).getString(path);
    }

    public static boolean getBoolean(ConfigType type, String path) {
        return configs.get(type).getBoolean(path);
    }

    public static long getLong(ConfigType type, String path) {
        return configs.get(type).getLong(path);
    }

    public static double getDouble(ConfigType type, String path) {
        return configs.get(type).getDouble(path);
    }

    public static List<String> getList(ConfigType type, String path) {
        return configs.get(type).getStringList(path);
    }
    public static List<Integer> getIntegerList(ConfigType type, String path) {
        return configs.get(type).getIntegerList(path);
    }

    public static Set<String> getConfigurationSections(ConfigType type, String path) {
        if (configs.get(type).getConfigurationSection(path) == null) return null;
        return configs.get(type).getConfigurationSection(path).getKeys(false);
    }

    public static void set(ConfigType type, String path, Object value) {
        configs.get(type).set(path, value);
    }


    public static void reload() {
        Log.info("Reloading configs...");
        load();
        Log.fine("Successful reloaded!");
    }

    public static void reload(ConfigType type) {
        Log.info("Reloading config " + type.getConfig() + "...");
        load(type);
        Log.fine("Loaded!");
    }

    @SneakyThrows
    public static void save() {
        Log.info("Saving config files....");
        for (Map.Entry<ConfigType, FileConfiguration> config : configs.entrySet()) {
            config.getValue().save(new File(XG7Kits.getPlugin().getDataFolder(), config.getKey().getConfig()));
        }
        Log.fine("Successful saved!");
    }

    @SneakyThrows
    public static void save(ConfigType configType) {
        Log.info("Saving config " + configType.getConfig() + "...");
        configs.get(configType).save(new File(XG7Kits.getPlugin().getDataFolder(), configType.getConfig()));
        Log.fine("Loaded!");
    }


}
