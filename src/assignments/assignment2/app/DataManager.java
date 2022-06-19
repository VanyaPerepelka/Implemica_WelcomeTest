package assignments.assignment2.app;

import java.util.ArrayList;
import java.util.List;

public class DataManager{

    private static final int NUMBER_OF_TESTS = 1;
    private static final int NUMBER_OF_CITIES = 2;
    private static final int CITY_NAME = 3;
    private static final int NUMBER_OF_NEIGHBOURS = 4;
    private static final int INDEX_AND_TRANSPORTATION_COST = 5;
    private static final int NUMBER_OF_PATHS_TO_FIND = 6;
    private static final int SOURCE_NAME_AND_DESTINATION_NAME = 7;
    private static final int FINISH_ALL_PHASES = 8;

    /**
     * indicates the current phase.
     */
    private int currentParameterPhase = 1;
    private int numberOfTests = 0;
    private int indexCityCount = 1;

    /**
     * Created and filled for each test.
     */
    private Engine engine;

    /**
     * Size depends on the number of tests.
     */
    private final List<Engine> engineList;

    private City city;

    public DataManager() {
        engineList = new ArrayList<>();
    }

    public void executeConsoleData(String readLine) {
        try {
            if ("-1".equals(readLine)) {
                System.out.println("seems wired value [" + readLine + "]");
            }
            if (!"".equals(readLine)) {
                setParameter(readLine);
            } else {
                if (currentParameterPhase != FINISH_ALL_PHASES) {
                    System.out.println("Insufficient parameters. Please continue to enter the parameters.");
                }
                for (Engine engine : engineList) {
                    List<String> results = engine.search();
                    results.forEach(System.out::println);
                    System.out.println();
                }
                engineList.clear();
                currentParameterPhase = NUMBER_OF_TESTS;
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Handles and validates input data depending on the current phase.
     * Then parameter fits in .
     *
     * @param inputParameter input parameter which is validated and processed depending on the current phase.
     * @throws IllegalArgumentException 1. number of tests more than 10;
     *                                  2. number of cities more than 10000;
     *                                  3. nmber of neighbors more than the number of cities;
     *                                  4. number of of path to find more than 100.
     */
    private void setParameter(String inputParameter) {
        switch (currentParameterPhase) {
            case NUMBER_OF_TESTS -> {
                numberOfTests = checkInteger(inputParameter, "number of tests");
                if (numberOfTests > 10) {
                    throw new IllegalArgumentException("Number of tests should be less than 10. Please try again.");
                }
                currentParameterPhase = NUMBER_OF_CITIES;
            }
            case NUMBER_OF_CITIES -> {
                engine = new Engine();
                int numberOfCities = checkInteger(inputParameter, "number of cities");
                if (numberOfCities > 10000) {
                    throw new IllegalArgumentException("Number of cities should be less than 10000. Please try again.");
                }
                engine.setNumberOfCities(numberOfCities);
                currentParameterPhase = CITY_NAME;
            }
            case CITY_NAME -> {
                checkNewCityName(inputParameter);
                city = new City();
                city.setName(inputParameter);
                city.setIndex(indexCityCount++);
                engine.getCities().add(city);
                currentParameterPhase = NUMBER_OF_NEIGHBOURS;
            }
            case NUMBER_OF_NEIGHBOURS -> {
                int numberOfNeighbours = checkInteger(inputParameter, "numner of neighbours");
                if (engine.getNumberOfCities() - 1 < numberOfNeighbours) {
                    throw new IllegalArgumentException("Number of neighbors more than the number of cities. " +
                            "Please try again.");
                }
                city.setNumberOfNeighbours(numberOfNeighbours);
                currentParameterPhase = INDEX_AND_TRANSPORTATION_COST;
            }
            case INDEX_AND_TRANSPORTATION_COST -> {
                String[] neighboursParameters = inputParameter.split(" ");
                city.getNeighbours().add(checkIndexAndCostOfNeighbour(neighboursParameters));
                // if all the neighbors entered
                if (city.getNeighbours().size() == city.getNumberOfNeighbours()) {
                    // if all cities entered
                    if (engine.getCities().size() == engine.getNumberOfCities()) {
                        currentParameterPhase = NUMBER_OF_PATHS_TO_FIND;
                        break;
                    }
                    currentParameterPhase = CITY_NAME;
                }
            }
            case NUMBER_OF_PATHS_TO_FIND -> {
                int numberOfPathsToFind = checkInteger(inputParameter, "number of path to find");
                if (numberOfPathsToFind > 100) {
                    throw new IllegalArgumentException("Number of of path to find should be less than 100. " +
                            "Please try again.");
                }
                engine.setNumberOfPathsToFind(numberOfPathsToFind);
                currentParameterPhase = SOURCE_NAME_AND_DESTINATION_NAME;
            }
            case SOURCE_NAME_AND_DESTINATION_NAME -> {
                String[] namesOfCities = inputParameter.split(" ");
                List<Target> targetsList = engine.getTargets();
                targetsList.add(checkSourceNameAndDestinationName(namesOfCities));
                if (targetsList.size() == engine.getNumberOfPathsToFind()) {
                    engineList.add(engine);
                    if (engineList.size() != numberOfTests) {
                        currentParameterPhase = NUMBER_OF_CITIES;
                        indexCityCount = 1;
                        break;
                    }
                    currentParameterPhase = FINISH_ALL_PHASES;
                }
            }
        }
    }

    /**
     * Validates parameters of neighbour and collects them to {@link Neighbor}.
     *
     * @param neighbourParameters pair of parameters of neighbour separated by a space.
     * @return collected pair of parameters of neighbour to {@link Neighbor}
     * @throws IllegalArgumentException 1. {@param neighbourParameters} contains more then 2 parameters;
     *                                  2. parameters are not positive integer;
     *                                  3. neighbor index is more than numer of cities;
     *                                  4. neighbor index is equal to the index of the current city;
     *                                  5. has already been specified this neighbor index for current city;
     *                                  6. city at the specified neighbor index has been created
     *                                  and the current city is not specified as a neighbor or their cost is different.
     */
    private Neighbor checkIndexAndCostOfNeighbour(String[] neighbourParameters) {
        if (neighbourParameters.length != 2) {
            throw new IllegalArgumentException("You must enter 2 parameters: index of a city and the transportation cost" +
                    " separated by a space. Please try again.");
        }
        int index = checkInteger(neighbourParameters[0], "index of neighbours");
        int cost = checkInteger(neighbourParameters[1], "cost to neighbours");
        if (index > engine.getNumberOfCities()) {
            throw new IllegalArgumentException("Index greater than the specified number of cities. Please try again.");
        }
        if (index == city.getIndex()) {
            throw new IllegalArgumentException("Index is the index of the current city (" + city.getName() +
                    "). Please try again.");
        }
        boolean isIndexDuplicate = city.getNeighbours().stream().anyMatch(neighbor -> neighbor.getIndex() == index);
        if (isIndexDuplicate) {
            throw new IllegalArgumentException("Already specified the neighbor with such index. Please try again.");
        }
        List<City> cities = engine.getCities();
        if (cities.size() >= index) {
            boolean isCorrectCost = cities.get(index - 1).getNeighbours().stream().
                    anyMatch(node -> node.getIndex() == city.getIndex() && node.getCost() == cost);
            if (!isCorrectCost) {
                throw new IllegalArgumentException("Wrong index or cost. Please try again.");
            }
        }
        return new Neighbor(index, cost);
    }

    /**
     * Converts to int and check parameter for a positive integer.
     *
     * @param value         parameter to check.
     * @param parameterName parameter name to display it with exeption message.
     * @return {@param value} converted to int.
     * @throws IllegalArgumentException 1. {@param value} failed convert to integer;
     *                                  2. {@param value} is a negative integer or 0.
     */
    private int checkInteger(String value, String parameterName) {
        value = value.trim();
        try {
            int parseInt = Integer.parseInt(value);
            if (parseInt <= 0) {
                throw new NumberFormatException();
            }
            return parseInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("You must enter parameter: " + parameterName +
                    ". It should be a positive integer. Please try again.", e);
        }
    }

    /**
     * Checks {@param name} for a valid name and the absence of duplicates.
     *
     * @param name city name for checks.
     * @throws IllegalArgumentException 1. {@param name} is not valid (s should be in the range of a-z
     *                                  and no more than 10 characters);
     *                                  2. city with {@param name} already exists.
     */
    private void checkNewCityName(String name) {
        if (!name.matches("[a-z]{1,10}")) {
            throw new IllegalArgumentException("The name of the city should be in the range of a-z " +
                    "and no more than 10 characters. Please try again.");
        }
        engine.getCities().forEach(city -> {
            if (city.getName().equals(name)) {
                throw new IllegalArgumentException("City with the such name already exists. Please try again.");
            }
        });
    }

    /**
     * Check source name and destination name and collect them to {@link Neighbor}.
     *
     * @param names pair of city name (source and destination).
     * @return collected pair of city name (source and destination) to {@link Target}.
     * @throws IllegalArgumentException 1. {@param names} contains more then 2 parameters;
     *                                  2. city with one of the names does not exist.
     */
    private Target checkSourceNameAndDestinationName(String[] names) {
        if (names.length != 2) {
            throw new IllegalArgumentException("You must enter a valid name of two cities " +
                    "separated by a space. Please try again.");
        }
        for (String cityName : names) {
            boolean validName = engine.getCities().stream().anyMatch(city -> city.getName().equals(cityName));
            if (!validName) {
                throw new IllegalArgumentException("City with the name \"" + cityName + "\" does not exist. Please try again.");
            }
        }
        return new Target(names[0], names[1]);
    }

}
