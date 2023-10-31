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

public class VoxelPhoneCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Switch for amount of arguments provided py command sender
        switch (args.length) {
            //Send usage if argument amount is invalid/unused
            default -> {
                sendUsage(sender);
            }
            //Send plugin help if player types command without arguments
            case 0 -> {
                sender.sendMessage(ChatColor.YELLOW + "Commands:");
                sender.sendMessage(ChatColor.YELLOW + "/phone <give/help> <player> - Gives the specified player the phone item");
                sender.sendMessage(ChatColor.YELLOW + "/number - displays your number");
                sender.sendMessage(ChatColor.YELLOW + "/VoxelPhone - lists plugin information");
                sender.sendMessage(ChatColor.YELLOW + "How to earn money:");
                sender.sendMessage(ChatColor.YELLOW + "To get money right click a gold ingot. Doing this will add 5Ł to your account.");
                sender.sendMessage(ChatColor.YELLOW + "About this plugin:");
                sender.sendMessage(ChatColor.YELLOW + "This plugin implements phones, similar the phones in our real world.");
            }
            //Send plugin help if player types command + help argument
            case 1 -> {
                if (args[0].equalsIgnoreCase("help")) {
                    sender.sendMessage(ChatColor.YELLOW + "Commands:");
                    sender.sendMessage(ChatColor.YELLOW + "/phone <give/help> <player> - Gives the specified player the phone item");
                    sender.sendMessage(ChatColor.YELLOW + "/number - displays your number");
                    sender.sendMessage(ChatColor.YELLOW + "/VoxelPhone - lists plugin information");
                    sender.sendMessage(ChatColor.YELLOW + "How to earn money:");
                    sender.sendMessage(ChatColor.YELLOW + "To get money right click a gold ingot. Doing this will add 5Ł to your account.");
                    sender.sendMessage(ChatColor.YELLOW + "About this plugin:");
                    sender.sendMessage(ChatColor.YELLOW + "This plugin implements phones, similar the phones in our real world.");
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
                argSuggestions.add("help");
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
        sender.sendMessage(ChatColor.YELLOW + "Usage: /VoxelPhone");
    }
}
