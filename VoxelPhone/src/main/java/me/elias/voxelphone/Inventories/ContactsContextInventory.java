package me.elias.voxelphone.Inventories;

import me.elias.voxelphone.ItemMetaManipulation;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactsContextInventory extends Inventories {

    public Player contact;

    public ContactsContextInventory(Player t, Player contact) {

        this.contact = contact;

        //Create the list for the items that should be displayed in the inventory
        HashMap<Integer, ItemStack> itemsDisplayed = new HashMap<Integer, ItemStack>();


        //Contacts Display Item
        ItemStack messageItem = new ItemStack(Material.PAPER);

        //Rename Item
        ItemMetaManipulation.renameItem(messageItem, "Message", ChatColor.GOLD);

        //Change Lore
        List<String> addContactLore = new ArrayList<String>();
        addContactLore.add(ChatColor.WHITE + "Directly message the contact");
        ItemMetaManipulation.setLore(messageItem, addContactLore);

        //Contacts Display Item
        ItemStack backItem = new ItemStack(Material.BARRIER);

        //Rename Item
        ItemMetaManipulation.renameItem(backItem, "Back", ChatColor.GOLD);

        //Change Lore
        List<String> backItemLore = new ArrayList<String>();
        backItemLore.add(ChatColor.WHITE + "Back to contacts");
        ItemMetaManipulation.setLore(backItem, backItemLore);

        //Contacts Display Item
        ItemStack removeContact = new ItemStack(Material.RED_WOOL);

        //Rename Item
        ItemMetaManipulation.renameItem(removeContact, "Remove Contact", ChatColor.GOLD);

        //Change Lore
        List<String> removeContactLore = new ArrayList<String>();
        removeContactLore.add(ChatColor.WHITE + "Remove this contact");
        ItemMetaManipulation.setLore(removeContact, removeContactLore);

        //Contacts Display Item
        ItemStack payContactItem = new ItemStack(Material.EMERALD);

        //Rename Item
        ItemMetaManipulation.renameItem(payContactItem, "Send money", ChatColor.GOLD);


        //Add the items to the list + the item slot it should be at
        itemsDisplayed.put(0, messageItem);
        itemsDisplayed.put(1, payContactItem);
        itemsDisplayed.put(7, removeContact);
        itemsDisplayed.put(8, backItem);

        //create the inventory
        super.initialiseInventory(t, 9, "Contacts", itemsDisplayed);
        //make the inventory locked (nothing can be taken out of it)
        super.lockInventory(true);
        //open the inventory
        super.openInventory(true);
    }
}
