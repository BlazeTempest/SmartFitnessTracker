================================================================================
SMART FITNESS TRACKER

A Java-based desktop application for tracking physical activities, calculating
calorie expenditure, and managing personal health goals. Built with JavaFX and
Object-Oriented Programming (OOP) principles.

[ TABLE OF CONTENTS ]

Features

Prerequisites

Installation & Setup

Project Structure

Usage Guide

================================================================================

FEATURES
================================================================================

Multi-User Support: Create and switch between multiple user profiles.

Activity Logging: Track Running, Swimming, Yoga, and Gym Workouts.

Data Persistence: Automatic saving/loading of user data using Java
Serialization.

Visual Dashboard: Dynamic Pie Charts showing calorie breakdown.

Health Insights: Automated health advice based on your activity levels.

================================================================================
2. PREREQUISITES

To run this project, you need the following installed on your machine:

Java Development Kit (JDK) 11 or higher

Verify installation by running: java -version

JavaFX SDK (Required if your JDK does not include JavaFX)

Download from: https://gluonhq.com/products/javafx/

================================================================================
3. INSTALLATION & SETUP

--- Option 1: Running in IntelliJ IDEA (Recommended) ---

Open the Project:

Open IntelliJ -> File -> Open -> Select the project folder.

Add JavaFX Library:

Go to File -> Project Structure -> Libraries.

Click "+" -> Java -> Select the "lib" folder inside your downloaded
JavaFX SDK.

Configure Run Configuration:

Open "FitnessApp.java".

Click the Run button (Green Play Icon).

NOTE: If you get a "Module not found" error:
a. Go to Run -> Edit Configurations.
b. In "VM Options", add the following line (replace path with your own):
--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml

--- Option 2: Running in Eclipse ---

Import Project:

File -> New -> Java Project -> Uncheck "Use default location" ->
Browse to project folder.

Add User Library:

Right-click project -> Build Path -> Configure Build Path.

Go to Libraries -> Classpath -> Add External JARs.

Select all .jar files from the JavaFX SDK "lib" folder.

Run:

Right-click "FitnessApp.java" -> Run As -> Java Application.

(If required) Add the VM arguments mentioned above in Run Configurations.

--- Option 3: Running via Command Line ---

Navigate to the "src" folder (or the root where the "app" folder exists).

Compile:
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml app/*.java

Run:
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml app.FitnessApp

================================================================================
4. PROJECT STRUCTURE

src/
├── app/
│   ├── FitnessApp.java       # Main Entry Point (JavaFX)
│   ├── User.java             # User Data Model
│   ├── Activity.java         # Abstract Base Class
│   ├── Running.java          # Concrete Activity
│   ├── Swimming.java         # Concrete Activity
│   ├── GymWorkout.java       # Concrete Activity
│   ├── Yoga.java             # Concrete Activity
│   ├── Tracker.java          # Generic Tracker Logic
│   ├── HealthAdvisor.java    # Logic for advice generation
│   └── AlertSystem.java      # Notification System
└── resources/
└── application.css       # Styling for the Dashboard

================================================================================
5. USAGE GUIDE

Start the App: Launch "FitnessApp".

Create Profile: In the sidebar, enter your Name, Age, and Weight, then
click "Create".

Log Activity:

Select an activity type (e.g., Running).

Enter Duration (minutes).

Enter Extra info (Distance for running, Laps for swimming).

Click "Add Activity".

View Stats: The Pie Chart and Recent History list will update automatically.

Get Advice: Click "Get Recommendation" to see personalized feedback.

Switch User: Use the "Switch Profile" dropdown in the sidebar to toggle
between users.

Created by Blazeri.
