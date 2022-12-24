package de.philipp.chatfilter;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {


    private final File folder = new File("plugins/ChatFilter/");
    private final File configFile = new File(folder, "config.yml");

    private final YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);

    public FileUtils() {
        if(!folder.exists()) {
            folder.mkdir();
        }
        try {
            if (!configFile.exists()) {
                configFile.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        config.addDefault("prefix", "&7[&aChatFilter&7]");
        List<String> blacklistedWords = new ArrayList<>();
        blacklistedWords.add("Nuttensohn");
        blacklistedWords.add("hs");
        blacklistedWords.add("ns");
        blacklistedWords.add("hurensohn");
        config.addDefault("blacklist", blacklistedWords);
        config.options().copyDefaults(true);
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.set("blacklist", ChatFilter.getInstance().getBlackList());
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public YamlConfiguration getConfig() {
        return config;
    }
}
