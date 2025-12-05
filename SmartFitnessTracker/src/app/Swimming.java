package app;

public class Swimming extends Activity {
    private int laps;

    public Swimming(int durationMinutes, int laps) {
        super("Swimming", durationMinutes);
        this.laps = laps;
    }

    @Override
    public void calculateCalories(double userWeightKg) {
        double met = 8.0;
        caloriesBurned = met * userWeightKg * (durationMinutes / 60.0);
    }

    @Override
    public String getIntensityLevel() { return (laps > 20) ? "High" : "Moderate"; }
}
