package com.xg7plugins.xg7randomkits.events.commandtabevents;

import com.xg7plugins.xg7randomkits.commands.PermissionType;
import com.xg7plugins.xg7randomkits.data.ConfigType;
import com.xg7plugins.xg7randomkits.data.handler.Config;
import com.xg7plugins.xg7randomkits.events.PacketPlayEvent;
import com.xg7plugins.xg7randomkits.utils.NMSUtil;
import lombok.SneakyThrows;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.Arrays;

public class TabCompleteEvent implements PacketPlayEvent.PacketPlayOutEvent {

    @Override
    public String[] getPacketsNames() {
        return new String[]{"PacketPlayOutTabComplete"};
    }

    @SneakyThrows
    @Override
    public Object out(Player player, Object packet) {
        Class<?> packetPlayOutTabCompleteClass = NMSUtil.getNMSClass("PacketPlayOutTabComplete");
        Field suggestionsField = packetPlayOutTabCompleteClass.getDeclaredField("a");
        suggestionsField.setAccessible(true);
        Object suggestions = suggestionsField.get(packet);

        if (suggestions instanceof String[]) {
            String[] suggestionsArray = (String[]) suggestions;
            if (player.hasPermission(PermissionType.ANTITAB_BYPASS.getPerm())) return packet;

            for (String commands : Config.getList(ConfigType.CONFIG, "block-commands.commands-blocked")) {
                suggestionsArray = Arrays.stream(suggestionsArray)
                        .filter(suggestion -> !suggestion.startsWith(commands))
                        .toArray(String[]::new);

            }
            suggestionsField.set(packet, suggestionsArray);
        }
        return packet;
    }

    @Override
    public boolean isEnabled() {
        return Config.getBoolean(ConfigType.CONFIG, "block-commands.anti-tab");
    }
}
