package app;

import java.io.Serializable;

// Abstract Activity class now implements Serializable for data persistence
public abstract class Activity implements Trackable, Serializable {
    private static final long serialVersionUID = 1L; // Recommended for Serializable classes
    
    protected String name;
    protected int durationMinutes;
    protected double caloriesBurned;

    public Activity(String name, int durationMinutes) {
        this.name = name;
        this.durationMinutes = durationMinutes;
        this.caloriesBurned = 0.0;
    }

    // abstract method to be implemented by subclasses (OVERRIDE)
    public abstract void calculateCalories(double userWeightKg);

    // default polymorphic method - can be overridden
    public String getIntensityLevel() { return "Moderate"; }

    @Override
    public void track() {
        System.out.println("Tracking activity: " + name + " (" + durationMinutes + " min)"); 
    }

    public String getName() { return name; }
    public int getDurationMinutes() { return durationMinutes; }
    public double getCaloriesBurned() { return caloriesBurned; }
}