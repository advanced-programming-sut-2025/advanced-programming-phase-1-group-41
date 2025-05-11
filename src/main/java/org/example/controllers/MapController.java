package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.locations.Farm;

import java.util.regex.Matcher;

public class MapController {
    public Result walk(Matcher matcher){return null;}

    public Result printMap(Matcher matcher){
        Farm farm = App.getGame().getCurrentPlayerFarm();
        if(farm==null){
            System.out.println("Farm is null");
        }else{
            farm.printMap();
        }
        return new Result(true, "");
    }

    public Result SSSP(Matcher matcher){

    }

    public Result helpReadingMap(Matcher matcher){return null;}
}

