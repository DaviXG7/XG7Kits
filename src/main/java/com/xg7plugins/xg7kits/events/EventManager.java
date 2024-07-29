package com.xg7plugins.xg7randomkits.events;

import com.xg7plugins.xg7randomkits.XG7RandomKits;
import com.xg7plugins.xg7randomkits.data.ConfigType;
import com.xg7plugins.xg7randomkits.data.handler.Config;
import com.xg7plugins.xg7randomkits.events.commandtabevents.PluginTabCompleteEventNew;
import com.xg7plugins.xg7randomkits.utils.Log;
import com.xg7plugins.xg7randomkits.utils.PacketEvents;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EventManager implements Listener {

    private static final List<Event> events = new ArrayList<>();
    @Getter
    private static final List<PacketPlayEvent> packetEvents = new ArrayList<>();

    @Getter
    private static List<String> worlds;

    public static void reload() {
        Log.info("Reloading events...");
        HandlerList.unregisterAll(XG7RandomKits.getPlugin());
        new EventManager().init();
    }

    public void init() {

        if (Integer.parseInt(Bukkit.getServer().getVersion().split("\\.")[1].replace(")", "")) >= 14) events.add(new PluginTabCompleteEventNew());

        worlds = Config.getList(ConfigType.CONFIG, "enabled-worlds");

        XG7RandomKits.getPlugin().getServer().getPluginManager().registerEvents(this, XG7RandomKits.getPlugin());

        events.stream().filter(Event::isEnabled).collect(Collectors.toList()).forEach(event -> XG7RandomKits.getPlugin().getServer().getPluginManager().registerEvents(event, XG7RandomKits.getPlugin()));

        Log.loading("Events loaded!");
    }

    public static void initPacketEvents() {
        Log.loading("Loading packet events...");

        Bukkit.getOnlinePlayers().forEach(PacketEvents::create);
        Log.loading("Packets loaded!");
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (worlds.contains(event.getPlayer().getWorld().getName())) events.stream().filter(event1 -> event1 instanceof JoinQuitEvent && event1.isEnabled()).collect(Collectors.toList()).forEach(i -> ((JoinQuitEvent)i).onJoin(event));
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (worlds.contains(event.getPlayer().getWorld().getName())) events.stream().filter(event1 -> event1 instanceof JoinQuitEvent && event1.isEnabled()).collect(Collectors.toList()).forEach(i -> ((JoinQuitEvent)i).onQuit(event));
    }
    @EventHandler
    public void onWorldChange(PlayerTeleportEvent event) {
        if (worlds.contains(event.getFrom().getWorld().getName()) && !worlds.contains(event.getTo().getWorld().getName())) {
            events.stream().filter(event1 -> event1 instanceof JoinQuitEvent && event1.isEnabled()).collect(Collectors.toList()).forEach(i -> ((JoinQuitEvent)i).onWorldLeave(event.getPlayer()));
        }
        if (!worlds.contains(event.getFrom().getWorld().getName()) && worlds.contains(event.getTo().getWorld().getName())) {
            events.stream().filter(event1 -> event1 instanceof JoinQuitEvent && event1.isEnabled()).collect(Collectors.toList()).forEach(i -> ((JoinQuitEvent)i).onWorldJoin(event.getPlayer()));
        }
    }

}
