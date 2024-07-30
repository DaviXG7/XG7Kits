package com.xg7plugins.xg7kits.kits;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.xg7plugins.xg7menus.api.menus.InventoryItem;
import lombok.SneakyThrows;

public class KitManager {

    public void init() {

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(InventoryItem.class, new ItemAdapter())
                .create();



    }


    static class ItemAdapter extends TypeAdapter<InventoryItem> {

        @SneakyThrows
        @Override
        public void write(JsonWriter out, InventoryItem value) {
            out.value(value.toString());
        }

        @SneakyThrows
        @Override
        public InventoryItem read(JsonReader in)  {
            return InventoryItem.fromString(in.nextString());
        }
    }

}
