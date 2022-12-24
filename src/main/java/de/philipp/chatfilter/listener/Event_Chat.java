package de.philipp.chatfilter.listener;

import de.philipp.chatfilter.ChatFilter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class Event_Chat implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if(event.getPlayer().hasPermission("chatfilter.bypass")) {
            return;
        }
        String blacklisted = "";
        boolean contains = false;
        for(String s : ChatFilter.getInstance().getBlackList()) {
            if(event.getMessage().toUpperCase().contains(s.toUpperCase())) {
                contains = true;
                blacklisted = s;
            }
        }
        if(contains) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(ChatFilter.getInstance().getPrefix() + "§cAchte auf deine Wortwahl!");
            for(Player all : Bukkit.getOnlinePlayers()) {
                if(all.hasPermission("chatfilter.see")) {
                    all.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDer Spieler §e" + event.getPlayer().getName() + " §chat probiert §3" + blacklisted + " §czu schreiben");
                }
            }
        }
    }

}
