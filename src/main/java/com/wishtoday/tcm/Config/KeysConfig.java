package com.wishtoday.tcm.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class KeysConfig {
    public static Map<String,String> map = new HashMap<>();
    private static final Type TYPE = new TypeToken<Map<String,String>>() {}.getType();
    private static final Path dir = FabricLoader.getInstance().getConfigDir();
    private static File file = new File(dir.toFile(), "keys.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    public static void config() throws IOException {
        if (file.exists()) {
            map = gson.fromJson(new FileReader(file), TYPE);
            if (map.get("APP_KEY").equals("") || map.get("SECRET_KEY").equals("")) {
                throw new RuntimeException();
            }
        } else {
            try(FileWriter writer = new FileWriter(file)){
                map.put("APP_KEY","");
                map.put("SECRET_KEY","");
                gson.toJson(map,writer);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
