package org.example.controllers;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Result;
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
            double energy = 0;
            for (Node cell : cells) {
                energy = cell.energyCost;

            }
            return new Result(true,"found the path ;D" +
                    "\n" +
                    "energy: "+App.getGame().getCurrentPlayer().getEnergy());
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

    public Result helpReadingMap(Matcher matcher){return null;}
}

