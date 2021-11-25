package ua.com.alevel.thirdTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class City {

    private String name;
    private int numOfNeighbors;
    private Map<Integer, Integer> neighbors = new HashMap<>();
    private List<Integer> neighborsIds = new ArrayList<>();
    private int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNumOfNeighbors(int numOfNeighbors) {
        this.numOfNeighbors = numOfNeighbors;
    }

    public void addMap(int indexOfCity, int price) {
        neighbors.put(indexOfCity, price);
    }

    public Map<Integer, Integer> getNeighbors() {
        return neighbors;
    }

    public List<Integer> getNeighborsIds() {
        return neighborsIds;
    }

    public void setNeighborsIds(List<Integer> neighborsIds) {
        this.neighborsIds = neighborsIds;
    }
}
