package org.example.models;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.example.controllers.AnimalController;

import java.util.Date;
import java.util.Random;

@Entity
public class TimeLine {


    @Id
    private ObjectId _id;
    private int hour;
    private int day;
    private Season season;
    private int year;

    public TimeLine() {
        this.hour = 9;
        this.day = 0;
        this.season = Season.Spring;
        this.year = 1990;
    }


    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public Season getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public void advanceOneHour(){
        hour++;
        if(hour >= 24){
            hour = 0;
            advanceOneDay();
        }
    }
    public void advanceOneDay(){
        App.getGame().getCurrentPlayerFarm().update();
//        (new AnimalController()).reset();

        App.getGame().setWeatherType(App.getGame().getTmrwWeatherType());
        predictTmrwWeather();

        day++;
        if(day >= 7){
            day = 0;
            advanceOneSeason();
        }
    }
    public void advanceOneSeason(){
        Season[] seasons = Season.values();
        season = seasons[(season.ordinal()+1) % 4];
        if(season.ordinal() == 0){
            advanceOneYear();
        }
    }
    public void advanceOneYear(){
        year++;
    }


    public void predictTmrwWeather() {
        WeatherType tmrw = null;
        Random rand = new Random();
        double chance = rand.nextDouble();

        if (season == Season.Spring) {
            if (chance < 0.6) {
                tmrw = WeatherType.Sunny;
            } else if (chance < 0.9) {
                tmrw = WeatherType.Rainy;
            } else {
                tmrw = WeatherType.Stormy;
            }
        } else if (season == Season.Summer) {
            if (chance < 0.8) {
                tmrw = WeatherType.Sunny;
            } else if (chance < 0.95) {
                tmrw = WeatherType.Rainy;
            } else {
                tmrw = WeatherType.Stormy;
            }
        } else if (season == Season.Autumn) {
            if (chance < 0.5) {
                tmrw = WeatherType.Sunny;
            } else if (chance < 0.85) {
                tmrw = WeatherType.Rainy;
            } else {
                tmrw = WeatherType.Stormy;
            }
        } else if (season == Season.Winter) {
            if (chance < 0.4) {
                tmrw = WeatherType.Sunny;
            }else {
                tmrw = WeatherType.Snowy;
            }
        }

        App.getGame().setTmrwWeatherType(tmrw);
    }



}
