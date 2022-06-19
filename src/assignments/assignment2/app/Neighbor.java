package assignments.assignment2.app;

public record Neighbor(int index, int cost) {

    public int getIndex() {
        return index;
    }

    public int getCost() {
        return cost;
    }
}
