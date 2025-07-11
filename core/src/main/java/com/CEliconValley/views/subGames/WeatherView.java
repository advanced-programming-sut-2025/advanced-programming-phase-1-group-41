package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.WeatherController;
import com.CEliconValley.views.commands.gameCommands.WeatherAndTimeCommands;

import java.util.regex.Matcher;

public class WeatherView {
    static WeatherController controller =  new WeatherController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = WeatherAndTimeCommands.Weather.getMatcher(input))!=null){
            System.out.println(controller.getCurrentWeather(matcher));
        }else if((matcher = WeatherAndTimeCommands.WeatherForecast.getMatcher(input))!=null){
            System.out.println(controller.predictTmrwWeather(matcher));
        }else if((matcher = WeatherAndTimeCommands.CheatWeather.getMatcher(input))!=null){
            System.out.println(controller.cheatChangeTmrwWeather(matcher));
        }else if((matcher = WeatherAndTimeCommands.CheatThor.getMatcher(input))!=null){
            System.out.println(controller.cheatStrikeThunder(matcher));
        } else{
            return false;
        }
        return true;
    }
}
