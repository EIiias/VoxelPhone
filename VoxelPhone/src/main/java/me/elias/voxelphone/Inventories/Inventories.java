package me.elias.voxelphone.Inventories;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class Inventories {

    //List with all custom inventories
    public static HashMap<Inventory, Inventories> inventoryList = new HashMap<Inventory, Inventories>();

    //The inventory
    public Inventory i;
    //If items can be taken out of by the player or not
    public boolean isLocked = false;
    //If the inventory is opened
    public boolean isOpen = false;
    //The owning player
    public Player p;


    public void initialiseInventory(Player t, int inventorySize, String inventoryName, HashMap<Integer, ItemStack> itemsDisplayed) {
        i = Bukkit.createInventory(t, inventorySize, inventoryName);

        //Go through each element in the "itemsDisplayed" lit and display each item at the corresponding slot
        for (Map.Entry<Integer, ItemStack> entry : itemsDisplayed.entrySet()) {
            i.setItem(entry.getKey(), entry.getValue());
        }

        p = t;

        //Add the custom inventory to the custom inventory list
        inventoryList.put(i, this);
    }

    public void lockInventory(boolean locked) {
        isLocked = locked;
    }

    public void openInventory(boolean open) {
        //Open inventory if not already open and should be opened
        if (!isOpen && open) {
            p.openInventory(i);
        //Close inventory if open and should be closed
        } else if (isOpen && !open) {
            p.closeInventory();
        }

        //Set current status
        isOpen = open;
    }

    public static void destroyInventoryInstance(Inventory inventory) {
        //Get the class instance
        Inventories target = inventoryList.get(inventory);
        //Remove the inventory from the custom inventory list
        inventoryList.remove(inventory);
        //Destroy parent class
        target = null;
    }
}


