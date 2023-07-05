//package com.example.finalmvcproj;
//import javafx.application.Application;
//import javafx.beans.property.ReadOnlyStringWrapper;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.HBox;
//import javafx.scene.layout.VBox;
//import javafx.stage.Stage;
//import javafx.geometry.Insets;
//import javafx.scene.control.DatePicker;
//
//
//import java.io.IOException;
//import java.sql.Connection;
//
//public class UIManager extends Application {
//    private Scene loginScene;
//    private Scene taskManagerScene;
//    public String loggedInUsername;
//
//    private  Scene oneuiscene;
//
//    public ObservableList<String> pendingTasks = FXCollections.observableArrayList();
//    public ObservableList<String> processingTasks = FXCollections.observableArrayList();
//    public ObservableList<String> completedTasks = FXCollections.observableArrayList();
//
//    private Scene adminLayout;
//    public void setUnassignedTasksListView(ListView<String> listView) {
//        unassignedTasksListView = listView;
//    }
//    public void setAssignedTasksListView(ListView<String> listView) {
//        assignedTasksListView = listView;
//    }
//
//
//    public void start(Stage primaryStage) throws IOException {
//
//        primaryStage.setTitle("Task Manager ");
//
//        //creating the login screen ui components
//        TextField usernameField = new TextField();
//        PasswordField passwordField = new PasswordField();
//        Button loginButton = new Button("Login");
////        Button adminButton = new Button("Admin");
//        Button signupButton = new Button("Sign Up");
//
//
//        VBox loginBox = new VBox(
//                new Label("Username"), usernameField,
//                new Label("Password"), passwordField,
//                new HBox(loginButton, signupButton) // Add login, signup, and admin buttons to a horizontal box
//        );
//        loginBox.setSpacing(10);
//        loginBox.setPadding(new javafx.geometry.Insets(10));
//        loginScene = new Scene(loginBox, 300, 200);
//        primaryStage.setTitle("Task Manager ");
//
//// Set the login scene as the initial scene
//        primaryStage.setScene(loginScene);
//        primaryStage.show();
//
//        // Create the task manager UI components
//        TextField taskTextField = new TextField();
//        Button addButton = new Button("Add Task");
//        ComboBox priorityCombo = new ComboBox();
//        priorityCombo.getItems().addAll("High","Medium","low");
//        Button performButton = new Button("Perform Task");
//        Button completeButton = new Button("Complete Task");
//        Button logoutButton = new Button("Log Out");
//        logoutButton.setOnAction(event -> {
//            // Clear the logged-in username
//            loggedInUsername = null;
//
//            // Clear the task lists
//            pendingTasks.clear();
//            processingTasks.clear();
//            completedTasks.clear();
//
//            // Show the login screen
//            primaryStage.setScene(loginScene);
//        });
//
//        // Create layout for the task manager screen
//        BorderPane root = new BorderPane();
//        ListView<String> pendingListView = new ListView<>();
//        ListView<String> processingListView = new ListView<>();
//        ListView<String> completedListView = new ListView<>();
//
//        // Bind the pendingListView to the pendingTasks list
//        pendingListView.setItems(pendingTasks);
//
//        // Bind the processingListView to the processingTasks list
//        processingListView.setItems(processingTasks);
//
//        // Bind the completedListView to the completedTasks list
//        completedListView.setItems(completedTasks);
//
//        // Create a VBox for the task manager UI
//        VBox taskManagerBox = new VBox(
//                new Label("Task Manager"),
//                new HBox(taskTextField, addButton),
//                new HBox(priorityCombo),
//                new HBox(performButton),
//                new HBox(completeButton),
//                new Label("Pending Tasks"),
//                pendingListView,
//                new Label("Processing Tasks"),
//                processingListView,
//                new Label("Completed Tasks"),
//                completedListView,
//                new HBox(logoutButton)
//
//        );
//        taskManagerBox.setSpacing(10);
//        taskManagerBox.setPadding(new javafx.geometry.Insets(10));
//        taskManagerScene = new Scene(taskManagerBox, 600, 400);
//
//        //call the database connection method from the imported class of the database connection
//        DatabaseConnection databaseConnection = new DatabaseConnection();
//        //get a connection to the database
//        Connection connection = databaseConnection.getConnection();
//        // Use the connection for database operations
//
//
//        // Create UI components for the admin window
//        TextField taskField = new TextField();
//        Button createTaskButton = new Button("Create Task");
//        Label deadlineLabel = new Label("Deadline");
//        DatePicker deadlinePicker = new DatePicker();
//        Label reminderLabel = new Label("Reminder Date");
//        DatePicker reminderPicker = new DatePicker();
//        ListView<String> unassignedTasksListView = new ListView<>();
//        ComboBox<String> userComboBox = new ComboBox<>();
//        Button assignTaskButton = new Button("Assign Task");
//        Button moreButton = new Button("MORE");
//        Button logoutButton2 = new Button("LogOut");
//
//
//        // Create the admin layout
//        VBox adminLayout = createAdminLayout(
//                taskField,
//                createTaskButton,
//                deadlineLabel,
//                deadlinePicker,
//                reminderLabel,
//                reminderPicker,
//                unassignedTasksListView,
//                userComboBox,
//                assignTaskButton,
//                moreButton,
//                logoutButton2
//        );
//        adminLayout.getChildren().add(deadlineLabel);
//        adminLayout.getChildren().add(deadlinePicker);
//        adminLayout.getChildren().add(reminderLabel);
//        adminLayout.getChildren().add(reminderPicker);
//        adminLayout.getChildren().add(logoutButton2);
//
//
//
//        //logoutbutton2 event handler
//        logoutButton2.setOnAction(actionEvent -> {
//            loggedInUsername = null;
//            primaryStage.setScene(loginScene);
//        });
//        databaseConnection.fetchUsersFromDB(userComboBox, this);
//        databaseConnection.fetchUnassignedTasksfromDB(unassignedTasksListView,this);
//        databaseConnection.fetchAssignedTasksfromDB(assignedTasksListView,this);
//
//        // Set the admin layout as the content of the admin stage
//        Scene adminScene = new Scene(adminLayout, 600, 400);
//
//
//        // Event handler for the login button
//        loginButton.setOnAction(event -> {
//            String username = usernameField.getText().trim();
//            String password = passwordField.getText().trim();
//            Authentication authentication1 = new Authentication();
//            DatabaseConnection databaseConnection1 = new DatabaseConnection();
//
//            if (username.equals("A") && password.equals("a")) {
//                showAlert("Authorized", "Welcome to the Admin page");
//                primaryStage.setScene(adminLayout.getScene());}
//            else if (authentication1.validateLogin(username, password)) {
//                loggedInUsername = username; // Set the logged-in username
//                primaryStage.setScene(taskManagerScene); // Show the task manager screen
//                databaseConnection1.loadTasksFromDatabase(username, this);
//            } else {
//                showAlert("Invalid credentials", "Please enter valid username and password or sign up.");
//            }
//        });
//
//
//
//
//        // Event handler for the signup button
//        signupButton.setOnAction(event -> {
//            String username = usernameField.getText().trim();
//            String password = passwordField.getText().trim();
//
//            if (username.isEmpty() || password.isEmpty()) {
//                showAlert("Error", "Username and password cannot be blank.");
//            } else {
//                Authentication authentication = new Authentication();
//                          boolean signupSuccessful = authentication.signup(username, password);
//
//                if (signupSuccessful) {
//                    showAlert("Success", "User created successfully. Please log in.");
//                } else {
//                    showAlert("Error", "Failed to create user. Please try again.");
//                }
//            }
//        });
//
//
//        //Event handler for the Add task button
//        addButton.setOnAction(event -> {
//            String task = taskTextField.getText().trim();
//            String priority = (String) priorityCombo.getValue();
//            if (task != null && !task.isEmpty() && priority!= null) {
//                databaseConnection.addTask(task, loggedInUsername,priority,this);
//                taskTextField.clear();
//                priorityCombo.getSelectionModel().clearSelection();
//            }
//        });
//
//
//        // Event handler for the perform button
//        performButton.setOnAction(event -> {
//            String selectedtask = pendingListView.getSelectionModel().getSelectedItem();
//            if (selectedtask != null) {
//                pendingTasks.remove(selectedtask);
//                processingTasks.add(selectedtask);
//                databaseConnection.moveTask(selectedtask,"processing",loggedInUsername,this);
//            }
//        });
//
//        // Event handler for the complete button
//        completeButton.setOnAction(event -> {
//            String selectedTask = processingListView.getSelectionModel().getSelectedItem();
//            if (selectedTask != null) {
//                processingTasks.remove(selectedTask); // Remove the task from the processingTasks list
//                completedTasks.add(selectedTask); // Add the task to the completedTasks list
//                databaseConnection.moveTask(selectedTask,"completed",loggedInUsername,this);
//            }
//        });
//
//
//    }
//
//
//    public static void showAlert(String title, String message) {
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle(title);
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//    }
//
//
//    // Create UI components for the admin window
//    TextField taskField = new TextField();
//    Button createTaskButton = new Button("Create Task");
//    ListView<String> unassignedTasksListView = new ListView<>();
//    ComboBox<String> userComboBox = new ComboBox<>();
//    Button assignTaskButton = new Button("Assign Task");
//
//
//    static ListView<String> assignedTasksListView = new ListView<>();
//    ListView<String> assignedUsersListView = new ListView<>();
//    Button moreButton = new Button("MORE");
//
//    // Method to create the layout and GUI components of the admin window
//    public  VBox createAdminLayout(
//            TextField taskField,
//            Button createTaskButton,
//            Label deadlineLabel, DatePicker deadlinePicker, Label reminderLabel, DatePicker reminderPicker, ListView<String> unassignedTasksListView,
//            ComboBox<String> userComboBox,
//            Button assignTaskButton,
//            Button moreButton,
//            Button logoutButton2) {
//        VBox adminLayout = new VBox(
//                new Label("Create Task"), taskField,
//                createTaskButton,
//                new Label("Unassigned Tasks"), unassignedTasksListView,
//                new Label("Assign To"), userComboBox,
//                assignTaskButton,
//                new Label("Assigned Tasks"), assignedTasksListView,
//                moreButton
//        );
//        adminLayout.setSpacing(10);
//        adminLayout.setPadding(new Insets(10));
//
//
//        // Event handler for create task button
//        createTaskButton.setOnAction(event -> {
//            String task = taskField.getText().trim();
//
//            if (task.isEmpty()) {
//                // Check if the task is empty
//                showAlert("Error", "Task cannot be empty.");
//                return;
//            } else if (unassignedTasksListView.getItems().contains(task)) {
//                // Task already exists, display an error message
//                showAlert("Error", "The task already exists in the list.");
//            } else {
//                // Task is unique, add it to the list
//                unassignedTasksListView.getItems().add(task);
//                taskField.clear();
//            }
//
//            DatabaseConnection databaseConnection = new DatabaseConnection();
//            databaseConnection.updateDBCreateTaskButton(task);
//
//
//        });
//
//        // Event handler for assign task button
//        assignTaskButton.setOnAction(event -> {
//                    String selectedTask = unassignedTasksListView.getSelectionModel().getSelectedItem();
//                    String selectedUser = userComboBox.getValue();
//                    if (selectedTask != null && selectedUser != null) {
//                        assignedTasksListView.getItems().add(selectedTask);
//                        unassignedTasksListView.getItems().remove(selectedTask);
//                        DatabaseConnection databaseConnection = new DatabaseConnection();
//                        databaseConnection.updateDBassigntaskbutton(selectedTask,selectedUser);
//
//                    }
//                    ;
//                }
//        );
//
//
//        // Event handler for the "More" button
//        moreButton.setOnAction(event -> {
//            // Create a new window for displaying the table
//            Stage moreStage = new Stage();
//            moreStage.setTitle("More Window");
//
//            // Create the table columns
//            TableColumn<String, String> taskColumn = new TableColumn<>("Task");
//            taskColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));
//
//            TableColumn<String, String> assignedToColumn = new TableColumn<>("Assigned User");
//            assignedToColumn.setCellValueFactory(cellData -> {
//                String task = cellData.getValue();
//                DatabaseConnection databaseConnection = new DatabaseConnection();
//                String assignedTo =databaseConnection.fetchAssignedUserForTaskFromDatabase(task);
//                return new ReadOnlyStringWrapper(assignedTo);
//            });
//
//            // Create the table and add the columns
//            TableView<String> taskTable = new TableView<>();
//            taskTable.getColumns().addAll(taskColumn, assignedToColumn);
//
//            // Get the tasks from the assigned tasks ListView
//            ObservableList<String> assignedTasks = assignedTasksListView.getItems();
//
//            // Fetch the corresponding assigned users for each task from the database and populate the table
//            for (String task : assignedTasks) {
//                taskTable.getItems().add(task);
//            }
//
//            // Create layout for the more window
//            VBox moreLayout = new VBox(taskTable);
//            Scene moreScene = new Scene(moreLayout, 300, 400);
//
//            // Set the more scene to the more stage
//            moreStage.setScene(moreScene);
//            moreStage.show();
//        });
//        return adminLayout;
//    }
//}





