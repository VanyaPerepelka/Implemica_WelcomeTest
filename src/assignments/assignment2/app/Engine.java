package assignments.assignment2.app;

import java.util.ArrayList;
import java.util.List;

/**ь
 * Calculates the shortest path from one city to another, using the Dijkstra’s algorithm.
 * Class fields fills {@link DataManager}
 */
public class Engine {
    private int numberOfCities;
    private final List<City> cities;
    private int numberOfPathsToFind;
    private final List<Target> targets;
    private String currentToCityName;

    public Engine() {
        cities = new ArrayList<>();
        targets = new ArrayList<>();
    }

    /**
     * Calculates the shortest path from one city to another.
     *
     * @return list of the shortest paths to all the distance specified in {@link this.targets}
     */
    public List<String> search() {
        List<String> minimumCostBetweenCities = new ArrayList<>();
        for (Target target : targets) {
            // definition variable for future recursive search in runDijkstrasAlgorithm();
            currentToCityName = target.getTo();
            City fromCity = cities.stream()
                    .filter(city -> target.getFrom().equals(city.getName()))
                    .findAny()
                    .orElse(null);
            assert fromCity != null;
            runDijkstrasAlgorithm(fromCity);
            City toCity = cities.stream()
                    .filter(city -> currentToCityName.equals(city.getName()))
                    .findAny()
                    .orElse(null);
            assert toCity != null;
            minimumCostBetweenCities.add(String.valueOf(toCity.getShortestCost()));
            // reset data before the next iteration
            cities.forEach(city -> {
                city.setShortestCost(0);
                city.setKnown(false);
            });
        }
        return minimumCostBetweenCities;
    }

    /**
     * Alternately, the city takes its neighbors, adds its own shortest cost and cost to the neighbor.
     * If the result obtained is less than they previously installed the shortest cost,
     * this value becomes its shortest cost. After the city has checked all the neighbors,
     * it becomes known and no one else will try to check the cost to it.
     * Similarly, this is doing recursively with each of its neighbors that have not yet marked as known.
     * See Dijkstra's Algorithm.
     *
     * @param mainCity city from which cost will be checked for all its neighbors
     */
    private void runDijkstrasAlgorithm(City mainCity) {
        for (Neighbor neighbor : mainCity.getNeighbours()) {
            City city = cities.get(neighbor.getIndex() - 1);
            if (city.isKnown()) {
                continue;
            }
            checkCommunicationBetweenCities(mainCity, neighbor, city);
            int smallerCostToCity = city.getShortestCost();
            int costAcrossMainCity = neighbor.getCost() + mainCity.getShortestCost();
            if (costAcrossMainCity < smallerCostToCity || smallerCostToCity == 0) {
                city.setShortestCost(costAcrossMainCity);
            } else {
                city.setShortestCost(smallerCostToCity);
            }
        }
        mainCity.setKnown(true);
        // recursive calculation for the neighbors that are not marked as known.
        for (Neighbor neighbor : mainCity.getNeighbours()) {
            City newMainCity = cities.get(neighbor.getIndex() - 1);
            if (newMainCity.isKnown()) {
                continue;
            }
            runDijkstrasAlgorithm(newMainCity);
        }
    }

    /**
     * Checks correct installed cost between the cities.
     *
     * @param mainCity        city which verified correct cost with a neighbor
     * @param neighbor node of neighbor which verified correct cost with {@param mainCity}
     * @param city            neighbor which verified correct cost with {@param mainCity}
     */
    private void checkCommunicationBetweenCities(City mainCity, Neighbor neighbor, City city) {
        if (city.getNeighbours().stream().
                noneMatch(node -> mainCity.getIndex() == node.getIndex()
                        && neighbor.getCost() == node.getCost())) {
            throw new IllegalArgumentException("Entered incorrect communication between the cities: "
                    + mainCity.getName() + " and "
                    + city.getName());
        }
    }

    public int getNumberOfCities() {
        return numberOfCities;
    }

    public void setNumberOfCities(int numberOfCities) {
        this.numberOfCities = numberOfCities;
    }

    public List<City> getCities() {
        return cities;
    }

    public int getNumberOfPathsToFind() {
        return numberOfPathsToFind;
    }

    public void setNumberOfPathsToFind(int numberOfPathsToFind) {
        this.numberOfPathsToFind = numberOfPathsToFind;
    }

    public List<Target> getTargets() {
        return targets;
    }
}
