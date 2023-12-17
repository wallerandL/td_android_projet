package com.Groupe4.td_android_projet.environement;

public class Cell {
    private int x, y;
    private double cost;
    private double heuristicCost;
    private Cell previous;
    private boolean walkable;

    public Cell(int x, int y, boolean walkable) {
        this.x = x;
        this.y = y;
        this.walkable = walkable;

    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isWalkable() {
        return walkable;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getHeuristicCost() {
        return heuristicCost;
    }

    public void setHeuristicCost(double heuristicCost) {
        this.heuristicCost = heuristicCost;
    }

    public Cell getPrevious() {
        return previous;
    }

    public void setPrevious(Cell previous) {
        this.previous = previous;
    }

    public double distanceTo(Cell other) {
        // Euclidean distance, you may want to use a different distance metric
        return Math.sqrt(Math.pow(x - other.x, 2) + Math.pow(y - other.y, 2));
    }

    public double getTotalCost() {
        return cost + heuristicCost;
    }

    public String toString() {
        return "Cell{x=" + x + ", y=" + y + "}";
    }
}
