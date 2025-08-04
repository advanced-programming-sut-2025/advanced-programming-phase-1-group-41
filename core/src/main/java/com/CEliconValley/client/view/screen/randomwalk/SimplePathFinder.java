package com.CEliconValley.client.view.screen.randomwalk;


import com.CEliconValley.client.view.screen.maps.CoopMap;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.client.view.screen.maps.BarnMap;

import java.util.*;

public class SimplePathFinder {
    BarnMap barn;
    CoopMap coop;
    public SimplePathFinder(BarnMap barn) {
        this.barn = barn;
    }
    public SimplePathFinder(CoopMap coopMap) {
        this.coop = coopMap;
        this.barn =null;
    }

    public Node findPath(int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();
        openSet.add(new Node(startX, startY, 0, 0, null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String key = current.x + "," + current.y;
            if (visited.contains(key)) continue;
            visited.add(key);

            if (current.x == goalX && current.y == goalY) {
                return getFirstNode(current);
            }

            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                Cell nextCell = null;
                if(canMoveTo(newX, newY)) {
                    nextCell = getCell(newX, newY);
                }
                if (nextCell == null) continue;

                int newTurns = (current.parent != null && directionChanged(current.parent.x, current.parent.y, current.x, current.y, newX, newY))
                    ? current.turns + 1 : current.turns;
                double newEnergyCost = current.energyCost + 0.1;
                if (current.parent != null && directionChanged(current.parent.x, current.parent.y, current.x, current.y, newX, newY)) {
                    newEnergyCost += 0.5;
                }


                openSet.add(new Node(newX, newY, newEnergyCost, newTurns, current));
            }
        }
        return null;
    }

    private boolean directionChanged(int prevX, int prevY, int currX, int currY, int newX, int newY) {
        return (prevX != newX && prevY != newY);
    }

    private boolean canMoveTo(int x, int y) {
        if(barn == null){
            for (Cell cell : coop.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }else{
            for (Cell cell : barn.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
    }
    private Cell getCell(int x, int y) {
        if(barn == null){
            for (Cell cell : coop.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return cell;
                }
            }
            return null;
        }
        for (Cell cell : barn.getCells()) {
            if (cell.getX() == x && cell.getY() == y) {
                return cell;
            }
        }
        return null;
    }
    private Node getFirstNode(Node node) {
        List<Node> path = new ArrayList<>();
        while (node != null) {
            path.add(node);
            node = node.parent;
        }
        Collections.reverse(path);
//        for (Node node1 : path) {
//            System.out.println(node1);
//        }
        return path.size() > 1 ? path.get(1) : path.get(0);
    }
}
