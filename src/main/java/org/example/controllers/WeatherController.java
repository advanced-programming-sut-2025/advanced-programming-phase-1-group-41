package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.WeatherType;

import java.util.regex.Matcher;

public class WeatherController {
    public Result strikeThunder(Matcher matcher){return null;}

    public Result predictTmrwWeather(Matcher matcher){
        return new Result(true,"tomorrow weather is: "+App.getGame().getTmrwWeatherType());
    }

    public Result getCurrentWeather(Matcher matcher){
        return new Result(true,"current weather is: "+ App.getGame().getWeatherType());
    }

    public void applyEfficiency(){}

    public Result cheatStrikeThunder(Matcher matcher){return null;}

    public Result cheatChangeTmrwWeather(Matcher matcher){
        String raw = matcher.group(1).trim();
        WeatherType weatherType = WeatherType.valueOf(raw);
        if(weatherType == null){
            return new Result(false,"weather type is null");
        }

        App.getGame().setTmrwWeatherType(weatherType);
        return new Result(true,"tomorrow weather is: "+weatherType);
    }



}
