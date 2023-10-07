package me.elias.voxelphone;

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

        //Storing the main class instance in a variable
        instance = this;

        //Create plugin folder under $serverDir/Plugins if non existent
        if (!new File(VoxelPhone.pluginFolder).exists())  {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin folder doesn't exist, creating one");
            new File(VoxelPhone.pluginFolder).mkdir();
        } else {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin folder exists");
        }

        //Loading Plugin/Player Data
        VoxelPhone.pluginConfig = new PluginConfig();
        VoxelPhone.playerStats = new PlayerStats();
    }

    @Override
    public void onDisable() {
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " stopping plugin");
        VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " saving plugin config");
        VoxelPhone.pluginConfig.savePluginConfig();
    }
}
