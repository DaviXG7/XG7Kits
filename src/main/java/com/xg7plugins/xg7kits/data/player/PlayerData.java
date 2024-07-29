package com.xg7plugins.xg7kits.data.player;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class PlayerData {

    private UUID id;
    private int kills;
    private int deaths;
    private int killsStreak;

}
