package app;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

// User implements Serializable to save profile and history
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String name;
    private int age;
    private double weightKg;
    private List<Activity> activities = new ArrayList<>();

    public User(String name, int age, double weightKg) {
        this.name = name; this.age = age; this.weightKg = weightKg;
    }

    public void addActivity(Activity a) { activities.add(a); }
    public List<Activity> getActivities() { return activities; }
    public double getWeightKg() { return weightKg; }
    public String getName() { return name; }

    // Nested class
    public class HealthReport {
        private LocalDate date;
        public HealthReport(LocalDate date) { this.date = date; }

        public double totalCalories() {
            double s = 0.0; for (Activity a: activities) s += a.getCaloriesBurned(); return s;
        }

        public String summary() {
            StringBuilder sb = new StringBuilder();
            sb.append("Health Report for ").append(date).append("\n");
            sb.append("User: ").append(name).append("\n");
            sb.append("Total Calories: ").append(String.format("%.1f", totalCalories())).append(" kcal\n");
            for (Activity a: activities) {
                sb.append(" - ").append(a.getName()).append(": ")
                  .append(String.format("%.1f", a.getCaloriesBurned())).append(" kcal (")
                  .append(a.getIntensityLevel()).append(")\n");
            }
            return sb.toString();
        }
    }
}