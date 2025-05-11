package org.example.controllers;

import org.example.models.Cell;
import org.example.models.Player;

import java.util.*;

public class PathFinder {
    private final ArrayList<Cell> cells;

    public PathFinder(ArrayList<Cell> cells) {
        this.cells = cells;
    }

    public List<Node> findPath(Player player, int goalX, int goalY) {
        int startX = player.getX();
        int startY = player.getY();
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();
        openSet.add(new Node(startX, startY, 0, 0, null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String key = current.x + "," + current.y;
            if (visited.contains(key)) continue;
            visited.add(key);

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];

                Cell nextCell = getCell(newX, newY);
                if (nextCell == null || nextCell.getObjectMap() != null) continue;

                int newTurns = (current.parent != null && directionChanged(current.parent.x, current.parent.y, current.x, current.y, newX, newY))
                        ? current.turns + 1 : current.turns;
                double newEnergyCost = (1 + 10 * newTurns) / 20.0 + current.energyCost;


                // check energy
                if (newEnergyCost > player.getEnergy()) continue;

                openSet.add(new Node(newX, newY, newEnergyCost, newTurns, current));
            }
        }
        return null;
    }

    private Cell getCell(int x, int y) {
        for (Cell cell : cells) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }

    private boolean directionChanged(int prevX, int prevY, int currX, int currY, int newX, int newY) {
        return (prevX != newX && prevY != newY);
    }

    private List<Node> reconstructPath(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
        return path;
    }
}

class Node implements Comparable<Node> {
    int x, y;
    double energyCost;
    int turns;
    Node parent;

    public Node(int x, int y, double energyCost, int turns, Node parent) {
        this.x = x;
        this.y = y;
        this.energyCost = energyCost;
        this.turns = turns;
        this.parent = parent;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.energyCost, other.energyCost);
    }
}
