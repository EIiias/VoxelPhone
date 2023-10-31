package me.elias.voxelphone.Events;

import me.elias.voxelphone.Inventories.*;
import me.elias.voxelphone.PlayerStats;
import me.elias.voxelphone.VoxelPhone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Objects;

public class OnInventoryClick implements Listener {
    @EventHandler
    public void onPlayerInteract(InventoryClickEvent e) {

        //Check if the main opened inventory is in the custom inventory list
        if (Inventories.inventoryList.containsKey(e.getInventory())) {
            //store custom inventory class instance in variable
            Inventories inventoryInstance = Inventories.inventoryList.get(e.getInventory());
            //Check if inventory is locked (if items can be removed)
            if (inventoryInstance.isLocked) {
                //Cancel the event
                e.setCancelled(true);
            }

            //Check that player is not clicking in his inventory
            if (!(e.getClickedInventory() instanceof PlayerInventory)) {

                Player p = (Player) e.getWhoClicked();

                //Check which custom inventory
                if (inventoryInstance instanceof PhoneInventory) {
                    //Check which slot was clicked
                    switch (e.getSlot()) {
                        //Open contacts inventory if slot 10 was clicked
                        case 10 -> {
                            new ContactsInventory(p);
                        }
                        //Start payment mode if slot 12 was clicked
                        case 12 -> {
                            p.sendMessage(ChatColor.GREEN + "You're now in payment mode! Type 'c' to cancel.");
                            p.sendMessage(ChatColor.GREEN + "Enter in this format '[Phone Number] [Amount]':");
                            //Set state in SQL Database to "PAYING"
                            VoxelPhone.playerStats.setState("PAYING", p);
                            //Close the phone inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());

                        }
                        //Open contracts menu if slot 14 was clicked
                        case 14 -> {
                            new ContractsInventory(p);
                        }
                    }
                } else if (inventoryInstance instanceof ContractsInventory) {
                    switch (e.getSlot()) {
                        //Check which slot was clicked
                        case 0 -> {
                            //Check if player has enough money
                            if (VoxelPhone.playerStats.getMoney(p) >= 100) {
                                //Subtract money from player
                                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) - 100, p);
                                //Add messages available to player
                                VoxelPhone.playerStats.setMessagesAvailable(VoxelPhone.playerStats.getMessagesAvailable(p) + 5, p);
                                p.sendMessage(ChatColor.GREEN + "You have " + VoxelPhone.playerStats.getMessagesAvailable(p) + " direct messages available!");
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have enough money to buy this contract!");
                            }
                        }
                        case 1 -> {
                            //Check if player has enough money
                            if (VoxelPhone.playerStats.getMoney(p) >= 1000) {
                                //Subtract money from player
                                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) - 1000, p);
                                //Add messages available to player
                                VoxelPhone.playerStats.setMessagesAvailable(VoxelPhone.playerStats.getMessagesAvailable(p) + 55, p);
                                p.sendMessage(ChatColor.GREEN + "You have " + VoxelPhone.playerStats.getMessagesAvailable(p) + " direct messages available!");
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have enough money to buy this contract!");
                            }
                        }
                        case 2 -> {
                            //Check if player has enough money
                            if (VoxelPhone.playerStats.getMoney(p) >= 5000) {
                                //Subtract money from player
                                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) - 5000, p);
                                //Add messages available to player
                                VoxelPhone.playerStats.setMessagesAvailable(VoxelPhone.playerStats.getMessagesAvailable(p) + 700, p);
                                p.sendMessage(ChatColor.GREEN + "You have " + VoxelPhone.playerStats.getMessagesAvailable(p) + " direct messages available!");
                            } else {
                                p.sendMessage(ChatColor.RED + "You don't have enough money to buy this contract!");
                            }
                        }
                    }
                } else if (inventoryInstance instanceof ContactsInventory) {
                    //Check which slot was clicked
                    switch (e.getSlot()) {
                        //Set player in adding contact mode
                        case 0 -> {
                            p.sendMessage(ChatColor.GREEN + "You're now in contact adding mode! Type 'c' to cancel.");
                            p.sendMessage(ChatColor.GREEN + "Enter in this format '[Phone Number]':");
                            //Set state
                            VoxelPhone.playerStats.setState("ADDCONTACT", p);
                            //Close inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());
                        }
                    }

                    //Check if player clicked nothing
                    if (e.getCurrentItem() == null) {
                        return;
                    }

                    //Check if player clicked a player head
                    if (e.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                        //Get the SkullMeta of the player head
                        SkullMeta skullMeta = (SkullMeta) e.getCurrentItem().getItemMeta();

                        //Check if player is online
                        assert skullMeta != null;
                        if (!Objects.requireNonNull(skullMeta.getOwningPlayer()).isOnline()) {
                            p.sendMessage(ChatColor.RED + "Contact not online!");
                            return;
                        }

                        //Create a new contacts context inventory, getting the player by using the SkullMeta
                        new ContactsContextInventory(p, (Player) skullMeta.getOwningPlayer());
                    }


                } else if (inventoryInstance instanceof ContactsContextInventory) {
                    //Check which slot was clicked
                    switch (e.getSlot()) {
                        case 0 -> {
                            p.sendMessage(ChatColor.GREEN + "You're now in direct messaging mode! Type 'c' to exit.");

                            VoxelPhone.playerStats.setState("DIRECTMESSAGE", p);
                            //Get the target player for dms
                            Player contact = ((ContactsContextInventory) inventoryInstance).contact;
                            //Set which target player for dms in SQL Database
                            VoxelPhone.playerStats.setDirectMessaging(VoxelPhone.playerStats.getPhoneNumberFromPlayer(contact), p);
                            //Close inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());
                        }
                        case 1 -> {
                            p.sendMessage(ChatColor.GREEN + "You're now in paying contact mode! Type 'c' to exit.");

                            //Set state
                            VoxelPhone.playerStats.setState("PAYINGCONTACT", p);
                            //Set which target player is being paid in the SQL Database
                            VoxelPhone.playerStats.setDirectMessaging(VoxelPhone.playerStats.getPhoneNumberFromPlayer(((ContactsContextInventory) inventoryInstance).contact), p);
                            //Close inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());
                        }
                        case 7 -> {
                            //Remove the entry the contact entry in the SQL Database
                            VoxelPhone.playerStats.removeContact(((ContactsContextInventory) inventoryInstance).contact, p);
                            //Close inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());
                            //Go back to contact menu
                            new ContactsInventory(p);
                        }
                        case 8 -> {
                            //Close inventory
                            inventoryInstance.openInventory(false);
                            //Remove the inventory from ram
                            Inventories.destroyInventoryInstance(e.getInventory());
                            //Open contact menu
                            new ContactsInventory(p);
                        }
                    }
                }
            }
        }
    }
}
