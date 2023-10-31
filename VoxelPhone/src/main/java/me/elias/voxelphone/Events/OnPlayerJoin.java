package me.elias.voxelphone.Events;

import me.elias.voxelphone.Inventories.ContractsInventory;
import me.elias.voxelphone.Inventories.Inventories;
import me.elias.voxelphone.Inventories.PhoneInventory;
import me.elias.voxelphone.PlayerStats;
import me.elias.voxelphone.PluginConfig;
import me.elias.voxelphone.VoxelPhone;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.PlayerInventory;

import java.util.Objects;

public class OnPlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        //Generate default player stats if player joined for the first time
        if (!e.getPlayer().hasPlayedBefore()) {
            String[] sql = {
                    "INSERT INTO PlayerStats (id, money) VALUES ('" + e.getPlayer().getUniqueId() + "', '500');\n"
            };
            VoxelPhone.playerStats.executeSQLStatements(sql, false);
            VoxelPhone.playerStats.generateNewPhoneNumber(e.getPlayer());
        }
    }
}
