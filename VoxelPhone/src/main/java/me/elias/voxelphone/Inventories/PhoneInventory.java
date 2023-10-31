package me.elias.voxelphone.Inventories;

import me.elias.voxelphone.ItemMetaManipulation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PhoneInventory extends Inventories {

    public PhoneInventory(Player t) {

        //Create the list for the items that should be displayed in the inventory
        HashMap<Integer, ItemStack> itemsDisplayed = new HashMap<Integer, ItemStack>();

        String playerName;

        //Append character for inventory name
        if (t.getName().endsWith("s")) {
            playerName = t.getName() + "'";
        } else {
            playerName = t.getName() + "'s";
        }


        //Contacts Display Item
        //Get player head of phone owner
        ItemStack contactsItem = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) contactsItem.getItemMeta();
        skullMeta.setOwningPlayer(Bukkit.getOfflinePlayer(t.getUniqueId()));
        contactsItem.setItemMeta(skullMeta);

        //Rename Item
        ItemMetaManipulation.renameItem(contactsItem, "Contacts", ChatColor.GOLD);

        //Change Lore
        List<String> contactsItemLore = new ArrayList<String>();
        contactsItemLore.add(ChatColor.WHITE + "Manage your contacts");
        ItemMetaManipulation.setLore(contactsItem, contactsItemLore);

        //Payment Display Item
        ItemStack payItem = new ItemStack(Material.EMERALD);
        ItemMetaManipulation.renameItem(payItem, "Payments", ChatColor.GOLD);
        //Change Lore
        List<String> payItemLore = new ArrayList<String>();
        contactsItemLore.add(ChatColor.WHITE + "Commit a payment");
        ItemMetaManipulation.setLore(payItem, payItemLore);

        //Contracts Display Item
        ItemStack contractsItem = new ItemStack(Material.WRITABLE_BOOK);
        ItemMetaManipulation.renameItem(contractsItem, "Contracts", ChatColor.GOLD);
        //Change Lore
        List<String> contractsItemLore = new ArrayList<String>();
        contractsItemLore.add(ChatColor.WHITE + "Manage your contracts");
        ItemMetaManipulation.setLore(contractsItem, contractsItemLore);


        //Add the items to the list + the item slot it should be add
        itemsDisplayed.put(10, contactsItem);
        itemsDisplayed.put(12, payItem);
        itemsDisplayed.put(14, contractsItem);

        //create the inventory
        super.initialiseInventory(t, 45, playerName + " phone", itemsDisplayed);
        //make the inventory locked (nothing can be taken out of it)
        super.lockInventory(true);
        //open the inventory
        super.openInventory(true);
    }
}
