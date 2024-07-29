package com.xg7plugins.xg7kits;

import com.xg7plugins.xg7kits.cache.CacheManager;
import com.xg7plugins.xg7kits.commands.CommandManager;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.data.handler.SQLHandler;
import com.xg7plugins.xg7kits.events.EventManager;
import com.xg7plugins.xg7kits.tasks.TaskManager;
import com.xg7plugins.xg7kits.utils.Log;
import com.xg7plugins.xg7kits.utils.Placeholders;
import com.xg7plugins.xg7menus.api.XG7Menus;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public final class XG7Kits extends JavaPlugin {

    @Getter
    private static XG7Kits plugin;

    @Override
    public void onEnable() {
        plugin = this;

        this.getServer().getConsoleSender().sendMessage("Loading...");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "__   __  ___   ______     " + ChatColor.RED + " _____   " + ChatColor.GOLD + "_  __");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "\\ \\ / / / __| |___   /   " + ChatColor.RED + " |  __ \\ " + ChatColor.GOLD + "| |/ /");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + " \\ v / | |  _     / /    " + ChatColor.RED + " | |__) |" + ChatColor.GOLD + "|   / ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + " / . \\ | |_| |   / /     " + ChatColor.RED + " |  _  / " + ChatColor.GOLD + "|   \\ ");
        this.getServer().getConsoleSender().sendMessage(ChatColor.BLUE + "/_/ \\_\\ \\____|  /_/      " + ChatColor.RED + " |_| \\_\\ " + ChatColor.GOLD + "|_|\\_\\");

        try {
            Class.forName("org.spigotmc.SpigotConfig");
        } catch (ClassNotFoundException var4) {
            this.getServer().getConsoleSender().sendMessage("                       SPIGOT NOT DETECTED                     ");
            this.getServer().getConsoleSender().sendMessage("THIS PLUGIN NEEDS SPIGOT TO WORK!                              ");
            this.getServer().getConsoleSender().sendMessage("DOWNLOAD HERE: https://www.spigotmc.org/wiki/spigot-installation/.");
            this.getServer().getConsoleSender().sendMessage("THE PLUGIN WILL DISABLE!                                         ");
            this.getPluginLoader().disablePlugin(this);
            return;
        }

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null) {
            this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "It's recommended to install PlaceholderAPI");
            this.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "to get more resourses!");
        } else {
            new Placeholders().register();
        }
        Log.setEnabled(getConfig().getBoolean("debug"));
        if (Log.isEnabled) Log.warn("DEBUG is enabled, to disable go on config.yml");

        Log.loading("Loading the plugin..");

        XG7Menus.inicialize(this);

        Log.loading("Loading configuration and data...");
        Config.load();
        SQLHandler.connect();

        Log.loading("Loading cache...");
        CacheManager.init();

        Log.loading("Loading events...");
        new EventManager().init();
        if (Integer.parseInt(Bukkit.getServer().getVersion().split("\\.")[1].replace(")", "")) <= 13) EventManager.initPacketEvents();

        Log.loading("Loading commands...");
        new CommandManager().init();

        Log.loading("Loading Tasks...");
        TaskManager.initTimerTasks();

        Log.loading("Loaded!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
