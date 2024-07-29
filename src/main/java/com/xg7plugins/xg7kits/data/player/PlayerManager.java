package com.xg7plugins.xg7randomkits.data.player;

import com.xg7plugins.xg7randomkits.cache.CacheManager;
import com.xg7plugins.xg7randomkits.data.handler.SQLHandler;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerManager {

    public synchronized static PlayerData getPlayerData(UUID uuid) {
        if (CacheManager.getSqlCache().asMap().containsKey(uuid)) return CacheManager.getSqlCache().asMap().get(uuid);

        List<Map<String, Object>> playerDataMap = SQLHandler.select("SELECT * FROM players WHERE id = ?", uuid.toString());

        if (playerDataMap.isEmpty()) return null;

        return new PlayerData(UUID.fromString((String) playerDataMap.get(0).get("id")), (Integer) playerDataMap.get(0).get("kills"),(Integer) playerDataMap.get(0).get("deaths"),(Integer) playerDataMap.get(0).get("killsstreak"));
    }

    public synchronized static PlayerData createPlayerData(UUID id) {
        PlayerData tempdata = getPlayerData(id);
        if (tempdata != null) return tempdata;

        PlayerData data = new PlayerData(id, 0,0,0);

        SQLHandler.update("INSERT INTO players (id, kills, deaths, killsstreak) VALUES (?, ?, ?)", id, 0,0,0);

        CacheManager.put(data.getId(), data);

        return data;
    }



}
