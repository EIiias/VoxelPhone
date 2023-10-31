package me.elias.voxelphone.Commands;

import me.elias.voxelphone.ItemMetaManipulation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PhoneCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Switch for amount of arguments provided by command sender
        switch (args.length) {
            //Send usage if argument amount is invalid/unused
            default -> {
                sendUsage(sender);
            }
            case 1 -> {
                //Send usage if command sender uses "help" argument
                if (args[0].equalsIgnoreCase("help")) {
                    sendUsage(sender);
                }
            }
            case 2 -> {
                //If first argument is "give"
                if (args[0].equalsIgnoreCase("give")) {
                    //Used to determine if player (second argument) is valid
                    boolean validPlayer = false;
                    //Loop through each online player
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        //Check if player is valid
                        if (p.getName().equals(args[1])) {
                            validPlayer = true;
                            //Check if targeted player's inventory is full
                            if (p.getInventory().firstEmpty() != -1) {
                                //Give targeted player "phone item"
                                ItemStack phone = new ItemStack(Material.BLACK_CANDLE);
                                //Rename item to "Phone"
                                ItemMetaManipulation.renameItem(phone, "Phone", ChatColor.GOLD);
                                //Add metadata to detect if it is a phone item
                                ItemMetaManipulation.addBooleanPDS(phone, "phone", true);
                                //Add it to the players inventory
                                p.getInventory().addItem(phone);
                            } else {
                                sender.sendMessage(ChatColor.RED + "Target inventory is full! Item could not be given!");
                            }
                        }
                    }
                    //if the player (second argument) was invalid send error and display usage
                    if (!validPlayer) {
                        sender.sendMessage(ChatColor.RED + "Target player not found!");
                        sendUsage(sender);
                    }
                }
            }
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        ArrayList<String> argSuggestions = new ArrayList<>();

        //Determine which argument (number) the player is currently writing
        switch (args.length) {
            case 1 -> {

                //Arguments suggested for first argument
                argSuggestions.add("give");
                argSuggestions.add("help");
            }
            case 2 -> {
                //Arguments suggested for second argument
                //Looping through each online player and adding each of their name to the arg suggestion list
                for (Player player : Bukkit.getOnlinePlayers()) {
                    argSuggestions.add(player.getName());
                }
            }
        }

        //loop through each argument suggested
        //Check if the arg suggestions start with what the player already typed
        //Remove arguments suggested if they don't start with what the command sender already typed
        argSuggestions.removeIf(argSuggestion -> !argSuggestion.startsWith(args[args.length - 1]));

        //Return the final arg suggestion list -> each element in the list will be displayed as a suggestion
        return argSuggestions;
    }

    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage: /phone give <player> - gives phone item to target player");
        sender.sendMessage(ChatColor.YELLOW + "Usage: /phone help - sends information on command usage");
    }
}
