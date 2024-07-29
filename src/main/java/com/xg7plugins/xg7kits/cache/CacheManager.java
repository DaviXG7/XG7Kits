package com.xg7plugins.xg7randomkits.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.xg7plugins.xg7randomkits.data.ConfigType;
import com.xg7plugins.xg7randomkits.data.handler.Config;
import com.xg7plugins.xg7randomkits.data.player.PlayerData;
import com.xg7plugins.xg7randomkits.utils.Log;
import lombok.Getter;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CacheManager {
    @Getter
    private static Cache<UUID, Long> selectorCache;
    @Getter
    private static Cache<UUID, Long> lobbyCache;
    @Getter
    private static Cache<UUID, Long> pvpCache;
    @Getter
    private static Cache<UUID, PlayerData> sqlCache;
    @Getter
    private static Cache<UUID, Long> spamChache;

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

}
