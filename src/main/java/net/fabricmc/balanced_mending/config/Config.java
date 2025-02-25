package net.fabricmc.balanced_mending.config;

import com.google.gson.Gson;
import net.fabricmc.balanced_mending.BalancedMending;
import net.fabricmc.loader.api.FabricLoader;

import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public final class Config {
    private static final Path CONFIG_DIR = FabricLoader.getInstance().getConfigDir();
    private static final Path CONFIG_PATH = CONFIG_DIR.resolve("balanced_mending.json");

    private static ConfigData configCurrent;

    public static ConfigData get() {
        if (configCurrent == null) load();
        return configCurrent;
    }

    public static void load() {
        var file = CONFIG_PATH.toFile();

        try {
            Files.createDirectories(CONFIG_DIR);

            if (file.exists()) {
                configCurrent = new Gson().fromJson(new FileReader(file), ConfigData.class);
            } else {
                configCurrent = new ConfigData();
            }
        } catch (Exception e) {
            configCurrent = new ConfigData();
            BalancedMending.LOGGER.error("Failed to load config file", e);
        }

        save();
    }

    public static void save() {
        try (FileWriter file = new FileWriter(CONFIG_PATH.toFile())) {
            var json = new Gson().toJson(configCurrent);
            file.write(json);
            file.flush();
        } catch (Exception e) {
            BalancedMending.LOGGER.error("Failed to save config file", e);
        }
    }
}
