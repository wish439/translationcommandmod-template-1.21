package com.wishtoday.tcm.Config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.reflect.TypeToken;
import com.wishtoday.tcm.TranslationCommandMod;
import net.fabricmc.loader.api.FabricLoader;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class KeysConfig {
    public static Map<String, String> map = new HashMap<>();
    private static final Type TYPE = new TypeToken<Map<String, String>>() {
    }.getType();
    private static final Path dir = FabricLoader.getInstance().getConfigDir();
    private static final File FILE = new File(dir.toFile(), "keys.json");
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static void config() {
        if (FILE.exists()) {
            try (FileReader reader = new FileReader(FILE)) {
                map = gson.fromJson(reader, TYPE);
            } catch (IOException e) {
                TranslationCommandMod.LOGGER.error("Load APP_KEY failed:{}", e.getMessage());
            } catch (JsonIOException e) {
                TranslationCommandMod.LOGGER.error("Load APP_KEY failed JSON:{}", e.getMessage());
                write();
            }
            if (map.get("APP_KEY").isEmpty() || map.get("SECRET_KEY").isEmpty()) {
                TranslationCommandMod.LOGGER.error("Load APP_KEY failed");
                TranslationCommandMod.LOGGER.error("TranslationCommandMod closed");
            }
            return;
        }
        write();
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void write() {
        FILE.delete();
        try (FileWriter writer = new FileWriter(FILE)) {
            map.put("APP_KEY", "");
            map.put("SECRET_KEY", "");
            gson.toJson(map, writer);
        } catch (IOException e) {
            TranslationCommandMod.LOGGER.error("write keys failed:{}", e.getMessage());
        }
    }
}
