package com.CEliconValley.controllers;

import com.CEliconValley.models.*;

import com.CEliconValley.models.foragings.Crop;
import com.CEliconValley.models.foragings.ForagingCrop;
import com.CEliconValley.models.foragings.ForagingTree;
import com.CEliconValley.models.foragings.Nature.Bush;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.foragings.Nature.Plant;
import com.CEliconValley.models.foragings.Nature.Tree;
import com.CEliconValley.models.locations.Farm;

import java.util.Random;
import java.util.regex.Matcher;

public class WeatherController {
    public static void strikeThunder(Farm farm){
        Random rand = new Random();
        System.out.println("Farm " + farm.getId() + " Thunderstorm last night:");
        for(int i = 0; i < 3; i++){
            int x = rand.nextInt(App.MaxLength);
            int y = rand.nextInt(App.MaxHeight);
            Cell cell = Finder.findCellByCoordinates(x, y, farm);
            if(cell == null){
                i--;
                continue;
            }
            if(cell.getObjectMap() == null){
                i--;
                continue;
            }
            if(cell.getObjectMap() instanceof Grass || cell.getObjectMap() instanceof Crop || cell.getObjectMap() instanceof ForagingCrop
                    || cell.getObjectMap() instanceof Plant || cell.getObjectMap() instanceof Bush){
                if(cell.getObjectMap() instanceof Crop crop){
                    if(farm.getGreenhouse().isGreenHouse(crop.getX(), crop.getY())){
                        continue;
                    }
                }
                Grass grass = new Grass();
                grass.setThundered(true);
                cell.setObjectMap(grass);
                System.out.println("A " + cell.getObjectMap().getName() + " was struck by lightning at " + x + ", " + y);
            } else if(cell.getObjectMap() instanceof Tree || cell.getObjectMap() instanceof ForagingTree){
                if(cell.getObjectMap() instanceof Tree tree){
                    if(farm.getGreenhouse().isGreenHouse(tree.getX(), tree.getY())){
                        continue;
                    }
                    tree.thunder();
                } else {
                    ((ForagingTree) cell.getObjectMap()).thunder();
                }
                System.out.println("A " + cell.getObjectMap().getName() + " was struck by lightning at " + x + ", " + y);
            } else {
                i--;
            }
        }
    }

    public Result predictTmrwWeather(Matcher matcher){
        return new Result(true,"tomorrow weather is: " + App.getGame().getTmrwWeatherType());
    }

    public Result getCurrentWeather(Matcher matcher){
        return new Result(true,"current weather is: " + App.getGame().getWeatherType());
    }

    public void applyEfficiency(){}

    public Result cheatStrikeThunder(Matcher matcher){
        int x = Integer.parseInt(matcher.group("x"));
        int y = Integer.parseInt(matcher.group("y"));
        Farm farm = App.getGame().getCurrentPlayerFarm();
        Cell cell = Finder.findCellByCoordinates(x, y, farm);
        assert cell != null;
        if(cell.getObjectMap() instanceof Grass || cell.getObjectMap() instanceof Crop || cell.getObjectMap() instanceof ForagingCrop
                || cell.getObjectMap() instanceof Plant || cell.getObjectMap() instanceof Bush){
            Grass grass = new Grass();
            grass.setThundered(true);
            cell.setObjectMap(grass);
        } else if(cell.getObjectMap() instanceof Tree || cell.getObjectMap() instanceof ForagingTree){
            if(cell.getObjectMap() instanceof Tree){
                ((Tree) cell.getObjectMap()).thunder();
            } else {
                ((ForagingTree) cell.getObjectMap()).thunder();
            }
        } else {
            return new Result(true,"Cell '" + cell.getObjectMap().getName() +"' is not available for thundering!");
        }
        return new Result(true,"Thundered " + cell.getObjectMap().getName() + " at " + x + ", " + y + " successfully!.");
    }

    public Result cheatChangeTmrwWeather(Matcher matcher){
        String raw = matcher.group(1).trim();
        if(raw.isEmpty()){
            return new Result(false,"empty command");

        }
        try {
            WeatherType weatherType = WeatherType.valueOf(raw);
        } catch (IllegalArgumentException e) {
            return new Result(false, "Invalid weather type provided");
        }

        WeatherType weatherType = WeatherType.valueOf(raw);

        App.getGame().setTmrwWeatherType(weatherType);
        return new Result(true,"tomorrow weather is: "+weatherType);
    }



}
