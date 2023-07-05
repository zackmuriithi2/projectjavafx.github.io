package com.example.finalmvcproj;
import com.example.finalmvcproj.UIManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
public class DatabaseConnection {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/tasks";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "volvoA5.";

    public Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database");
                e.printStackTrace();
            }
        }
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Disconnected from the database");
            } catch (SQLException e) {
                System.out.println("Failed to close the database connection");
                e.printStackTrace();
            }
        }
    }

    public  boolean validateUserCredentials(String username, String password){
        //sql query to check credentials
        String sql = "SELECT * FROM users where username = ? AND password = ?";
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            // Check if a row is returned from the query
            // If a row is returned, it means the credentials are valid
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void fetchUsersFromDB(ComboBox<String> userComboBox,UIManager uiManager) {
        String sql = "SELECT username from users";
        try {
            PreparedStatement statement = getConnection().prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<String> users = FXCollections.observableArrayList();
            while (resultSet.next()) {
                String user = resultSet.getString("username");
                users.add(user);
            }
            resultSet.close();
            statement.close();

            // Set retrieved users to the comboBox
            userComboBox.getItems().addAll(users);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void fetchUnassignedTasksfromDB(ListView<String>unassignedTasksListView,UIManager uiManager){
        String sql = "SELECT task FROM tasks WHERE status = 'pending' AND created_by = ?";
        try{
            PreparedStatement unassignedStatement = getConnection().prepareStatement(sql);
            unassignedStatement.setString(1, "Administrator");
            ResultSet unassignedResultSet = unassignedStatement.executeQuery();
            ObservableList<String> unassignedTasks = FXCollections.observableArrayList();
            while (unassignedResultSet.next()){
                String unassignedTask = unassignedResultSet.getString("task");
                unassignedTasks.add(unassignedTask);
            }
             unassignedResultSet.close();
             unassignedStatement.close();

            // Set the retrieved unassigned tasks in the unassignedTasksListView
            uiManager.setUnassignedTasksListView(unassignedTasksListView);
            uiManager.unassignedTasksListView.setItems(unassignedTasks);
        } catch (SQLException e) {
          e.printStackTrace();        }
    }

    public  void fetchAssignedTasksfromDB (ListView<String>assignedTasksListView,UIManager uiManager){
        String sql ="SELECT task FROM tasks WHERE status = 'Assigned' OR status = 'processing' OR status = 'completed' AND created_by = ?";
        try {
            PreparedStatement assignedStatement = getConnection().prepareStatement(sql);
            assignedStatement.setString(1, "Administrator");
            ResultSet assignedResultSet = assignedStatement.executeQuery();
            ObservableList<String> assignedTasks = FXCollections.observableArrayList();

            while (assignedResultSet.next()){
                String unassignedTask = assignedResultSet.getString("task");
                assignedTasks.add(unassignedTask);
            }
            assignedResultSet.close();
            assignedStatement.close();

            // Set the retrieved unassigned tasks in the unassignedTasksListView
            uiManager.setAssignedTasksListView(assignedTasksListView);
            uiManager.assignedTasksListView.setItems(assignedTasks);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void addTask(String task, String username, String priority,    UIManager uiManager) {
        String sql = "SELECT COUNT(*) FROM tasks WHERE task = ? AND created_by = ?";
        try {
            Connection connection = getConnection(); // Ensure connection is initialized
            PreparedStatement checkStatement = connection.prepareStatement(sql);
            checkStatement.setString(1, task);
            checkStatement.setString(2, username);
            ResultSet checkResult = checkStatement.executeQuery();
            checkResult.next();
            int count = checkResult.getInt(1);
            checkResult.close();
            checkStatement.close();

            if (count > 0) {
                // Task already exists
                uiManager.showAlert("Error", "Task already exists");
            } else {
                // Insert task into the database
                PreparedStatement insertStatement = connection.prepareStatement("INSERT INTO tasks(task, status, created_by,priority) VALUES (?, ?, ?,?)");
                insertStatement.setString(1, task);
                insertStatement.setString(2, "pending");
                insertStatement.setString(3, username);
                insertStatement.setString(4,priority);
                insertStatement.executeUpdate(); // Use executeUpdate instead of executeQuery
                insertStatement.close();

                // Update the task list locally
                uiManager.pendingTasks.add(task);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void moveTask(String selectedTask, String newStatus, String username,UIManager uiManager){
        String sql ="UPDATE tasks SET status = ?, performed_by = ?, completed_by = ? WHERE task = ?";
        try{
            PreparedStatement statement = getConnection().prepareStatement(sql);
            statement.setString(1, newStatus);
            statement.setString(2, (newStatus.equals("processing")? username : null));
            statement.setString(3,(newStatus.equals("competed")? username : null));
            statement.setString(4,selectedTask);
            statement.executeUpdate();
            statement.close();

            //update the task lists locally
            switch (newStatus){
                case "pending":
                    uiManager.processingTasks.remove(selectedTask);
                    uiManager.completedTasks.remove(selectedTask);
                    uiManager.pendingTasks.add(selectedTask);
                    break;
                case "processing":
                    uiManager.pendingTasks.remove(selectedTask);
                    uiManager.completedTasks.remove(selectedTask);
                    uiManager.processingTasks.add(selectedTask);
                   break;
                case "completed":
                    uiManager.pendingTasks.remove(selectedTask);
                    uiManager.processingTasks.remove(selectedTask);
                    uiManager.completedTasks.add(selectedTask);
                    break;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void loadTasksFromDatabase(String loggedInUsername, UIManager uiManager) {

        try {

            PreparedStatement statement = getConnection().prepareStatement("SELECT task, status FROM tasks WHERE created_by = ? or assigned_to = ?");

            statement.setString(1, uiManager.loggedInUsername);
            statement.setString(2, uiManager.loggedInUsername); // Include the user in assigned_to condition
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String task = resultSet.getString("task");
                String status = resultSet.getString("status");

                switch (status) {
                    case "pending", "Assigned":
                         uiManager.pendingTasks.add(task);
                        break;
                    case "processing":
                       uiManager.processingTasks.add(task);
                        break;
                    case "completed":
                        uiManager.completedTasks.add(task);
                        break;
                    default:
                        // Handle any other status here if needed
                        break;
                }
            }
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public String fetchAssignedUserForTaskFromDatabase(String task) {
        String assignedTo = null;
        try {
            // Connect to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/tasks", "root", "volvoA5.");

            // Prepare the SQL statement
            String sql = "SELECT assigned_to FROM tasks WHERE task = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, task);

            // Execute the query
            ResultSet resultSet = statement.executeQuery();

            // Retrieve the assigned user from the result set
            if (resultSet.next()) {
                assignedTo = resultSet.getString("assigned_to");
            }

            // Close the resources
            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return assignedTo;
    }


    public  void updateDBCreateTaskButton(String task){

        try {
            String username = "Administrator";
            PreparedStatement selectStatement = getConnection().prepareStatement("SELECT COUNT(*) FROM tasks WHERE task = ?");
            selectStatement.setString(1, task);
            ResultSet resultSet = selectStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            if (count == 0) {
                // The task doesn't exist, so insert it
                PreparedStatement insertStatement = getConnection().prepareStatement("INSERT INTO tasks (task, status, created_by) VALUES (?, ?, ?)");
                insertStatement.setString(1, task);
                insertStatement.setString(2, "pending");
                insertStatement.setString(3, username);
                insertStatement.executeUpdate();
                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Task Created");
                successAlert.setContentText("Task created successfully.");
                successAlert.showAndWait();
            } else {
                // The task already exists
                System.out.println("Task already exists.");
            }
        } catch (SQLException e) {
            // Handle any errors that may occur during database operations
            e.printStackTrace();
        }

    }

    public  void updateDBassigntaskbutton(String selectedTask,String selectedUser){
        try{
            PreparedStatement statement =  getConnection().prepareStatement("UPDATE tasks SET assigned_to = ?, status = 'Assigned' WHERE task = ?");
            statement.setString(1, selectedUser);
            statement.setString(2, selectedTask);
            statement.executeUpdate();
            statement.close();
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setTitle("Success");
            successAlert.setHeaderText("Task Assigned");
            successAlert.setContentText("Task assigned successfully.");
            successAlert.showAndWait();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
