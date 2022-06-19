package assignments.assignment2;

import java.util.ArrayList;
import java.util.List;

public class City {
    /**
     * true - if found the shortest path to the city
     */
    private boolean known;
    private String name;
    private int index;
    private int shortestCost;
    private int numberOfNeighbours;
    private final List<NodeOfNeighbour> neighbours;

    public City() {
        neighbours = new ArrayList<>();
    }

    public boolean isKnown() {
        return known;
    }

    public void setKnown(boolean known) {
        this.known = known;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getShortestCost() {
        return shortestCost;
    }

    public void setShortestCost(int shortestCost) {
        this.shortestCost = shortestCost;
    }

    public List<NodeOfNeighbour> getNeighbours() {
        return neighbours;
    }

    public int getNumberOfNeighbours() {
        return numberOfNeighbours;
    }

    public void setNumberOfNeighbours(int numberOfNeighbours) {
        this.numberOfNeighbours = numberOfNeighbours;
    }
}
