package app;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Side;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FitnessApp extends Application {
    // Changed filename to avoid conflict with old single-user file
    private static final String DATA_FILE = "fitness_db.ser";
    
    // Data structures for multiple users
    private Map<String, User> userDatabase = new HashMap<>();
    private User currentUser;
    
    private Tracker<Activity> tracker;
    private AlertSystem alerts;
    
    // UI Components
    private ObservableList<String> activityItems;
    private ObservableList<PieChart.Data> pieChartData;
    private Label welcomeLabel;
    private Label totalCaloriesLabel;
    private Label statusLabel;
    private ComboBox<String> userSelector; // New Selector

    @Override
    public void start(Stage primaryStage) {
        tracker = new Tracker<>();
        alerts = new AlertSystem();
        alerts.setAlertHandler(msg -> statusLabel.setText(msg));

        // --- 1. Sidebar (Dark Theme) ---
        VBox sidebar = new VBox(20);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setPrefWidth(260);

        Label appTitle = new Label("FitTrack Pro");
        appTitle.getStyleClass().add("app-title");

        welcomeLabel = new Label("Guest Mode");
        welcomeLabel.getStyleClass().add("sidebar-welcome");

        Separator sep1 = new Separator();
        Separator sep2 = new Separator();

        // --- Switch User Section (New) ---
        Label switchUserLabel = new Label("Switch Profile");
        switchUserLabel.getStyleClass().add("sidebar-header");
        
        userSelector = new ComboBox<>();
        userSelector.setPromptText("Select User...");
        userSelector.setMaxWidth(Double.MAX_VALUE);
        userSelector.setOnAction(e -> switchUser(userSelector.getValue()));

        VBox switchBox = new VBox(10, switchUserLabel, userSelector);

        // --- Create User Section ---
        Label createProfileLabel = new Label("New Profile");
        createProfileLabel.getStyleClass().add("sidebar-header");
        
        TextField nameField = new TextField(); nameField.setPromptText("Name");
        TextField ageField = new TextField(); ageField.setPromptText("Age");
        TextField weightField = new TextField(); weightField.setPromptText("Weight (kg)");
        Button createUserBtn = new Button("Create");
        createUserBtn.setMaxWidth(Double.MAX_VALUE);
        createUserBtn.getStyleClass().add("btn-accent");

        VBox userForm = new VBox(10, createProfileLabel, nameField, ageField, weightField, createUserBtn);
        
        // --- Stats Section ---
        Label statsHeader = new Label("Quick Stats");
        statsHeader.getStyleClass().add("sidebar-header");
        totalCaloriesLabel = new Label("0 kcal");
        totalCaloriesLabel.getStyleClass().add("stat-highlight");
        
        VBox statsBox = new VBox(10, statsHeader, totalCaloriesLabel);

        sidebar.getChildren().addAll(appTitle, welcomeLabel, sep1, switchBox, sep2, userForm, new Separator(), statsBox);

        // --- 2. Main Content Area (Light Theme) ---
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.getStyleClass().add("main-content");
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        Label dashboardTitle = new Label("Dashboard");
        dashboardTitle.getStyleClass().add("page-title");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);

        // Card 1: Log Activity
        VBox logCard = createCard("Log Activity");
        ComboBox<String> activityType = new ComboBox<>(FXCollections.observableArrayList("Running","Gym Workout","Yoga","Swimming"));
        activityType.getSelectionModel().selectFirst();
        activityType.setMaxWidth(Double.MAX_VALUE);
        
        TextField durationField = new TextField(); durationField.setPromptText("Duration (min)");
        TextField extraField = new TextField(); extraField.setPromptText("Extra(Optional): km / sets / laps");
        Button addBtn = new Button("Add Activity");
        addBtn.getStyleClass().add("btn-primary");
        addBtn.setMaxWidth(Double.MAX_VALUE);
        
        VBox logForm = new VBox(15, activityType, durationField, extraField, addBtn);
        logCard.getChildren().add(logForm);

        // Card 2: History
        VBox historyCard = createCard("Recent History");
        activityItems = FXCollections.observableArrayList();
        ListView<String> listView = new ListView<>(activityItems);
        listView.setPrefHeight(200);
        historyCard.getChildren().add(listView);

        // Card 3: Visualization (Fixed Labels)
        VBox chartCard = createCard("Calorie Breakdown");
        pieChartData = FXCollections.observableArrayList();
        PieChart chart = new PieChart(pieChartData);
        chart.setPrefHeight(250);
        chart.setLabelsVisible(false); // Hide slice labels to prevent overflow
        chart.setLegendSide(Side.BOTTOM); // Move legend to bottom for better space
        chartCard.getChildren().add(chart);

        // Card 4: Advice
        VBox adviceCard = createCard("Health Insights");
        TextArea adviceOutput = new TextArea();
        adviceOutput.setWrapText(true);
        adviceOutput.setEditable(false);
        adviceOutput.setPrefHeight(150);
        Button getAdviceBtn = new Button("Get Recommendation");
        getAdviceBtn.getStyleClass().add("btn-secondary");
        getAdviceBtn.setOnAction(e -> {
            if (currentUser != null) adviceOutput.setText(HealthAdvisor.advise(currentUser));
        });
        adviceCard.getChildren().addAll(adviceOutput, getAdviceBtn);

        grid.add(logCard, 0, 0);
        grid.add(historyCard, 1, 0);
        grid.add(chartCard, 0, 1);
        grid.add(adviceCard, 1, 1);

        ColumnConstraints col1 = new ColumnConstraints(); col1.setPercentWidth(40);
        ColumnConstraints col2 = new ColumnConstraints(); col2.setPercentWidth(60);
        grid.getColumnConstraints().addAll(col1, col2);

        mainContent.getChildren().addAll(dashboardTitle, grid);

        BorderPane root = new BorderPane();
        root.setLeft(sidebar);
        root.setCenter(mainContent);

        statusLabel = new Label("Ready");
        statusLabel.setPadding(new Insets(5, 10, 5, 10));
        statusLabel.setStyle("-fx-text-fill: #7f8c8d; -fx-font-size: 12px;");
        root.setBottom(statusLabel);

        // --- Event Handlers ---

        createUserBtn.setOnAction(e -> {
            try {
                String n = nameField.getText().trim();
                if(n.isEmpty()) throw new Exception("Name required");
                if(userDatabase.containsKey(n)) {
                    showError("User '" + n + "' already exists. Please switch user.");
                    return;
                }
                
                int age = Integer.parseInt(ageField.getText().trim());
                double w = Double.parseDouble(weightField.getText().trim());
                
                User newUser = new User(n, age, w);
                userDatabase.put(n, newUser);
                updateUserSelector();
                userSelector.setValue(n); // Auto-select new user
                
                saveData();
                alerts.send("Profile created: " + n);
                
                // Clear form
                nameField.clear(); ageField.clear(); weightField.clear();
            } catch (Exception ex) {
                showError("Invalid input: " + ex.getMessage());
            }
        });

        addBtn.setOnAction(e -> {
            if (currentUser == null) { showError("Select or create a profile first."); return; }
            try {
                String type = activityType.getValue();
                int dur = Integer.parseInt(durationField.getText().trim());
                String extra = extraField.getText().trim();
                Activity a = null;
                
                switch (type) {
                    case "Running":
                        double dist = extra.isEmpty() ? 0.0 : Double.parseDouble(extra);
                        a = new Running(dur, dist); break;
                    case "Gym Workout":
                        int sets = extra.isEmpty() ? 0 : Integer.parseInt(extra);
                        a = new GymWorkout(dur, sets); break;
                    case "Yoga": a = new Yoga(dur); break;
                    case "Swimming":
                        int laps = extra.isEmpty() ? 0 : Integer.parseInt(extra);
                        a = new Swimming(dur, laps); break;
                }
                
                if (a != null) {
                    a.calculateCalories(currentUser.getWeightKg());
                    a.track();
                    currentUser.addActivity(a);
                    refreshUI();
                    saveData();
                    alerts.send("Logged: " + type);
                    durationField.clear(); extraField.clear();
                }
            } catch (Exception ex) { showError("Invalid activity data."); }
        });

        // Load DB on startup
        loadData();

        Scene scene = new Scene(root, 1100, 700);
        scene.getStylesheets().add(getClass().getResource("/resources/application.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("FitTrack Pro - Multi-User Dashboard");
        primaryStage.show();
    }

    // --- Helpers ---

    private void switchUser(String userName) {
        if(userName == null || !userDatabase.containsKey(userName)) return;
        currentUser = userDatabase.get(userName);
        tracker = new Tracker<>(); // Reset tracker for current view
        refreshUI();
        alerts.send("Switched to user: " + userName);
    }

    private void updateUserSelector() {
        // Remember selection or default to current
        String currentSelection = userSelector.getValue();
        userSelector.setItems(FXCollections.observableArrayList(userDatabase.keySet()));
        if(currentSelection != null && userDatabase.containsKey(currentSelection)) {
            userSelector.setValue(currentSelection);
        }
    }

    private void refreshUI() {
        if (currentUser == null) return;
        
        welcomeLabel.setText("Hello, " + currentUser.getName());
        activityItems.clear();
        double totalCal = 0;
        Map<String, Double> chartMap = new HashMap<>();

        for (Activity a : currentUser.getActivities()) {
            activityItems.add(String.format("%s | %d min | %.0f kcal", a.getName(), a.getDurationMinutes(), a.getCaloriesBurned()));
            totalCal += a.getCaloriesBurned();
            chartMap.put(a.getName(), chartMap.getOrDefault(a.getName(), 0.0) + a.getCaloriesBurned());
        }

        pieChartData.clear();
        for (Map.Entry<String, Double> entry : chartMap.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        totalCaloriesLabel.setText(String.format("%.0f kcal", totalCal));
    }

    private VBox createCard(String title) {
        VBox card = new VBox(15);
        card.getStyleClass().add("card");
        Label l = new Label(title);
        l.getStyleClass().add("card-title");
        card.getChildren().add(l);
        return card;
    }

    // --- Persistence ---

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(userDatabase);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadData() {
        File f = new File(DATA_FILE);
        if (f.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
                Object obj = ois.readObject();
                if (obj instanceof Map) {
                    userDatabase = (Map<String, User>) obj;
                    updateUserSelector();
                    if (!userDatabase.isEmpty()) {
                        // Auto-select first user
                        String firstUser = userDatabase.keySet().iterator().next();
                        userSelector.setValue(firstUser);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error loading DB: " + e.getMessage());
                // If incompatible, we start fresh
                userDatabase = new HashMap<>();
            }
        }
    }

    private void showError(String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR, msg, ButtonType.OK); 
        a.setHeaderText("Error"); a.showAndWait();
    }

    public static void main(String[] args) { launch(args); }
}