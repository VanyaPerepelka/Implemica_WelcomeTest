package assignments.assignment2.app;

public record Target(String from, String to) {

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }
}
