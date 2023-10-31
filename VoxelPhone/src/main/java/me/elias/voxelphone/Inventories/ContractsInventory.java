package me.elias.voxelphone.Inventories;

import me.elias.voxelphone.ItemMetaManipulation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContractsInventory extends Inventories {

    public ContractsInventory(Player t) {

        //Create the list for the items that should be displayed in the inventory
        HashMap<Integer, ItemStack> itemsDisplayed = new HashMap<Integer, ItemStack>();


        //contract1 Display Item
        ItemStack contract1 = new ItemStack(Material.WRITABLE_BOOK);

        //Rename Item
        ItemMetaManipulation.renameItem(contract1, "Basic Data Volume", ChatColor.GOLD);

        //Change Lore
        List<String> contract1Lore = new ArrayList<String>();
        contract1Lore.add(ChatColor.WHITE + "Messages: +5");
        contract1Lore.add(ChatColor.WHITE + "Price: 100Ł");
        ItemMetaManipulation.setLore(contract1, contract1Lore);

        //contract2 Display Item
        ItemStack contract2 = new ItemStack(Material.WRITABLE_BOOK);
        ItemMetaManipulation.renameItem(contract2, "Medium Data Volume", ChatColor.GOLD);
        //Change Lore
        List<String> contract2Lore = new ArrayList<String>();
        contract2Lore.add(ChatColor.WHITE + "Messages: +55");
        contract2Lore.add(ChatColor.WHITE + "Price: 1000Ł");
        ItemMetaManipulation.setLore(contract2, contract2Lore);

        //contract3 Display Item
        ItemStack contract3 = new ItemStack(Material.WRITABLE_BOOK);
        ItemMetaManipulation.renameItem(contract3, "Big Data Volume", ChatColor.GOLD);
        //Change Lore
        List<String> contract3Lore = new ArrayList<String>();
        contract3Lore.add(ChatColor.WHITE + "Messages: +700");
        contract3Lore.add(ChatColor.WHITE + "Price: 5000Ł");
        ItemMetaManipulation.setLore(contract3, contract3Lore);


        //Add the items to the list + the item slot it should be add
        itemsDisplayed.put(0, contract1);
        itemsDisplayed.put(1, contract2);
        itemsDisplayed.put(2, contract3);

        //create the inventory
        super.initialiseInventory(t, 9, "Contracts", itemsDisplayed);
        //make the inventory locked (nothing can be taken out of it)
        super.lockInventory(true);
        //open the inventory
        super.openInventory(true);
    }
}
