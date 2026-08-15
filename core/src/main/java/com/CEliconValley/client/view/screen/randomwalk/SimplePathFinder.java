package com.CEliconValley.client.view.screen.randomwalk;


import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.client.view.screen.maps.CoopMap;
import com.CEliconValley.client.view.screen.maps.FarmMap;
import com.CEliconValley.client.view.screen.maps.VillageMap;
import com.CEliconValley.common.CellData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.buildings.Wall;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Lake;
import com.CEliconValley.models.foragings.Nature.Obstacle;
import com.CEliconValley.models.foragings.Nature.Rock;
import com.CEliconValley.client.view.screen.maps.BarnMap;
import com.CEliconValley.models.locations.Village;

import java.util.*;

import static com.badlogic.gdx.scenes.scene2d.ui.Table.Debug.cell;

public class SimplePathFinder {
    BarnMap barn = null;
    CoopMap coop = null;
    FarmMap farm = null;
    VillageMap villageMap = null;
    Village village = null;
    public SimplePathFinder(BarnMap barn) {
        this.barn = barn;
    }
    public SimplePathFinder(CoopMap coopMap) {
        this.coop = coopMap;
    }

    public SimplePathFinder(FarmMap farm) {
        this.farm = farm;
    }
    public SimplePathFinder(VillageMap villageMap) {
        this.villageMap = villageMap;
    }

    public SimplePathFinder(Village village) {
        this.village = village;
    }

    public Queue<Node> getPathQueue(int startX, int startY, int goalX, int goalY) {
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();
        openSet.add(new Node(startX, startY, 0, 0, null));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            String key = current.x + "," + current.y;
            if (visited.contains(key)) continue;
            visited.add(key);

            if (current.x == goalX && current.y == goalY) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                path.remove(0); // Remove current position
                return new LinkedList<>(path); // Convert to queue
            }

            for (int[] dir : new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}) {
                int newX = current.x + dir[0];
                int newY = current.y + dir[1];
                Cell nextCell = canMoveTo(newX, newY) ? getCell(newX, newY) : null;
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
        return new LinkedList<>(); // Empty path if unreachable
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
        if(coop != null){
            for (Cell cell : coop.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }else if(barn != null){
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
        else if (farm != null){
            for (CellData cd : farm.farmData.getCells()) {
                Cell cell = cd.extractData();
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake || cell.getObjectMap() instanceof Rock || cell.getObjectMap() instanceof Wall) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }else if(villageMap != null){
            for (CellData cd : villageMap.villageData.getCellsData()) {
                if (cd.getX() == x && cd.getY() == y) {
                    Cell cell = cd.extractData();
                    if (cell.getObjectMap() instanceof Lake ||( cell.getObjectMap() instanceof Grass grass && !grass.isGround() )
                        ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        System.out.println(cd.getObjectName()+" "+cell.getX()+" "+cell.getY());
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }else if(village != null){
            for (Cell cell : village.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    if (cell.getObjectMap() instanceof Lake ||( cell.getObjectMap() instanceof Grass grass && !grass.isGround() )
                        ||cell.getObjectMap() instanceof Wall ||cell.getObjectMap() instanceof Obstacle) {
                        return false;
                    }
                    return true;
                }
            }
            return false;
        }
        return false;
    }
    private Cell getCell(int x, int y) {
        if(coop != null){
            for (Cell cell : coop.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return cell;
                }
            }
            return null;
        }else if(barn != null){
            for (Cell cell : barn.getCells()) {
                if (cell.getX() == x && cell.getY() == y) {
                    return cell;
                }
            }
        }else if(farm != null){
            for (CellData cell : farm.farmData.getCells()) {
                if(cell.getX() == x && cell.getY() == y){
                    return cell.extractData();
                }
            }
        }else if(villageMap != null){
            for (CellData cell : villageMap.villageData.getCellsData()) {
                if(cell.getX() == x && cell.getY() == y){
                    return cell.extractData();
                }
            }
        }else if(village != null){
            for (Cell cell : village.getCells()) {
                if(cell.getX() == x && cell.getY() == y){
                    return cell;
                }
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
