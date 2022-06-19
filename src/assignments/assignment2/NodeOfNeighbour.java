package assignments.assignment2;

public record NodeOfNeighbour(int index, int cost) {

    public int getIndex() {
        return index;
    }

    public int getCost() {
        return cost;
    }
}
