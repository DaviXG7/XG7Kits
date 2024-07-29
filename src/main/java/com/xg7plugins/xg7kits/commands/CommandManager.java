package com.xg7plugins.xg7randomkits.commands;

import com.xg7plugins.xg7randomkits.XG7RandomKits;
import com.xg7plugins.xg7randomkits.data.ConfigType;
import com.xg7plugins.xg7randomkits.data.handler.Config;
import com.xg7plugins.xg7randomkits.events.EventManager;
import com.xg7plugins.xg7randomkits.utils.Log;
import com.xg7plugins.xg7randomkits.utils.Text;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandManager implements CommandExecutor, TabCompleter, Listener {

    @Getter
    private static final List<com.xg7plugins.xg7randomkits.commands.Command> commands = new ArrayList<>();

    @SneakyThrows
    public void init() {
        Log.info("Loading commands...");
        PermissionType.register();

        Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
        commandMapField.setAccessible(true);
        CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());

        for (com.xg7plugins.xg7randomkits.commands.Command command : commands) {
            Constructor<PluginCommand> constructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            constructor.setAccessible(true);
            PluginCommand pluginCommand = constructor.newInstance(command.getName(), XG7RandomKits.getPlugin());
            pluginCommand.setExecutor(this);
            pluginCommand.setUsage(command.getSyntax());
            pluginCommand.setTabCompleter(this);
            pluginCommand.setDescription(command.getDescription());
            pluginCommand.setAliases(command.getAliasses());
            commandMap.register(command.getName(), pluginCommand);
        }

        XG7RandomKits.getPlugin().getServer().getPluginManager().registerEvents(this, XG7RandomKits.getPlugin());

        Log.fine("Commands loaded!");
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        com.xg7plugins.xg7randomkits.commands.Command command1 = commands.stream().filter(cmd -> cmd.getName().equals(command.getName())).findFirst().get();

        if (!commandSender.hasPermission(command1.getPermission().getPerm()) && !command1.getPermission().equals(PermissionType.DEFAULT)) {
            Text.send(Config.getString(ConfigType.MESSAGES, "commands.no-permission"), commandSender);
            return true;
        }
        if (!(commandSender instanceof Player)) {
            if (command1.isOnlyPlayer()) {
                Text.send(Config.getString(ConfigType.MESSAGES, "commands.not-a-player"), commandSender);
                return true;
            }
        }
        if (commandSender instanceof Player) {
            if (command1.isOnlyInLobbyWorld() && !EventManager.getWorlds().contains(((Player) commandSender).getWorld().getName())) {
                Text.send(Config.getString(ConfigType.MESSAGES, "commands.not-in-world"), commandSender);
                return true;
            }
        }
        return command1.onCommand(commandSender,command,s,strings);
    }

    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        com.xg7plugins.xg7randomkits.commands.Command command1 = commands.stream().filter(cmd -> cmd.getName().equals(command.getName())).findFirst().get();
        return command1.onTabComplete(commandSender,command,s,strings);
    }
}
