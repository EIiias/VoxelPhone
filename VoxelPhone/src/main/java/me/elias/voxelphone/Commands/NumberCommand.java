package me.elias.voxelphone.Commands;

import me.elias.voxelphone.ItemMetaManipulation;
import me.elias.voxelphone.VoxelPhone;
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
import java.util.List;

public class NumberCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Check if command executor is player and cancel if not
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Command can only be executed by players!");
            return true;
        }

        //Send player phone number via chat
        sender.sendMessage(ChatColor.GREEN + "Your phone number: " + VoxelPhone.playerStats.getPhoneNumberFromPlayer((Player) sender));

        return true;
    }


    //Display command usage in chat
    public void sendUsage(CommandSender sender) {
        sender.sendMessage(ChatColor.YELLOW + "Usage: /number - display your phone number");
    }
}
