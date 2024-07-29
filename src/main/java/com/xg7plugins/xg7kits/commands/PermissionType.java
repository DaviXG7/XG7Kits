package com.xg7plugins.xg7kits.commands;

import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public enum PermissionType {

    DEFAULT(""),

    ADMIN("*"),

    ANTITAB_BYPASS("antitab.bypass"),
    ANTITAB_PLUGIN_BYPASS("antitab.plugin.bypass"),

    COMMAND("command.*"),

    BUG("xg7lobby.command.bug"),
    SUGGEST("xg7lobby.command.suggest"),

    RELOAD("command.reload.*"),
    RELOAD_DB("command.reload.db"),
    RELOAD_CONFIG("command.reload.config"),
    RELOAD_CACHE("command.reload.cache"),
    RELOAD_TASK("command.reload.task"),
    RELOAD_MENUS("command.reload.menus");

    final String perm;

    public String getPerm() {
        return "xg7lobby." + perm;
    }

    PermissionType(String perm) {
        this.perm = perm;
    }

    public static void register() {

        Arrays.stream(PermissionType.values()).forEach(type -> Bukkit.getPluginManager().addPermission(new Permission(type.getPerm())));

        List<PermissionType> parents = Arrays.stream(PermissionType.values()).filter(type -> type.perm.endsWith(".*")).collect(Collectors.toList());

        parents.forEach(parent -> {
            List<PermissionType> children = Arrays.stream(PermissionType.values())
                    .filter(child -> child.perm.startsWith(parent.perm.substring(0, parent.perm.length() - 1)) && child != parent)
                    .collect(Collectors.toList());

            Permission parentPerm = Objects.requireNonNull(Bukkit.getPluginManager().getPermission(parent.getPerm()));
            children.forEach(child -> parentPerm.getChildren().put(child.getPerm(), true));
            parentPerm.recalculatePermissibles();
        });

    }

}
