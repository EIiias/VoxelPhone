package me.elias.voxelphone;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class PluginConfig {

    public static final String configFileLocation = VoxelPhone.pluginFolder + "/" + VoxelPhone.pluginName + ".yml";
    public final YamlConfiguration configuration = new YamlConfiguration();

    //Default config values
    public String databaseConnectionString = "jdbc:mariadb://192.168.0.51:3306/VoxelPhone";
    public String databaseUsername = "VoxelPhone";
    public String databasePassword = "VoxelPhone";

    PluginConfig() {
        //Create new config file with default values if config file doesn't exist
        if (!new File(configFileLocation).exists()) {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin config doesn't exist, creating one");
            this.savePluginConfig();
        //Load existing config file
        } else {
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " plugin config exists");
            this.loadPluginConfig();
        }
    }

    public void savePluginConfig() {

        //Add comments at the top of the config file
        List<String> configFileInfo = new ArrayList<>();
        configFileInfo.add("This is the config file for the VoxelPhone plugin");
        configFileInfo.add("Comments indicated by a '#' are ignored");

        configuration.options().setHeader(configFileInfo);

        //Config values that can be adjusted
        configuration.set("database_connection_string", databaseConnectionString);
        configuration.set("database_username", databaseUsername);
        configuration.set("database_password", databasePassword);

        //Save the config file using new values
        try {
            configuration.save(configFileLocation);
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " saved plugin config");
        }
        catch (Exception e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't save plugin config");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }

    public void loadPluginConfig() {
        //Load values from existing config file
        databaseConnectionString = (String) VoxelPhone.pluginConfig.configuration.get("database_connection_string");
        databaseUsername = (String) VoxelPhone.pluginConfig.configuration.get("database_username");
        databasePassword = (String) VoxelPhone.pluginConfig.configuration.get("database_password");
    }
}
