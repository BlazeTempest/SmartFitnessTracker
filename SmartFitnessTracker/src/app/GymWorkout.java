package app;

public class GymWorkout extends Activity {
    private int sets;

    public GymWorkout(int durationMinutes, int sets) {
        super("Gym Workout", durationMinutes);
        this.sets = sets;
    }

    @Override
    public void calculateCalories(double userWeightKg) {
        double met = 6.0;
        caloriesBurned = met * userWeightKg * (durationMinutes / 60.0);
    }

    @Override
    public String getIntensityLevel() { return (sets > 3) ? "High" : "Moderate"; }
}
