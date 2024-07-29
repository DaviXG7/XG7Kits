package com.xg7plugins.xg7kits.events.actions;

import com.cryptomorin.xseries.XMaterial;
import com.xg7plugins.xg7menus.api.menus.InventoryItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ActionType {
    TITLE,
    GAMEMODE,
    TP,
    BROADCAST,
    SUMMON,
    EFFECT,
    COMMAND,
    CONSOLE,
    MESSAGE,
    SOUND,
    FIREWORK;

    public static List<InventoryItem> getIcons() {
        List<InventoryItem> items = new ArrayList<>();

        items.add(new InventoryItem(XMaterial.OAK_SIGN.parseItem(), "§6Title action", Arrays.asList("§9Description: §rSends a title to a player", "§9Usage: §7§o\"[TITLE] title, subtitle\""), 1, -1));
        items.add(new InventoryItem(XMaterial.GRASS_BLOCK.parseItem(), "§6Gamemode action", Arrays.asList("§9Description: §rChanges the gamemode of a player", "§9Usage: §7§o\"[GAMEMODE] gamemode value\""), 1, -1));
        items.add(new InventoryItem(XMaterial.ENDERMAN_SPAWN_EGG.parseItem(), "§6Tp action", Arrays.asList("§9Description: §rTeleports a player to a location", "§9Usage: §7§o\"[TP] world, x, y, z, (yaw), (pitch)\""), 1, -1));
        items.add(new InventoryItem(XMaterial.PAPER.parseItem(), "§6Broadcast action", Arrays.asList("§9Description: §rSends a message to all players", "§9Usage: §7§o\"[BROADCAST] message\""), 1, -1));
        items.add(new InventoryItem(XMaterial.SKELETON_SKULL.parseItem(), "§6Summon action", Arrays.asList("§9Description: §rSummons an entity", "§9Usage: §7§o\"[SUMMON] entity type\""), 1, -1));
        items.add(new InventoryItem(XMaterial.COMMAND_BLOCK.parseItem(), "§6Command action", Arrays.asList("§9Description: §rMakes a player execute a command", "§9Usage: §7§o\"[COMMAND] command\""), 1, -1));
        items.add(new InventoryItem(XMaterial.COMMAND_BLOCK.parseItem(), "§6Console action", Arrays.asList("§9Description: §rMakes the console execute a command", "§9Usage: §7§o\"[CONSOLE] command\""), 1, -1));
        items.add(new InventoryItem(XMaterial.WRITABLE_BOOK.parseItem(), "§6Message action", Arrays.asList("§9Description: §rSends a message to a player", "§9Usage: §7§o\"[MESSAGE] message\""), 1, -1));
        items.add(new InventoryItem.SkullInventoryItem("§6Sound action", Arrays.asList("§9Description: §rPlay a sound on a player", "§9Usage: §7§o\"[SOUND] sound type\""), 1, -1).setValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVjYjQ5Y2NjYzEzNmIyZjQ3OTJhYTE5MDY3ZGM2NDVhNGVmYTEyYzM3NzQxM2QxOGNkMjEyNzM4YjE5NjlhYSJ9fX0="));
        items.add(new InventoryItem(XMaterial.FIREWORK_ROCKET.parseItem(), "§6Firework action", Arrays.asList("§9Description: §rSpawns a firework on a player", "§9Usage: §7§o\"[FIREWORK] ball type, hex: color, hex: colorfade, logic: trail, logic: flicker, power\""), 1, -1));

        items.add(new InventoryItem.SkullInventoryItem("§6Modifier Center", Arrays.asList("§9Description: §rCenter a message", "§9Example usage: §7§o\"[MESSAGE] [CENTER] a center message!\""), 1, -1).setValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjg4OTViYTZhZjAyNmEwZDE5NTJiYzEwYzFmY2IzZWM0NjgzMjM3NjdiYzU2ZjkxYTVhNWI1Y2YzYTYzMzAwZSJ9fX0="));
        items.add(new InventoryItem.SkullInventoryItem("§6Modifier Action", Arrays.asList("§9Description: §rSends a action bar", "§9Example usage: §7§o\"[MESSAGE] [ACTION] action bar message!\""), 1, -1).setValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ0MGIwYWNiZjk4YzY3YzU1ZjIwYmMxOWQ5NDkxZGU0OWYzYzhhMjBkZTkwNWUzZmFkNWIzZGU4YjRkYjdiOCJ9fX0="));
        items.add(new InventoryItem.SkullInventoryItem("§6Conditions Permission", Arrays.asList("§9Description: §rExecutes the action if player have permission", "§9Example usage: §7§o\"[MESSAGE] [PERMISSION: permission] Executes if the player has the permission!\"", "§9Example usage: §7§o\"[MESSAGE] [!PERMISSION: permission] Executes if the player don't has the permission!\""), 1, -1).setValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGNmNmRmODBkNWFiZjc1NDlmYzBiZTlkODE3YjEwMzAwZTFjMzY3OTZkMzlmM2I1MDFhZmY4OTA4ZWQ3ODFjZCJ9fX0="));
        items.add(new InventoryItem.SkullInventoryItem("§6Conditions IF", Arrays.asList("§9Description: §rExecutes the action if the result of placeholder is a logic value", "§9Example usage: §7§o\"[MESSAGE] [IF: %placeholer_boolean%] Executes if the boolean is true!\"", "§9Example usage: §7§o\"[MESSAGE] [!IF: %placeholer_boolean%] Executes if the boolean is false!\""), 1, -1).setValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjcyMTI0YTk4OTNmZjg0ODY4ZWZkMThlMzkzOWM5Yjg3ZGU1NzM4M2QxZmM2ZWNlNWYyZGZkN2NjODRlZTRkMCJ9fX0="));

        return items;
    }

}
