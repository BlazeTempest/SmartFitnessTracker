package app;

public class Yoga extends Activity {
    public Yoga(int durationMinutes) { super("Yoga", durationMinutes); }

    @Override
    public void calculateCalories(double userWeightKg) {
        double met = 3.0;
        caloriesBurned = met * userWeightKg * (durationMinutes / 60.0);
    }

    @Override
    public String getIntensityLevel() { return "Low"; }
}
