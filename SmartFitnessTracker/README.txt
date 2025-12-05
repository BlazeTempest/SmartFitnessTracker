SMART FITNESS TRACKER
PREREQUISITES

To run this project, you need the following installed on your machine:

Java Development Kit (JDK) 11 or higher

Verify installation by running: java -version

JavaFX SDK (Required if your JDK does not include JavaFX)

================================================================================
INSTALLATION & SETUP

--- Option 1: Running in IntelliJ IDEA ---

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
USAGE GUIDE

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

Created by CHILL GUYS.