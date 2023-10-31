package me.elias.voxelphone.Inventories;

import me.elias.voxelphone.ItemMetaManipulation;
import me.elias.voxelphone.VoxelPhone;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ContactsInventory extends Inventories {

    public ContactsInventory(Player t) {

        //Create the list for the items that should be displayed in the inventory
        HashMap<Integer, ItemStack> itemsDisplayed = new HashMap<Integer, ItemStack>();


        //Contacts Display Item
        ItemStack addContactItem = new ItemStack(Material.LIME_WOOL);

        //Rename Item
        ItemMetaManipulation.renameItem(addContactItem, "Add Contact", ChatColor.GOLD);

        //Change Lore
        List<String> addContactLore = new ArrayList<String>();
        addContactLore.add(ChatColor.WHITE + "Add a new contact to your contact list");
        ItemMetaManipulation.setLore(addContactItem, addContactLore);

        //Add the items to the list + the item slot it should be at
        itemsDisplayed.put(0, addContactItem);

        //First slot where the player head used to display the contacts is in the inventory
        int i = 1;

        //For each contact add a player head in the contacts inventory
        for (UUID contact : VoxelPhone.playerStats.getContacts(t)) {
            ItemStack contactSkull = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta) contactSkull.getItemMeta();
            OfflinePlayer contactPlayer = Bukkit.getOfflinePlayer(contact);
            skullMeta.setOwningPlayer(contactPlayer);
            contactSkull.setItemMeta(skullMeta);

            ItemMetaManipulation.renameItem(contactSkull, contactPlayer.getName(), ChatColor.GOLD);
            //Change Lore
            List<String> contactLore = new ArrayList<String>();
            //Check if player is online/offline
            if (contactPlayer.isOnline()) {
                contactLore.add(ChatColor.GREEN + "Online");
            } else {
                contactLore.add(ChatColor.RED + "Offline");
            }
            //Add the phone number to the item lore
            contactLore.add(ChatColor.WHITE + "Number: " + VoxelPhone.playerStats.getPhoneNumberFromPlayer(t));
            ItemMetaManipulation.setLore(contactSkull, contactLore);

            //Put the player head at the correct slot and
            itemsDisplayed.put(i, contactSkull);
            i++;
        }

        //create the inventory
        super.initialiseInventory(t, 54, "Contacts", itemsDisplayed);
        //make the inventory locked (nothing can be taken out of it)
        super.lockInventory(true);
        //open the inventory
        super.openInventory(true);
    }
}
