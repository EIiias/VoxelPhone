package me.elias.voxelphone.Events;

import me.elias.voxelphone.Inventories.Inventories;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OnInventoryClose implements Listener {
    @EventHandler
    public void onPlayerInteract(InventoryCloseEvent e) {
        //Destroy inventory + parent class if custom inventory
        if (Inventories.inventoryList.containsKey(e.getInventory())) {
            Inventories.destroyInventoryInstance(e.getInventory());
        }
    }
}
