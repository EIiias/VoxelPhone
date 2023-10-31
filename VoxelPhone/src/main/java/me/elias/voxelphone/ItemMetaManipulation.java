package me.elias.voxelphone;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ItemMetaManipulation {

    public static void addBooleanPDS(ItemStack item, String keyName, Boolean value) {

        //Get item metadata
        ItemMeta itemMeta = item.getItemMeta();

        //Get PDS
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();

        //Add the new boolean
        persistentDataContainer.set(new NamespacedKey(VoxelPhone.instance, keyName), PersistentDataType.BOOLEAN, value);

        //Apply the modified metadata back to the item
        item.setItemMeta(itemMeta);
    }

    public static Boolean isPhone(ItemStack item) {

        //Get item metadata
        ItemMeta itemMeta = item.getItemMeta();

        //Get PDS
        PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();

        //Check if PDS contains boolean value called "phone"
        if (persistentDataContainer.has(new NamespacedKey(VoxelPhone.instance, "phone"), PersistentDataType.BOOLEAN)) {
            //Return the value of the boolean
            return persistentDataContainer.get(new NamespacedKey(VoxelPhone.instance, "phone"), PersistentDataType.BOOLEAN);
        } else {
            return false;
        }
    }

    public static void renameItem(ItemStack item, String name, ChatColor nameColor) {

        //Get metadata
        ItemMeta itemMeta = item.getItemMeta();

        //Set new display name
        itemMeta.setDisplayName(nameColor + name);

        //Apply the modified metadata back to the item
        item.setItemMeta(itemMeta);
    }

    public static void setLore(ItemStack item, List<String> itemLore) {

        //Get metadata
        ItemMeta itemMeta = item.getItemMeta();

        //Set new lore
        itemMeta.setLore(itemLore);

        //Apply the modified metadata back to the item
        item.setItemMeta(itemMeta);
    }
}
