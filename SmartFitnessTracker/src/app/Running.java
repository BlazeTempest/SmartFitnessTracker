package app;

public class Running extends Activity {
    private double distanceKm;

    public Running(int durationMinutes, double distanceKm) {
        super("Running", durationMinutes);
        this.distanceKm = distanceKm;
    }

    @Override
    public void calculateCalories(double userWeightKg) {
        double met = 9.8;
        caloriesBurned = met * userWeightKg * (durationMinutes / 60.0);
    }

    @Override
    public String getIntensityLevel() {
        double speed = (durationMinutes == 0) ? 0 : (distanceKm / (durationMinutes/60.0));
        return (speed > 9) ? "High" : "Moderate";
    }
}
