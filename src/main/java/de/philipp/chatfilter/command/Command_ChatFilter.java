package de.philipp.chatfilter.command;

import de.philipp.chatfilter.ChatFilter;
import de.philipp.chatfilter.FileUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Command_ChatFilter implements CommandExecutor {

    public void sendHelp(Player player) {
        player.sendMessage("");
        player.sendMessage("");
        player.sendMessage("§a/Chatfilter list §e-> §2Zeige alle geblacklisteten Wörter.");
        player.sendMessage("§a/Chatfilter add <Wort> §e-> §2Blockiere ein Wort.");
        player.sendMessage("§a/Chatfilter remove <Wort> §e-> §2Erlaube ein bereits Blockiertes Wort.");
        player.sendMessage("§a/Chatfilter reload §e-> §2Läd die Konfig neu.");
        player.sendMessage("");
        player.sendMessage("");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDazu musst du ein Spieler sein!");
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("chatfilter.use")) {
            player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDazu hast du keine Rechte!");
            return false;
        }

        if(args.length == 1) {
            if(args[0].equalsIgnoreCase("reload")) {
                ChatFilter.getInstance().reloadKonfig();
                player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDie Blacklist wurde reloaded");
            } else if(args[0].equalsIgnoreCase("help")) {
                sendHelp(player);
            } else if(args[0].equalsIgnoreCase("list")) {
                if(ChatFilter.getInstance().getBlackList().size() < 1) {
                    player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDie Blacklist ist Leer");
                    return true;
                }
                String msg = "";
                for (int i = 0; i < ChatFilter.getInstance().getBlackList().size(); i++) {
                    if(i == 0) {
                        msg = ChatFilter.getInstance().getBlackList().get(i);
                    } else if(i == ChatFilter.getInstance().getBlackList().size()){
                        msg = msg + ChatFilter.getInstance().getBlackList().get(i) + ".";
                    } else {
                        msg = msg + ", " + ChatFilter.getInstance().getBlackList().get(i);
                    }
                }
                player.sendMessage(ChatFilter.getInstance().getPrefix() + "§aAuf der Blacklist stehen: " + msg);
            } else {
                player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cBenutze /Chatfilter help für alle Commands");
            }
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("add")) {
                if(!ChatFilter.getInstance().getBlackList().contains(args[1].toUpperCase())) {
                    ChatFilter.getInstance().getBlackList().add(args[1].toUpperCase());
                    ChatFilter.getInstance().getFileUtils().saveConfig();
                    player.sendMessage(ChatFilter.getInstance().getPrefix() + "§aDu hast das Wort §e" + args[1] + " §aauf die Blacklist gesetzt.");
                } else {
                    player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDieses Wort steht bereits auf der Blacklist!");
                }
            } else if(args[0].equalsIgnoreCase("remove")) {
                if(ChatFilter.getInstance().getBlackList().contains(args[1].toUpperCase())) {
                    ChatFilter.getInstance().getBlackList().remove(args[1].toUpperCase());
                    player.sendMessage(ChatFilter.getInstance().getPrefix() + "§aDu hast das Wort §e" + args[1] + " §avon der Blacklist genommen.");
                    ChatFilter.getInstance().getFileUtils().saveConfig();

                } else {
                    player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cDieses Wort steht nicht auf der Blacklist!");
                }
            } else {
                player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cBenutze /Chatfilter help für alle Commands");
            }
        } else {
            player.sendMessage(ChatFilter.getInstance().getPrefix() + "§cBenutze /Chatfilter help für alle Commands");
        }
        return false;
    }
}
