package com.xg7plugins.xg7kits.events.actions;

import com.cryptomorin.xseries.XEntityType;
import com.cryptomorin.xseries.XPotion;
import com.cryptomorin.xseries.XSound;
import com.xg7plugins.xg7kits.data.player.PlayerData;
import com.xg7plugins.xg7kits.data.player.PlayerManager;
import com.xg7plugins.xg7kits.utils.Log;
import com.xg7plugins.xg7kits.utils.Text;
import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.potion.PotionEffect;

import java.util.Objects;

public class Action {

    public static void execute(String text, Player player) {
        ActionType type = null;
        for (ActionType actionType : ActionType.values()) {
            if (text.startsWith("[" + actionType.name() + "] ")) {
                type = actionType;
                text = text.replace("[" + actionType.name() + "] ", "");
                break;
            }
        }
        if (type == null) {
            Log.severe("Action with name " + text.split(" ")[0] + " doesn't exist!");
            return;
        }
        if (text.startsWith("[PERMISSION: ")) {
            String perm = text.substring(13, text.indexOf("] "));
            if (!player.hasPermission(perm)) return;
            text = text.replace("[PERMISSION: " + perm + "] ", "");
        }
        if (text.startsWith("[!PERMISSION: ")) {
            String perm = text.substring(14, text.indexOf("] "));
            if (player.hasPermission(perm)) return;
            text = text.replace("[!PERMISSION: " + perm + "] ", "");
        }
        if (text.startsWith("[IF: ")) {
            String condition = text.substring(5, text.indexOf("] "));
            try {
                if (!Boolean.parseBoolean(Text.setPlaceholders(condition, player))) return;
            } catch (Exception ignored) {}
            text = text.replace("[IF: " + condition + "] ", "");
        }
        if (text.startsWith("[!IF: ")) {
            String condition = text.substring(6, text.indexOf("] "));
            try {
                if (Boolean.parseBoolean(Text.setPlaceholders(condition, player))) return;
            } catch (Exception ignored) {}
            text = text.replace("[!IF: " + condition + "] ", "");
        }

        text = text.replace("[PLAYER]", player.getName());

        String[] textSplited = text.split(", ");
        PlayerData data = PlayerManager.getPlayerData(player.getUniqueId());

        switch (type) {
            case TITLE:

                if (textSplited.length != 2 && textSplited.length != 5) {
                    Log.severe("Action TITLE needs 5 arguments: title, subtitle Optional: (fade in ticks, stay time ticks, fade out ticks)");
                    return;
                }

                if (textSplited.length == 2) {
                    player.sendTitle(Text.getFormatedText(player, textSplited[0]), Text.getFormatedText(player, textSplited[1]));
                    return;
                }
                player.sendTitle(Text.getFormatedText(player, textSplited[0]), Text.getFormatedText(player, textSplited[1]), Integer.parseInt(textSplited[2]), Integer.parseInt(textSplited[3]), Integer.parseInt(textSplited[4]));

                return;

            case GAMEMODE:

                player.setGameMode(GameMode.valueOf(text.toUpperCase()));

                return;
            case TP:

                if (textSplited.length != 6) {
                    Log.severe("Action TP needs 6 arguments: world, x, y, z, yaw, pitch");
                    return;
                }
                player.teleport(new Location(Bukkit.getWorld(textSplited[0]), Double.parseDouble(textSplited[1]), Double.parseDouble(textSplited[2]), Double.parseDouble(textSplited[3]), Float.parseFloat(textSplited[4]), Float.parseFloat(textSplited[5])));

                return;
            case BROADCAST:

                Bukkit.broadcastMessage(Text.getFormatedText(player, text));

                return;
            case SUMMON:

                player.getWorld().spawnEntity(player.getLocation(), XEntityType.valueOf(text.toUpperCase()).get());

                return;
            case EFFECT:

                if (textSplited.length != 3) {
                    Log.severe("Action EFFECT needs 3 arguments: potion effect type, duration, amplifier");
                    return;
                }

                player.addPotionEffect(new PotionEffect(Objects.requireNonNull(XPotion.valueOf(textSplited[0].toUpperCase()).getPotionEffectType()), Integer.parseInt(textSplited[1]), Integer.parseInt(textSplited[2]) - 1));

                return;
            case COMMAND:

                player.performCommand(text);

                return;
            case CONSOLE:

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), text);

                return;
            case MESSAGE:

                Text.send(text, player);

                return;
            case SOUND:

                if (textSplited.length != 3) {
                    Log.severe("Action SOUND needs 3 arguments: sound type, volume, pitch");
                    return;
                }

                player.playSound(player.getLocation(), Objects.requireNonNull(XSound.valueOf(textSplited[0].toUpperCase()).parseSound()), Float.parseFloat(textSplited[1]), Float.parseFloat(textSplited[2]));

                return;

            case FIREWORK:

                if (textSplited.length != 6) {
                    Log.severe("Action FIREWORK needs 6 arguments: type, hex: color, hex: colorfade, logic: trail, logic: flicker, power");
                    return;
                }
                Firework firework = (Firework) player.getWorld().spawnEntity(player.getLocation(), XEntityType.FIREWORK_ROCKET.get());

                FireworkMeta fireworkMeta = firework.getFireworkMeta();

                FireworkEffect.Builder builder = FireworkEffect.builder();
                builder.with(FireworkEffect.Type.valueOf(textSplited[0]));
                builder.withColor(Color.fromRGB(Integer.parseInt(textSplited[1].replace("#", ""), 16)));
                builder.withFade(Color.fromRGB(Integer.parseInt(textSplited[2].replace("#", ""), 16)));
                builder.trail(Boolean.parseBoolean(textSplited[3]));
                builder.flicker(Boolean.parseBoolean(textSplited[4]));

                FireworkEffect effect = builder.build();
                fireworkMeta.addEffect(effect);
                fireworkMeta.setPower(Integer.parseInt(textSplited[5]));

                firework.setFireworkMeta(fireworkMeta);

                try {
                    firework.detonate();
                } catch (Exception ignored) {}

        }
    }

}
