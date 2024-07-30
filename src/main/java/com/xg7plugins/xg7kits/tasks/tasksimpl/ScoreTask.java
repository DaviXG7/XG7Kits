package com.xg7plugins.xg7kits.tasks.tasksimpl;

import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.events.EventManager;
import com.xg7plugins.xg7kits.scores.Bossbar;
import com.xg7plugins.xg7kits.scores.ScoreBoard;
import com.xg7plugins.xg7kits.scores.TabList;
import com.xg7plugins.xg7kits.tasks.Task;
import org.bukkit.Bukkit;

public class ScoreTask extends Task {
    public ScoreTask() {
        super("xg7kscore", Config.getLong(ConfigType.CONFIG, "scores-update"));
    }

    @Override
    public void run() {

        Bukkit.getOnlinePlayers().stream().filter(player -> EventManager.getWorlds().contains(player.getWorld().getName())).forEach(player -> {

            if (Integer.parseInt(Bukkit.getServer().getVersion().split("\\.")[1].replace(")", "")) >= 9) Bossbar.updateTitle();
            ScoreBoard.set(player);
            TabList.sendTablist(player, Config.getList(ConfigType.CONFIG, "tablist.header"), Config.getList(ConfigType.CONFIG, "tablist.footer"));

        });
    }
}
