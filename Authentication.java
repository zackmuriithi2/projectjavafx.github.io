package com.example.finalmvcproj;
import  com.example.finalmvcproj.DatabaseConnection;
import  com.example.finalmvcproj.UIManager;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Authentication {

    public final BooleanProperty isLoggedInProperty = new SimpleBooleanProperty(false);

    public boolean validateLogin(String username, String password) {
        try {
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();

            // Check if the connection is null or closed
            if (connection == null || connection.isClosed()) {
                System.out.println("Connection is null or closed");
                return false;
            }

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            // Set the parameters
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            // Check if any rows are returned
            if (resultSet.next()) {
                // User credentials are valid
                return true;
            }

            // Close the resources
            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public boolean signup(String username, String password) {
        try {
            DatabaseConnection databaseConnection3 = new DatabaseConnection();
            Connection connection3 = databaseConnection3.getConnection();


            // Check if the connection is null or closed
            if (connection3 == null || connection3.isClosed()) {
                System.out.println("Connection is null or closed");
                return false;
            }


            // Check if the user already exists
            if (userExists(username)) {
                System.out.println("User already exists!");
                return false;
            }

            String query = "INSERT INTO users (username, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection3.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Signup successful!");
                return true;
            } else {
                System.out.println("Signup failed!");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private boolean userExists(String username) {
        try {
            // Check if the user exists in the database
            DatabaseConnection databaseConnection = new DatabaseConnection();
            Connection connection = databaseConnection.getConnection();
            String query = "SELECT COUNT(*) FROM users WHERE username = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void openAdminWindow()
    {
        Stage adminstage = new Stage();
        adminstage.setTitle("Admin window");
    }

}



