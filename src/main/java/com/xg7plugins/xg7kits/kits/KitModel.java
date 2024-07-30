package com.xg7plugins.xg7kits.kits;

import com.xg7plugins.xg7menus.api.menus.InventoryItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class KitModel {

    private String name;
    private List<InventoryItem> items;
    private String permission;
    private long cooldown;
    private InventoryItem icon;


}
