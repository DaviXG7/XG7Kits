package com.xg7plugins.xg7kits.commands;

import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.utils.Text;
import com.xg7plugins.xg7menus.api.menus.InventoryItem;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public interface Command {

    String getName();
    default List<String> getAliasses() {
        return Config.getList(ConfigType.COMMANDS, "commands." + getName() + ".args");
    }
    InventoryItem getIcon();
    String getDescription();
    String getSyntax();
    boolean isOnlyInLobbyWorld();
    default PermissionType getPermission() {
        return PermissionType.DEFAULT;
    }
    default boolean isEnabled() {
        return Config.getBoolean(ConfigType.COMMANDS, "commands." + getName() + ".enabled");
    }
    boolean isOnlyPlayer();
    default List<SubCommand> getSubCommands() {
        return new ArrayList<>();
    }
    default boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length == 0) {
            Text.send(Config.getString(ConfigType.MESSAGES, "commands.syntax-error").replace("[SYNTAX]", getSyntax()), sender);
            return true;
        }
        SubCommand subcommand = getSubCommands().stream().filter(subCommand -> subCommand.getName().equals(args[0])).findFirst().orElse(null);
        if (subcommand != null) {
            subcommand.onCommand(sender,command,label,args);
            return true;
        }
        Text.send(Config.getString(ConfigType.MESSAGES, "commands.syntax-error").replace("[SYNTAX]", getSyntax()), sender);
        return true;
    }
    List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args);

}
