package me.elias.voxelphone;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.logging.Level;

public class PlayerStats {

    private Connection connection;

    public PlayerStats() {

        //Establish connection to SQL Server
        try{
            Class.forName("org.mariadb.jdbc.Driver");
            connection = DriverManager.getConnection(
                    VoxelPhone.pluginConfig.databaseConnectionString,
                    VoxelPhone.pluginConfig.databaseUsername,
                    VoxelPhone.pluginConfig.databasePassword
            );
            VoxelPhone.logger.info("[" + VoxelPhone.pluginName + "]" + " connected to SQL server");
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't connect to SQL server");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        } catch (ClassNotFoundException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't load required SQL driver");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }


    }

    public void executeSQLStatements(String[] commands) {

        //Create SQL statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't create SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            return;
        }

        //Only continue if statement was created successfully
        assert statement != null;

        //Execute provided SQL commands
        for (String command : commands) {
            try {
                statement.execute(command);
            } catch (SQLException e) {
                VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't execute SQL command '" + command + "'");
                VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            }
        }

        //Close the SQL statement
        try {
            statement.close();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't close SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
    }
}
