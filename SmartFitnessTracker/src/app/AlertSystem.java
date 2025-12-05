package app;

public class AlertSystem {
    public interface Alert { void trigger(String message); }
    private Alert alertHandler;
    public AlertSystem() {
        this.alertHandler = new Alert() {
            @Override public void trigger(String message) { System.out.println("[ALERT] " + message); }
        };
    }
    public void setAlertHandler(Alert a) { this.alertHandler = a; }
    public void send(String msg) { this.alertHandler.trigger(msg); }
}
