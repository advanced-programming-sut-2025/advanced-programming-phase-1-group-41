package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.locations.Farm;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

public class MapController {
    public Result walk(Matcher matcher){
        String xRaw = matcher.group(1);
        String yRaw = matcher.group(2);
        int x = Integer.parseInt(xRaw);
        int y = Integer.parseInt(yRaw);
        List<Node> cells = SSSP(x,y);
        if (cells == null) {
            return new Result(false, "no path found!");
        }else{
            Player player = App.getGame().getCurrentPlayer();
            double energy = 0;
            for (Node cell : cells) {
                energy = cell.energyCost;
                if(energy > player.getEnergy() && !player.isEnergyUnilimited()){
                    player.decEnergy(player.getEnergy());
                    printMap(null);
                    return new Result(false, "you're running low :(");
                }
                player.setX(cell.x);
                player.setY(cell.y);
//                Cell c = Finder.findCellByCoordinates(cell.x,cell.y, App.getGame().getCurrentPlayerFarm());
//                System.out.println(c.getObjectMap().getClass());
            }
            if(!player.isEnergyUnilimited()){
                player.decEnergy(energy);
            }
            return new Result(true,"found the path ;D" +
                    "\n" +
                    "energy: "+player.getEnergy());
        }


    }

    public Result printMap(Matcher matcher){
        Farm farm = App.getGame().getCurrentPlayerFarm();
        if(farm==null){
            System.out.println("Farm is null");
        }else{
            farm.printMap();
        }
        return new Result(true, "");
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
                System.out.println(cell.getObjectMap().getName()+": "+cell.getObjectMap().getChar());
            }
        }
        objectNames.clear();
    }
}

