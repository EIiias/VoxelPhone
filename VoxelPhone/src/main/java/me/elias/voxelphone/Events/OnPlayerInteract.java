package me.elias.voxelphone.Events;

import me.elias.voxelphone.Inventories.PhoneInventory;
import me.elias.voxelphone.ItemMetaManipulation;
import me.elias.voxelphone.VoxelPhone;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class OnPlayerInteract implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        //Only continue if item in main hand stores item meta
        if (p.getInventory().getItemInMainHand().getItemMeta() == null) {
            return;
        }

        //Check if player is holding Gold ingot
        if (p.getInventory().getItemInMainHand().getType().equals(Material.GOLD_INGOT)) {
            //Check if player used Right click
            if (e.getAction().toString().contains("RIGHT_CLICK")) {
                //Add the money
                VoxelPhone.playerStats.setMoney(VoxelPhone.playerStats.getMoney(p) + 5, p);
                //Subtract 1 gold ingot from the player's inventory
                if (p.getInventory().getItemInMainHand().getAmount() == 1) {
                    p.getInventory().setItemInMainHand(new ItemStack(Material.AIR));
                } else {
                    p.getInventory().setItemInMainHand(new ItemStack(p.getInventory().getItemInMainHand().getType(), p.getInventory().getItemInMainHand().getAmount() - 1));
                }
                p.sendMessage(ChatColor.GREEN + "Added 5Ł to your account!");
                return;
            }
        }

        if (ItemMetaManipulation.isPhone(p.getInventory().getItemInMainHand())) {
                //Cancel Event
                e.setCancelled(true);
                //Create GUI for phone
                new PhoneInventory(p);

        }
    }
}
