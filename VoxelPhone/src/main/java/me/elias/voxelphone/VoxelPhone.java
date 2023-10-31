package me.elias.voxelphone;

import me.elias.voxelphone.Commands.NumberCommand;
import me.elias.voxelphone.Commands.PhoneCommand;
import me.elias.voxelphone.Commands.VoxelPhoneCommand;
import me.elias.voxelphone.Events.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public class VoxelPhone extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("main");
    public static VoxelPhone instance;
    public static final String pluginName = "VoxelPhone";
    public static final String pluginFolder = Bukkit.getWorldContainer().getAbsolutePath() + "/plugins/" + VoxelPhone.pluginName;
    public static PluginConfig pluginConfig;
    public static PlayerStats playerStats;

    @Override
    public void onEnable() {
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " starting plugin");

        //Storing the entry point class instance in a variable
        instance = this;

        //Create plugin folder in $serverDir/Plugins if non existent
        if (!new File(VoxelPhone.pluginFolder).exists())  {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin folder doesn't exist, creating one");
            new File(VoxelPhone.pluginFolder).mkdir();
        } else {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin folder exists");
        }

        //Loading Plugin/Player Data
        VoxelPhone.pluginConfig = new PluginConfig();
        VoxelPhone.playerStats = new PlayerStats();

        //Create Database structure if not existing
        String[] dataBaseStructure = {
                "CREATE TABLE IF NOT EXISTS PlayerStats (" +
                        "id CHAR(36) PRIMARY KEY, " +
                        "phoneNumber VARCHAR(16), " +
                        "state VARCHAR(16), " +
                        "messagesAvailable INT, " +
                        "directMessaging CHAR(12), " +
                        "money DOUBLE" +
                        ");",
                "CREATE TABLE IF NOT EXISTS Contacts (" +
                        "filler CHAR(1)); "

        };
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " creating Database structure if not compliant");
        VoxelPhone.playerStats.executeSQLStatements(dataBaseStructure, false);

        //Register Events
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerInteract(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInventoryClick(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnInventoryClose(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerJoin(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new OnPlayerChat(), this);

        //Register Commands + Tab completer
        getCommand("phone").setExecutor(new PhoneCommand());
        getCommand("phone").setTabCompleter(new PhoneCommand());
        getCommand("number").setExecutor(new NumberCommand());
        getCommand("VoxelPhone").setExecutor(new VoxelPhoneCommand());
        getCommand("VoxelPhone").setTabCompleter(new VoxelPhoneCommand());
    }

    @Override
    public void onDisable() {
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " stopping plugin");
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " saving plugin config");
        VoxelPhone.pluginConfig.savePluginConfig();
    }
}
