package me.elias.voxelphone;


import me.elias.voxelphone.Inventories.ContactsInventory;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.*;
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

    public Statement executeSQLStatements(String[] commands, boolean getStatement) {

        //Create SQL statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't create SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            return null;
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

        //Check if statement should be returned
        if (getStatement) {
            return statement;
        } else {
            //Close the SQL statement
            try {
                statement.close();
            } catch (SQLException e) {
                VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't close SQL statement");
                VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));

            }
            return null;
        }
    }

    public boolean checkForValueInSQLDatabase(String table, String column, String value) {
        //Create SQL statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't create SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw new RuntimeException();
        }

        //Only continue if statement was created successfully
        assert statement != null;

        String sql = "SELECT * FROM " + table + " WHERE " + column + " = ?";

        //Check if the value is in the SQL specified column
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, value);
            ResultSet resultSet = preparedStatement.executeQuery();
            boolean result = resultSet.next();
            statement.close();

            return result;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }





    }

    public double getMoney(Player p) {

        //Select row
        String sqlCommand = "SELECT money FROM PlayerStats WHERE id = '" + p.getUniqueId() + "';";

        Statement statement = VoxelPhone.playerStats.executeSQLStatements(new String[]{}, true);

        //Get value for Money from row
        try {
            ResultSet results = statement.executeQuery(sqlCommand);

            if (results.next()) {
                return results.getDouble("money");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Return error if money couldn't be gotten from the SQL Server
        VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't return money for player " + p.getName());
        return 0;
    }

    public void setMoney(double amount, Player p) {
        String[] sqlCommand = {
                "UPDATE PlayerStats SET money = '" + amount + "' WHERE id = '" + p.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand, false);
    }

    public String getState(Player p) {

        //Select row
        String sqlCommand = "SELECT state FROM PlayerStats WHERE id = '" + p.getUniqueId() + "';";

        Statement statement = VoxelPhone.playerStats.executeSQLStatements(new String[]{}, true);

        //Get value for Money from row
        try {
            ResultSet results = statement.executeQuery(sqlCommand);

            if (results.next()) {
                return results.getString("state");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Return error if money couldn't be gotten from the SQL Server
        VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't return state for player " + p.getName());
        return "";
    }

    public void setState(String state, Player p) {
        String[] sqlCommand = {
                "UPDATE PlayerStats SET state = '" + state + "' WHERE id = '" + p.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand, false);
    }

    public int getMessagesAvailable(Player p) {

        //Select row
        String sqlCommand = "SELECT messagesAvailable FROM PlayerStats WHERE id = '" + p.getUniqueId() + "';";

        Statement statement = VoxelPhone.playerStats.executeSQLStatements(new String[]{}, true);

        //Get value for Money from row
        try {
            ResultSet results = statement.executeQuery(sqlCommand);

            if (results.next()) {
                return results.getInt("messagesAvailable");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Return error if money couldn't be gotten from the SQL Server
        VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't return state for player " + p.getName());
        return 0;
    }

    public void setMessagesAvailable(int amount, Player p) {
        String[] sqlCommand = {
                "UPDATE PlayerStats SET messagesAvailable = '" + amount + "' WHERE id = '" + p.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand, false);
    }

    public String getDirectMessaging(Player p) {

        //Select row
        String sqlCommand = "SELECT directMessaging FROM PlayerStats WHERE id = '" + p.getUniqueId() + "';";

        Statement statement = VoxelPhone.playerStats.executeSQLStatements(new String[]{}, true);

        //Get value for Money from row
        try {
            ResultSet results = statement.executeQuery(sqlCommand);

            if (results.next()) {
                return results.getString("directMessaging");
            }
            statement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Return error if money couldn't be gotten from the SQL Server
        VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't return state for player " + p.getName());
        return "";
    }

    public void setDirectMessaging(String number, Player p) {
        String[] sqlCommand = {
                "UPDATE PlayerStats SET directMessaging = '" + number + "' WHERE id = '" + p.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand, false);
    }

    public void removeContact(Player t, Player p) {
        String[] sqlCommand = {
                "DELETE FROM Contacts WHERE " + p.getUniqueId().toString().replace("-", "") + " = '" + t.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand, false);
    }

    public void generateNewPhoneNumber(Player p) {

        String newNumber = "";
        //Continue generating until unique number
        while (true) {


            // Create a Random object
            Random random = new Random();

            //Generate 10 random digits
            for (int i = 0; i < 9; i++) {

                //add '-' to the number
                switch (i) {
                    case 2, 5 -> newNumber += "-";
                }

                // Generate a random integer between 0 (inclusive) and 10 (exclusive)
                newNumber += (Integer.toString(random.nextInt(10)));
            }

            //Stop loop if generated phone number is unique
            if (!VoxelPhone.playerStats.checkForValueInSQLDatabase("PlayerStats", "PhoneNumber", newNumber)) {
                break;
            }
            //Reset number if number already existed
            newNumber = "";
        }
        //Update the phone number on the SQL Server
        String[] sqlCommand2 = {
                "UPDATE PlayerStats SET PhoneNumber = '" + newNumber + "' WHERE id = '" + p.getUniqueId() + "';"

        };
        VoxelPhone.playerStats.executeSQLStatements(sqlCommand2, false);

    }

    public Player getPlayerFromPhoneNumber(String number) {

        //Create SQL statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't create SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw new RuntimeException();
        }

        //Only continue if statement was created successfully
        assert statement != null;

        String sql = "SELECT id FROM PlayerStats WHERE PhoneNumber = ?";

        //Execute the SQL Query and return the player
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, number); // Set the parameter value
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String playerUUID = resultSet.getString("id");
                    return (Player) Bukkit.getOfflinePlayer(UUID.fromString(playerUUID));
                } else {
                    VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't retrieve player by phone number!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPhoneNumberFromPlayer(Player p) {

        //Create SQL statement
        Statement statement = null;
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't create SQL statement");
            VoxelPhone.logger.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            throw new RuntimeException();
        }

        //Only continue if statement was created successfully
        assert statement != null;

        String sql = "SELECT PhoneNumber FROM PlayerStats WHERE id = ?";

        //Execute the SQL Query and return the phone number
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, p.getUniqueId().toString()); // Set the parameter value
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getString("PhoneNumber");
                } else {
                    VoxelPhone.logger.log(Level.SEVERE, "[" + VoxelPhone.pluginName + "]" + " couldn't retrieve player by phone number!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addContact(Player t, Player p) {
        String[] dataBaseStructure2 = {
                "INSERT INTO Contacts (`" + p.getUniqueId().toString().replace("-", "") + "`) VALUES ('" + p.getUniqueId() + "');"
        };
        VoxelPhone.playerStats.executeSQLStatements(dataBaseStructure2, false);
    }

    public ArrayList<UUID> getContacts(Player p) {

        //Add new column for player if it doesn't exist
        String[] dataBaseStructure = {
                "ALTER TABLE Contacts ADD COLUMN IF NOT EXISTS " + p.getUniqueId().toString().replace("-", "") + " CHAR(36);"
        };
        VoxelPhone.playerStats.executeSQLStatements(dataBaseStructure, false);

        //Create an arraylist to store all contacts
        ArrayList<UUID> contacts = new ArrayList<UUID>();

        Statement statement = executeSQLStatements(new String[]{}, true);

        String sql = "SELECT " + p.getUniqueId().toString().replace("-", "") + " FROM Contacts";

        try {
            ResultSet result = statement.executeQuery(sql);
            //Get each contact and add them to the arraylist
            while (result.next()) {
                String columnValue = result.getString(p.getUniqueId().toString().replace("-", ""));
                contacts.add(UUID.fromString(columnValue));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return contacts;
    }
}