package com.example.finalmvcproj;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;


import java.io.IOException;
import java.sql.Connection;

public class UIManager extends Application {
    private Scene loginScene;
    private Scene taskManagerScene;
    public String loggedInUsername;

    private  Scene oneuiscene;

    public ObservableList<String> pendingTasks = FXCollections.observableArrayList();
    public ObservableList<String> processingTasks = FXCollections.observableArrayList();
    public ObservableList<String> completedTasks = FXCollections.observableArrayList();

    private Scene adminLayout;
    public void setUnassignedTasksListView(ListView<String> listView) {
        unassignedTasksListView = listView;
    }
    public void setAssignedTasksListView(ListView<String> listView) {
        assignedTasksListView = listView;
    }


    public void start(Stage primaryStage) throws IOException {

        primaryStage.setTitle("Task Manager ");

        //creating the login screen ui components
        TextField usernameField = new TextField();
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Login");
//        Button adminButton = new Button("Admin");
        Button signupButton = new Button("Sign Up");


        VBox loginBox = new VBox(
                new Label("Username"), usernameField,
                new Label("Password"), passwordField,
                new HBox(loginButton, signupButton) // Add login, signup, and admin buttons to a horizontal box
        );
        loginBox.setSpacing(10);
        loginBox.setPadding(new javafx.geometry.Insets(10));
        loginScene = new Scene(loginBox, 300, 200);
        primaryStage.setTitle("Task Manager ");

// Set the login scene as the initial scene
        primaryStage.setScene(loginScene);
        primaryStage.show();

        // Create the task manager UI components
        TextField taskTextField = new TextField();
        Button addButton = new Button("Add Task");
        ComboBox priorityCombo = new ComboBox();
        priorityCombo.getItems().addAll("High","Medium","low");
        Button performButton = new Button("Perform Task");
        Button completeButton = new Button("Complete Task");
        Button logoutButton = new Button("Log Out");

        // Create UI components for the admin window
        TextField taskField = new TextField();
        Button createTaskButton = new Button("Create Task");
        Label deadlineLabel = new Label("Deadline");
        DatePicker deadlinePicker = new DatePicker();
        Label reminderLabel = new Label("Reminder Date");
        DatePicker reminderPicker = new DatePicker();
        ListView<String> unassignedTasksListView = new ListView<>();
        ComboBox<String> userComboBox = new ComboBox<>();
        Button assignTaskButton = new Button("Assign Task");
        Button moreButton = new Button("MORE");

        Button logoutButton2 = new Button("LogOut");
        logoutButton.setOnAction(event -> {
            // Clear the logged-in username
            loggedInUsername = null;

            // Clear the task lists
            pendingTasks.clear();
            processingTasks.clear();
            completedTasks.clear();

            // Show the login screen
            primaryStage.setScene(loginScene);
        });

        // Create layout for the task manager screen
        BorderPane root = new BorderPane();
        ListView<String> pendingListView = new ListView<>();
        ListView<String> processingListView = new ListView<>();
        ListView<String> completedListView = new ListView<>();

        // Bind the pendingListView to the pendingTasks list
        pendingListView.setItems(pendingTasks);

        // Bind the processingListView to the processingTasks list
        processingListView.setItems(processingTasks);

        // Bind the completedListView to the completedTasks list
        completedListView.setItems(completedTasks);

        // Create a VBox for the task manager UI
        VBox taskManagerBox = new VBox(
                new Label("Task Manager"),
                new HBox(taskTextField, addButton),
                new HBox(priorityCombo),
                new HBox(performButton),
                new HBox(completeButton),
                new Label("Pending Tasks"),
                pendingListView,
                new Label("Processing Tasks"),
                processingListView,
                new Label("Completed Tasks"),
                completedListView,
                new HBox(logoutButton)

        );


        taskManagerBox.setSpacing(10);
        taskManagerBox.setPadding(new javafx.geometry.Insets(10));
        taskManagerScene = new Scene(taskManagerBox, 600, 400);

        //call the database connection method from the imported class of the database connection
        DatabaseConnection databaseConnection = new DatabaseConnection();
        //get a connection to the database
        Connection connection = databaseConnection.getConnection();
        // Use the connection for database operations




        // Create the admin layout
        VBox adminLayout = createAdminLayout(
                taskField,
                createTaskButton,
                deadlineLabel,
                deadlinePicker,
                reminderLabel,
                reminderPicker,
                unassignedTasksListView,
                userComboBox,
                assignTaskButton,
                moreButton,
                logoutButton2
        );
        adminLayout.getChildren().add(deadlineLabel);
        adminLayout.getChildren().add(deadlinePicker);
        adminLayout.getChildren().add(reminderLabel);
        adminLayout.getChildren().add(reminderPicker);
        adminLayout.getChildren().add(logoutButton2);



        //logoutbutton2 event handler
        logoutButton2.setOnAction(actionEvent -> {
            loggedInUsername = null;
            primaryStage.setScene(loginScene);
        });
        databaseConnection.fetchUsersFromDB(userComboBox, this);
        databaseConnection.fetchUnassignedTasksfromDB(unassignedTasksListView,this);
        databaseConnection.fetchAssignedTasksfromDB(assignedTasksListView,this);

        // Set the admin layout as the content of the admin stage
        Scene adminScene = new Scene(adminLayout, 600, 400);


        // Event handler for the login button
        loginButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            Authentication authentication1 = new Authentication();
            DatabaseConnection databaseConnection1 = new DatabaseConnection();

            if (username.equals("A") && password.equals("a")) {
                showAlert("Authorized", "Welcome to the Admin page");
                primaryStage.setScene(adminLayout.getScene());}
            else if (authentication1.validateLogin(username, password)) {
                loggedInUsername = username; // Set the logged-in username
                primaryStage.setScene(taskManagerScene); // Show the task manager screen
                databaseConnection1.loadTasksFromDatabase(username, this);
            } else {
                showAlert("Invalid credentials", "Please enter valid username and password or sign up.");
            }
        });




        // Event handler for the signup button
        signupButton.setOnAction(event -> {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();

            if (username.isEmpty() || password.isEmpty()) {
                showAlert("Error", "Username and password cannot be blank.");
            } else {
                Authentication authentication = new Authentication();
                boolean signupSuccessful = authentication.signup(username, password);

                if (signupSuccessful) {
                    showAlert("Success", "User created successfully. Please log in.");
                } else {
                    showAlert("Error", "Failed to create user. Please try again.");
                }
            }
        });


        //Event handler for the Add task button
        addButton.setOnAction(event -> {
            String task = taskTextField.getText().trim();
            String priority = (String) priorityCombo.getValue();
            if (task != null && !task.isEmpty() && priority!= null) {
                databaseConnection.addTask(task, loggedInUsername,priority,this);
                taskTextField.clear();
                priorityCombo.getSelectionModel().clearSelection();
            }
        });


        // Event handler for the perform button
        performButton.setOnAction(event -> {
            String selectedtask = pendingListView.getSelectionModel().getSelectedItem();
            if (selectedtask != null) {
                pendingTasks.remove(selectedtask);
                processingTasks.add(selectedtask);
                databaseConnection.moveTask(selectedtask,"processing",loggedInUsername,this);
            }
        });

        // Event handler for the complete button
        completeButton.setOnAction(event -> {
            String selectedTask = processingListView.getSelectionModel().getSelectedItem();
            if (selectedTask != null) {
                processingTasks.remove(selectedTask); // Remove the task from the processingTasks list
                completedTasks.add(selectedTask); // Add the task to the completedTasks list
                databaseConnection.moveTask(selectedTask,"completed",loggedInUsername,this);
            }
        });


    }


    public static void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    // Create UI components for the admin window
    TextField taskField = new TextField();
    Button createTaskButton = new Button("Create Task");
    ListView<String> unassignedTasksListView = new ListView<>();
    ComboBox<String> userComboBox = new ComboBox<>();
    Button assignTaskButton = new Button("Assign Task");


    static ListView<String> assignedTasksListView = new ListView<>();
    ListView<String> assignedUsersListView = new ListView<>();
    Button moreButton = new Button("MORE");

    // Method to create the layout and GUI components of the admin window
    public  VBox createAdminLayout(
            TextField taskField,
            Button createTaskButton,
            Label deadlineLabel, DatePicker deadlinePicker, Label reminderLabel, DatePicker reminderPicker, ListView<String> unassignedTasksListView,
            ComboBox<String> userComboBox,
            Button assignTaskButton,
            Button moreButton,
            Button logoutButton2) {
        VBox adminLayout = new VBox(
                new Label("Create Task"), taskField,
                createTaskButton,
                new Label("Unassigned Tasks"), unassignedTasksListView,
                new Label("Assign To"), userComboBox,
                assignTaskButton,
                new Label("Assigned Tasks"), assignedTasksListView,
                moreButton
        );
        adminLayout.setSpacing(10);
        adminLayout.setPadding(new Insets(10));


        // Event handler for create task button
        createTaskButton.setOnAction(event -> {
            String task = taskField.getText().trim();

            if (task.isEmpty()) {
                // Check if the task is empty
                showAlert("Error", "Task cannot be empty.");
                return;
            } else if (unassignedTasksListView.getItems().contains(task)) {
                // Task already exists, display an error message
                showAlert("Error", "The task already exists in the list.");
            } else {
                // Task is unique, add it to the list
                unassignedTasksListView.getItems().add(task);
                taskField.clear();
            }

            DatabaseConnection databaseConnection = new DatabaseConnection();
            databaseConnection.updateDBCreateTaskButton(task);


        });

        // Event handler for assign task button
        assignTaskButton.setOnAction(event -> {
                    String selectedTask = unassignedTasksListView.getSelectionModel().getSelectedItem();
                    String selectedUser = userComboBox.getValue();
                    if (selectedTask != null && selectedUser != null) {
                        assignedTasksListView.getItems().add(selectedTask);
                        unassignedTasksListView.getItems().remove(selectedTask);
                        DatabaseConnection databaseConnection = new DatabaseConnection();
                        databaseConnection.updateDBassigntaskbutton(selectedTask,selectedUser);

                    }
                    ;
                }
        );


        // Event handler for the "More" button
        moreButton.setOnAction(event -> {
            // Create a new window for displaying the table
            Stage moreStage = new Stage();
            moreStage.setTitle("More Window");

            // Create the table columns
            TableColumn<String, String> taskColumn = new TableColumn<>("Task");
            taskColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue()));

            TableColumn<String, String> assignedToColumn = new TableColumn<>("Assigned User");
            assignedToColumn.setCellValueFactory(cellData -> {
                String task = cellData.getValue();
                DatabaseConnection databaseConnection = new DatabaseConnection();
                String assignedTo =databaseConnection.fetchAssignedUserForTaskFromDatabase(task);
                return new ReadOnlyStringWrapper(assignedTo);
            });

            // Create the table and add the columns
            TableView<String> taskTable = new TableView<>();
            taskTable.getColumns().addAll(taskColumn, assignedToColumn);

            // Get the tasks from the assigned tasks ListView
            ObservableList<String> assignedTasks = assignedTasksListView.getItems();

            // Fetch the corresponding assigned users for each task from the database and populate the table
            for (String task : assignedTasks) {
                taskTable.getItems().add(task);
            }

            // Create layout for the more window
            VBox moreLayout = new VBox(taskTable);
            Scene moreScene = new Scene(moreLayout, 300, 400);

            // Set the more scene to the more stage
            moreStage.setScene(moreScene);
            moreStage.show();
        });
        return adminLayout;
    }
}
