package de.philipp.chatfilter;

import de.philipp.chatfilter.command.Command_ChatFilter;
import de.philipp.chatfilter.listener.Event_Chat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class ChatFilter extends JavaPlugin {


    private static ChatFilter instance;
    private FileUtils fileUtils;
    private String prefix;
    private List<String> blackList;

    public FileUtils getFileUtils() {
        return fileUtils;
    }

    public List<String> getBlackList() {
        return blackList;
    }

    public String getPrefix() {
        return prefix;
    }

    public static ChatFilter getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        fileUtils = new FileUtils();

        prefix = ChatColor.translateAlternateColorCodes('&', fileUtils.getConfig().getString("prefix") + " ");
        blackList = fileUtils.getConfig().getStringList("blacklist");

        getCommand("ChatFilter").setExecutor(new Command_ChatFilter());
        Bukkit.getPluginManager().registerEvents(new Event_Chat(), this);
        System.out.println("------------------");
        System.out.println("ChatFilter von CuzImPhilipp");
        System.out.println("Aktiviert");
        System.out.println("------------------");
    }

    @Override
    public void onDisable() {
        System.out.println("------------------");
        System.out.println("ChatFilter von CuzImPhilipp");
        System.out.println("Deaktiviert");
        System.out.println("------------------");
    }

    public void reloadKonfig() {
        fileUtils = new FileUtils();
        prefix = ChatColor.translateAlternateColorCodes('&', fileUtils.getConfig().getString("prefix") + " ");
        blackList = fileUtils.getConfig().getStringList("blacklist");
    }
}
