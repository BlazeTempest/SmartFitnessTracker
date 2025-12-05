package app;

public class HealthAdvisor {
    public static String advise(User user) {
        double total = 0; for (Activity a: user.getActivities()) total += a.getCaloriesBurned();
        if (total < 200) return "Try adding a short cardio session (20-30 min) to increase calorie burn.";
        if (total > 800) return "Great job — maintain balance and rest properly.";
        return "Good work. Keep a mix of cardio and strength training through the week.";
    }
}
