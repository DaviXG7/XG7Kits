package com.xg7plugins.xg7kits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.data.player.PlayerData;
import com.xg7plugins.xg7kits.kits.KitModel;
import com.xg7plugins.xg7kits.utils.Log;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CacheManager {

    @Getter
    private static Cache<UUID, PlayerData> sqlCache;
    @Getter
    private static Cache<String, KitModel> kitCache;

    public static void init() {
        sqlCache = CacheBuilder.newBuilder().expireAfterWrite(Config.getLong(ConfigType.CONFIG, "sql-cache-expires"), TimeUnit.MINUTES).build();
        kitCache = CacheBuilder.newBuilder().expireAfterWrite(Config.getLong(ConfigType.CONFIG, "kits.kit-cache-expires"), TimeUnit.MINUTES).build();
        Log.loading("Loaded!");
    }
    public static void put(KitModel kit) {
        kitCache.put(kit.getName(), kit);
    }
    public static void put(PlayerData data) {
        sqlCache.put(data.getId(), data);
    }
    public static void remove(UUID id) {
        sqlCache.invalidate(id);
        sqlCache.cleanUp();
    }
    public static void remove(String s) {
        kitCache.invalidate(s);
        kitCache.cleanUp();
    }
    public static void reloadAll() {
        sqlCache.invalidateAll();
        sqlCache.cleanUp();
        kitCache.invalidateAll();
        kitCache.cleanUp();
    }

}
