package app;

import java.util.ArrayList;
import java.util.List;

public class Tracker<T extends Activity> {
    private List<T> records = new ArrayList<>();
    public void log(T activity) { records.add(activity); }
    public void logAll(List<? extends T> activities) { records.addAll(activities); }
    public List<T> getAll() { return records; }
}
