package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.Bridge;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.Door;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.buildings.marketplaces.Marketplace;
import org.example.models.foragings.Nature.Grass;
import org.example.models.foragings.Nature.Obstacle;

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
                Cell nextCell = null;
                if(!App.getGame().getCurrentPlayer().isPlayerIsInVillage()){
                    nextCell = getCell(newX, newY);
                }else{
                    nextCell = Finder.findCellByCoordinatesVillage(newX, newY, App.getGame().getVillage());
                }
                // unavailable
                if (nextCell == null ||
                   nextCell.getObjectMap() instanceof Obstacle) continue;

                if(!player.isPlayerIsInVillage()){
                    if(!farm(nextCell)){
                        continue;
                    }
                }else{
                    if(!village(nextCell)){
                        continue;
                    }
                }

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

    private boolean farm(Cell nextCell){
        if(nextCell.getObjectMap() instanceof Building){
            if(!(nextCell.getObjectMap() instanceof Cottage) &&
                    !(nextCell.getObjectMap() instanceof Door) &&
                    !(nextCell.getObjectMap() instanceof Greenhouse)){
//                        System.out.println(nextCell.getObjectMap().getClass());
                return false;
            }
        }
        if(nextCell.getObjectMap() instanceof Door door){
            if(door.isClosed()){
//                System.out.println("door is closed :(");
                return false;
            }
        }
        return true;
    }

    private boolean village(Cell nextCell){
        if(nextCell.getObjectMap() instanceof Building){
            if(!(nextCell.getObjectMap() instanceof Door) &&
            !(nextCell.getObjectMap() instanceof Marketplace) &&
            !(nextCell.getObjectMap() instanceof Bridge)){
//                System.out.println(nextCell.getObjectMap().getClass());
                return false;
            }
        }else if(nextCell.getObjectMap() instanceof Grass){
            if(((Grass) nextCell.getObjectMap()).isGround()
                    || ((Grass) nextCell.getObjectMap()).isSand()
                    || ((Grass) nextCell.getObjectMap()).isFarmland()){
                return true;
            }
            return false;
        }
        if(nextCell.getObjectMap() instanceof Door door){
            if(door.isClosed()){
//                System.out.println("door is closed :(");
                return false;
            }
        }
        return true;
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
//        for (Node node1 : path) {
//            System.out.println(node1);
//        }
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

    @Override
    public String toString() {
        return "Node{" +
                "x=" + x +
                ", y=" + y +
                ", energyCost=" + energyCost +
                ", turns=" + turns +
                '}';
    }
}
