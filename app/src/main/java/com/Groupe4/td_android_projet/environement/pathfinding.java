package com.Groupe4.td_android_projet.environement;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

public class pathfinding {
    public static class TowerDefensePathfinding {

        public static List<Cell> findPath(Cell[][] grid, Cell start, Cell goal) {
            Set<Cell> visited = new HashSet<>();
            List<Cell> path = new ArrayList<>();
            Log.v("pathfindingSE", "Start: " + start);
            Log.v("pathfindingSE", "Goal: " + goal);
            Log.v("path2", " " + dfs(grid, start, goal, visited, path));



            if (dfs(grid, start, goal, visited, path)) {
                Log.v("pathfinding", "Path found");
                return path;
            } else {
                Log.v("pathfinding", "No path found");
                return Collections.emptyList();
            }
        }

        private static boolean dfs(Cell[][] grid, Cell current, Cell goal, Set<Cell> visited, List<Cell> path) {
            Log.v("pathfindingdfs", "Start cell coordinates: (" + current.getX() + ", " + current.getY() + ")");
            Log.v("pathfindingdfs", "Goal cell coordinates: (" + goal.getX() + ", " + goal.getY() + ")");

            Log.v("pathfindingdfs", "Visiting: " + current);
            if (current.equals(goal)) {
                Log.v("pathfindingdfs", "Goal reached"+ current);
                path.add(current);
                visited.clear();
                return true;
            }

            visited.add(current);
            path.add(current);

            for (Cell neighbor : getNeighbors(grid, current)) {
                if (!visited.contains(neighbor) && neighbor.isWalkable()) {
                    if (dfs(grid, neighbor, goal, visited, path)) {
                        return true;
                    }
                }
            }
            // Backtrack if the current path does not lead to the goal
            path.remove(path.size() - 1);
            return false;
        }

        private static List<Cell> getNeighbors(Cell[][] grid, Cell cell) {
            List<Cell> neighbors = new ArrayList<>();
            int numRows = grid.length;
            int numCols = grid[0].length;

            int x = (cell.getX());
            int y = (cell.getY());

            if (x > 0) neighbors.add(grid[x - 1][y]); // Left
            if (x < numRows - 1) neighbors.add(grid[x + 1][y]); // Right
            if (y > 0) neighbors.add(grid[x][y - 1]); // Up
            if (y < numCols - 1) neighbors.add(grid[x][y + 1]); // Down

            return neighbors;
        }
    }
}
