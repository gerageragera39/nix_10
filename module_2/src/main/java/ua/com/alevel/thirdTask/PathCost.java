package ua.com.alevel.thirdTask;

import java.io.*;
import java.util.*;

public class PathCost {

    public static final String pathInput = ".\\src\\main\\resources\\thirdTaskFiles\\third_task_input.txt";

    private List<List<Integer>> passed = new ArrayList<>();

    public void run() {
        BufferedReader bufferedReader = null;
        List<City> cities = new ArrayList<>();
        try {
            bufferedReader = new BufferedReader(new FileReader(pathInput));
            int numOfCities = 0;
            if (bufferedReader.ready()) {
                numOfCities = Integer.parseInt(bufferedReader.readLine());
                if(numOfCities>1000){
                    System.out.println("Too big number of cities");
                    System.exit(0);
                }
            } else {
                System.out.println("Wrong input");
                System.exit(0);
            }
            for (int k = 1; k <= numOfCities; k++) {
                City city = new City();
                city.setId(k);
                city.setName(bufferedReader.readLine());
                int numOfNeighbors = Integer.parseInt(bufferedReader.readLine());
                city.setNumOfNeighbors(numOfNeighbors);
                for (int i = 0; i < numOfNeighbors; i++) {
                    getMap(bufferedReader, city);
                }
                city.setNeighborsIds(getList(city));
                cities.add(city);
            }
            String[] destination = new String[2];
            int indexOfStartEnd = 0, indexOfNameCityEnd = 0;
            if (bufferedReader.ready()) {
                String str = bufferedReader.readLine();
                for (int i = 0; i < str.length(); i++) {
                    if (Character.isLetter(str.charAt(i))) {
                        destination[indexOfStartEnd] = String.valueOf(str.charAt(i));
                        for (int j = i + 1; j < str.length(); j++) {
                            if (Character.isLetter(str.charAt(j))) {
                                destination[indexOfStartEnd] += str.charAt(j);
                                indexOfNameCityEnd++;
                            } else {
                                break;
                            }
                        }
                        indexOfStartEnd++;
                        i += indexOfNameCityEnd;
                    }
                    indexOfNameCityEnd = 0;
                }
            }

            List<Integer> passedIds = new ArrayList<>();
            for (int i = 0; i < cities.size(); i++) {
                if (cities.get(i).getName().equals(destination[0])) {
                    findingPath(cities, cities.get(i), passedIds, destination[1]);
                    break;
                }
            }

            Map<List<Integer>, Integer> mapPathPrice = new HashMap<>();
            for (int i = 0; i < passed.size(); i++) {
                mapPathPrice.put(new ArrayList<>(passed.get(i)), getPrice(cities, passed.get(i)));
            }
            List<Integer> values = new ArrayList<>(mapPathPrice.values());
            for (int i = 0; i < values.size(); i++) {
                for (int j = 0; j < values.size(); j++) {
                    if (values.get(i) > values.get(j)) {
                        values.remove(i);
                        i--;
                        break;
                    }
                }
            }

            if (values.get(0) <= 200_000){
                for (int i = 0; i < mapPathPrice.size(); i++) {
                    if (mapPathPrice.get(passed.get(i)) == values.get(0)) {
                        System.out.print("The cheapest way : ");
                        for (int j = 0; j < passed.get(i).size(); j++) {
                            System.out.print(findById(cities, passed.get(i).get(j)).getName());
                            if (j != passed.get(i).size() - 1) {
                                System.out.print(" -> ");
                            }
                        }
                        System.out.println("; value = " + values.get(0));
                    }
                }
            }else{
                System.out.println("All paths are very expensive, cost more than 200.000");
            }
        } catch (IOException e) {
            System.out.println("Wrong input");
        }
    }

    public List<Integer> getList(City city) {
        return city.getNeighbors().keySet().stream().toList();
    }

    public List<Integer> findingPath(List<City> cities, City city, List<Integer> passedIds, String finish) {
        List<Integer> neighbors = new ArrayList<>(city.getNeighborsIds());
        for (int j = 0; j < neighbors.size(); j++) {
            for (int k = 0; k < passedIds.size(); k++) {
                if (neighbors.get(j) == passedIds.get(k)) {
                    neighbors.remove(j);
                    j--;
                    break;
                }
            }
        }
        passedIds.add(city.getId());
        if (city.getName().equals(finish)) {
            passed.add(new ArrayList<>(passedIds));
            return null;
        }
        for (int i = 0; i < neighbors.size(); i++) {
            findingPath(cities, findById(cities, neighbors.get(i)), passedIds, finish);
            passedIds.remove(passedIds.size() - 1);
        }
        return new ArrayList<>();
    }

    public City findById(List<City> cities, int id) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getId() == id) {
                return cities.get(i);
            }
        }
        return new City();
    }

    public void getMap(BufferedReader bufferedReader, City city) {
        try {
            int indexOfNumberEnd = 0;
            String[] mapField = new String[2];
            int index = 0;
            String str = bufferedReader.readLine();
            for (int i = 0; i < str.length(); i++) {
                if (Character.isDigit(str.charAt(i))) {
                    mapField[index] = String.valueOf(str.charAt(i));
                    for (int j = i + 1; j < str.length(); j++) {
                        if (Character.isDigit(str.charAt(j))) {
                            mapField[index] += str.charAt(j);
                            indexOfNumberEnd++;
                        } else {
                            break;
                        }
                    }
                    index++;
                    i += indexOfNumberEnd;
                }
                indexOfNumberEnd = 0;
            }
            city.addMap(Integer.parseInt(mapField[0]), Integer.parseInt(mapField[1]));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPrice(List<City> cities, List<Integer> path) {
        int priceOfPath = 0;
        for (int i = 0; i < path.size(); i++) {
            City city = findById(cities, path.get(i));
            if (i != path.size() - 1) {
                priceOfPath += city.getNeighbors().get(path.get(i + 1));
            }
        }
        return priceOfPath;
    }
}
