package com.xg7plugins.xg7kits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xg7plugins.xg7kits.data.ConfigType;
import com.xg7plugins.xg7kits.data.handler.Config;
import com.xg7plugins.xg7kits.data.player.PlayerData;
import com.xg7plugins.xg7kits.utils.Log;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CacheManager {

    @Getter
    private static Cache<UUID, PlayerData> sqlCache;

    public static void init() {
        sqlCache = CacheBuilder.newBuilder().expireAfterWrite(Config.getLong(ConfigType.CONFIG, "sql-cache-expires"), TimeUnit.MINUTES).build();
        Log.loading("Loaded!");
    }

    public static void put(UUID id, PlayerData data) {
        sqlCache.put(data.getId(), data);

    }

    public static void remove(UUID id, CacheType type) {
        sqlCache.invalidate(id);
        sqlCache.cleanUp();
    }
    public static void reloadAll() {
        sqlCache.invalidateAll();
        sqlCache.cleanUp();
    }

}
