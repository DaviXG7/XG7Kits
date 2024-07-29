package com.xg7plugins.xg7kits.tasks;

import com.xg7plugins.xg7kits.XG7Kits;
import com.xg7plugins.xg7kits.utils.Log;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TaskManager {

    private final static HashMap<String, Integer> tasksRunning = new HashMap<>();

    public static void initTimerTasks() {

        Bukkit.getOnlinePlayers().forEach(p -> {

        });

        List<Task> taskList = new ArrayList<>();

        taskList.forEach(TaskManager::addTask);


    }

    public static void addTask(Task task) {
        int taskid = Bukkit.getServer().getScheduler().runTaskTimer(
                XG7Kits.getPlugin(),
                task::run,
                0,
                task.getDelay()
        ).getTaskId();

        tasksRunning.put(task.getName(), taskid);
    }

    public static void addTaskAsync(Task task) {
        int taskid = Bukkit.getServer().getScheduler().runTaskTimerAsynchronously(
                XG7Kits.getPlugin(),
                task::run,
                0,
                task.getDelay()
        ).getTaskId();

        tasksRunning.put(task.getName(), taskid);
    }

    public static void cancelTask(String name) {
        if (tasksRunning.get(name) == null) return;
        Bukkit.getScheduler().cancelTask(tasksRunning.get(name));
        tasksRunning.remove(name);
    }

    public static void cancelAll() {
        Log.info("Cancelling tasks...");
        tasksRunning.forEach((key, value) -> Bukkit.getScheduler().cancelTask(value));
        tasksRunning.clear();
        Log.info("Cancelled!");
    }
}