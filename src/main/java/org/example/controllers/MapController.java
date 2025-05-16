package org.example.controllers;

import org.example.models.*;
import org.example.models.locations.Farm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MapController {
    public Result walk(Matcher matcher, Integer inputX , Integer inputY){
        int x;
        int y;
        if(inputX==null && inputY==null){
            String xRaw = matcher.group(1);
            String yRaw = matcher.group(2);
            x = Integer.parseInt(xRaw);
            y = Integer.parseInt(yRaw);
        }else{
            x = inputX;
            y = inputY;
        }
        List<Node> cells = SSSP(x,y);
        if (cells == null) {
            return new Result(false, "no path found!");
        }else{
            Player player = App.getGame().getCurrentPlayer();
            double energy = 0;
            for (Node cell : cells) {
//                System.out.println(cell);
                energy = cell.energyCost;
//                System.out.println("energy: "+energy);
                if(energy > player.getEnergy() && !player.isEnergyUnlimited()){
                    player.decEnergy(player.getEnergy());
                    printMap(null);
                    return new Result(false, "you're running low :(");
                }
                player.setX(cell.x);
                player.setY(cell.y);
                if(!player.isPlayerIsInVillage()){
                    if(App.getGame().getCurrentPlayerFarm().getTransferCells().contains(
                            Finder.findCellByCoordinates(cell.x, cell.y, App.getGame().getCurrentPlayerFarm())
                    )){
                        player.setPlayerIsInVillage(true);
                        player.setX(App.getGame().getVillage().getStartPoints().getFirst().getX());
                        player.setY(App.getGame().getVillage().getStartPoints().getFirst().getY());
                        System.out.println("going to village..");
                        System.out.println("rn on "+player.getX()+" "+player.getY());
                        break;
                    }
                }else{
                    if(App.getGame().getVillage().getTransferCells().contains(
                            Finder.findCellByCoordinatesVillage(cell.x, cell.y, App.getGame().getVillage())
                    )){
                        player.setPlayerIsInVillage(false);
                        player.setX(App.getGame().getCurrentPlayerFarm().getStartPoints().getFirst().getX());
                        player.setY(App.getGame().getCurrentPlayerFarm().getStartPoints().getFirst().getY());
                        System.out.println("going to farm ...");
                        System.out.println("rn on "+player.getX()+" "+player.getY());
                        break;
                    }
                }
//                Cell c = Finder.findCellByCoordinates(cell.x,cell.y, App.getGame().getCurrentPlayerFarm());
//                System.out.println(c.getObjectMap().getClass());
            }
            if(!player.isEnergyUnlimited()){
                player.decEnergy(energy);
            }
            return new Result(true,"found the path ;D" +
                    "\n" +
                    "energy: "+player.getEnergy());
        }


    }

    public void printMap(Matcher matcher){
        Farm farm = App.getGame().getCurrentPlayerFarm();
        if(farm==null){
            System.out.println("Farm is null");
        }else if(App.getGame().getCurrentPlayer().isPlayerIsInVillage()) {
            App.getGame().getVillage().printMap();
        }else{
            farm.printMap();
        }
    }

    public void printMapReal(Matcher matcher){
        int x = Integer.parseInt(matcher.group(1));
        int y = Integer.parseInt(matcher.group(2));
        int size = Integer.parseInt(matcher.group(3));
        Farm farm = App.getGame().getCurrentPlayerFarm();
        if(farm==null){
            System.out.println("Farm is null");
        }
        else{
            farm.printMap(x,y,size);
        }
    }

    public List<Node> SSSP(int x , int y){
        return (new PathFinder(App.getGame().getCurrentPlayerFarm().getCells()))
                .findPath(App.getGame().getCurrentPlayer(), x , y);
    }

    public void helpReadingMap(Matcher matcher){
        Farm farm = App.getGame().getCurrentPlayerFarm();
        ArrayList<Cell> cells = farm.getCells();
        ArrayList<String> objectNames = new ArrayList<>();
        for(Cell cell : cells){
            if(!objectNames.contains(cell.getObjectMap().getName())){
                objectNames.add(cell.getObjectMap().getName());
                System.out.printf(
                        "%-20s -> %7s\n\n",
                        cell.getObjectMap().getName(),
                        cell.getObjectMap().getChar()
                );
            }
        }
        objectNames.clear();
    }
}

