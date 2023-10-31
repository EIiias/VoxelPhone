package me.elias.voxelphone.Events;

import me.elias.voxelphone.PlayerStats;
import me.elias.voxelphone.VoxelPhone;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnPlayerChat implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player p = e.getPlayer();

        //Check for the state the player is in
        switch (VoxelPhone.playerStats.getState(p)) {
            case "PAYING" -> {
                e.setCancelled(true);

                //Check if player typed "c" -> cancel if yes
                if (e.getMessage().equalsIgnoreCase("c")) {
                    VoxelPhone.playerStats.setState("", p);
                    p.sendMessage(ChatColor.RED + "Canceled payment!");
                    return;
                }

                //Split the message into a string list
                String[] input = e.getMessage().split(" ");

                //Check if argument count is valid
                if (input.length != 2) {
                    p.sendMessage(ChatColor.RED + "Wrong format!");
                    return;
                }

                //Check if first string is a valid phone number
                if (!VoxelPhone.playerStats.checkForValueInSQLDatabase("PlayerStats", "PhoneNumber", input[0])) {
                    p.sendMessage(ChatColor.RED + "Phone number not found!");
                    VoxelPhone.playerStats.setState("", p);
                    return;
                }

                //Check if the first string is a valid transaction amount
                double amount;
                try {
                    amount = Double.parseDouble(input[1]);
                } catch (NumberFormatException n) {
                    p.sendMessage(ChatColor.RED + "Invalid amount!");
                    VoxelPhone.playerStats.setState("", p);
                    return;
                }

                //Check if player has enough moeny
                if (VoxelPhone.playerStats.getMoney(p) < amount) {
                    p.sendMessage(ChatColor.RED + "You don't have enough money to this transaction!");
                    return;
                }

                //Fulfill the transaction
                Player t = VoxelPhone.playerStats.getPlayerFromPhoneNumber(input[0]);
                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) - amount, p);
                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(t) + amount, t);
                p.sendMessage(ChatColor.GREEN + "Successfully sent " + amount + " to " + t.getName() + " (" + input[0] + ")!");
                VoxelPhone.playerStats.setState("", p);
            }
            case "ADDCONTACT" -> {
                e.setCancelled(true);

                //Check if player typed "c" -> cancel if yes
                if (e.getMessage().equalsIgnoreCase("c")) {
                    VoxelPhone.playerStats.setState("", p);
                    p.sendMessage(ChatColor.RED + "Canceled adding contact!");
                    return;
                }

                //Check if phone number is valid
                if (!VoxelPhone.playerStats.checkForValueInSQLDatabase("PlayerStats", "PhoneNumber", e.getMessage())) {
                    p.sendMessage(ChatColor.RED + "Phone number not found!");
                    VoxelPhone.playerStats.setState("", p);
                    return;
                }

                //Add the contact in the SQL Database
                VoxelPhone.playerStats.addContact(VoxelPhone.playerStats.getPlayerFromPhoneNumber(e.getMessage()), p);
                p.sendMessage(ChatColor.GREEN + "Successfully added " + VoxelPhone.playerStats.getPlayerFromPhoneNumber(e.getMessage()).getDisplayName() + " to your contacts!");
                VoxelPhone.playerStats.setState("", p);
            }
            case "DIRECTMESSAGE" -> {
                e.setCancelled(true);

                //Check if player typed "c" -> cancel if yes
                if (e.getMessage().equalsIgnoreCase("c")) {
                    VoxelPhone.playerStats.setState("", p);
                    p.sendMessage(ChatColor.RED + "Exited direct messaging mode!");
                    return;
                }

                //Check if player has enough "messagesAvailable"
                if (VoxelPhone.playerStats.getMessagesAvailable(p) < 0) {
                    VoxelPhone.playerStats.setState("", p);
                    p.sendMessage(ChatColor.RED + "Exiting direct messaging mode! No more messages available!");
                    return;
                }

                //Subtract 1 from the messages available
                VoxelPhone.playerStats.setMessagesAvailable(VoxelPhone.playerStats.getMessagesAvailable(p) - 1, p);

                //Send the message
                p.sendMessage(ChatColor.YELLOW + "[TO " + VoxelPhone.playerStats.getDirectMessaging(p) + "] " + e.getMessage());
                VoxelPhone.playerStats.getPlayerFromPhoneNumber(VoxelPhone.playerStats.getDirectMessaging(p)).sendMessage(ChatColor.YELLOW + "[FROM " + VoxelPhone.playerStats.getPhoneNumberFromPlayer(p) + "] " + e.getMessage());
            }
            case "PAYINGCONTACT" -> {
                e.setCancelled(true);

                //Check if player typed "c" -> cancel if yes
                if (e.getMessage().equalsIgnoreCase("c")) {
                    VoxelPhone.playerStats.setState("", p);
                    p.sendMessage(ChatColor.RED + "Canceled payment!");
                    return;
                }

                //Check if player entered valid transaction amount
                double amount;
                try {
                    amount = Double.parseDouble(e.getMessage());
                } catch (NumberFormatException n) {
                    p.sendMessage(ChatColor.RED + "Invalid amount!");
                    VoxelPhone.playerStats.setState("", p);
                    return;
                }

                //Check if player has enough money to fulfill the transaction
                if (VoxelPhone.playerStats.getMoney(p) < amount) {
                    p.sendMessage(ChatColor.RED + "You don't have enough money to this transaction!");
                    return;
                }

                //Do the transaction
                Player t = VoxelPhone.playerStats.getPlayerFromPhoneNumber(VoxelPhone.playerStats.getDirectMessaging(p));
                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) - amount, p);
                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(t) + amount, t);
                p.sendMessage(ChatColor.GREEN + "Successfully sent " + amount + " to " + t.getName() + " (" + VoxelPhone.playerStats.getPhoneNumberFromPlayer(t) + ")!");
                VoxelPhone.playerStats.setState("", p);
            }
        }

    }
}
