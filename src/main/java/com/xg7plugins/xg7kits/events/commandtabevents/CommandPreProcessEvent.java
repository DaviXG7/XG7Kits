package com.xg7plugins.xg7kits.events.commandtabevents;

import com.xg7plugins.xg7kits.commands.CommandManager;
import com.xg7plugins.xg7kits.commands.PermissionType;
import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.events.Event;
import com.xg7plugins.xg7kits.utils.Text;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.stream.Collectors;

public class CommandPreProcessEvent implements Event {
    @Override
    public boolean isEnabled() {
        return true;
    }

    @EventHandler
    public void onCommandPreProcess(PlayerCommandPreprocessEvent event) {
        Config.getList(ConfigType.CONFIG, "block-commands.commands-blocked").forEach(cmd -> {
            if (event.getMessage().startsWith(cmd) && !event.getPlayer().hasPermission(PermissionType.ANTITAB_BYPASS.getPerm())) {
                event.setCancelled(true);
                Text.send(Config.getString(ConfigType.MESSAGES, "commands.command-blocked"), event.getPlayer());
            }
        });
        if (Config.getBoolean(ConfigType.COMMANDS, "anti-tab")) {
            CommandManager.getCommands().stream().map(cmd -> "/" + cmd.getName()).collect(Collectors.toList()).forEach(cmd -> {
                if (event.getMessage().startsWith(cmd) && !event.getPlayer().hasPermission(PermissionType.ANTITAB_PLUGIN_BYPASS.getPerm())) {
                    event.setCancelled(true);
                    Text.send(Config.getString(ConfigType.MESSAGES, "commands.command-blocked"), event.getPlayer());
                }
            });
        }
    }
}
