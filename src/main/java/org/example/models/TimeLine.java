package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.example.controllers.subgames.AnimalController;
import org.example.controllers.subgames.FriendshipController;
import org.example.models.buildings.Building;
import org.example.models.buildings.marketplaces.*;
import org.example.models.locations.Farm;
import org.example.controllers.subgames.CraftingController;

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
        CraftingController.check();
        hour++;
        if(hour >= 24){
            hour = 0;
            advanceOneDay();
        }
    }
    public void advanceOneDay(){
        for (Building building : App.getGame().getVillage().getBuildings()) {
            if(building instanceof Blacksmith blacksmith){
                System.out.println("blacksmith found ..");
                blacksmith.updateStock();
            }else if(building instanceof MarnieRanch marnieRanch){
                System.out.println("marnieRanch found ..");
                marnieRanch.updateStock();
            }else if(building instanceof Saloon saloon){
                System.out.println("saloon found ..");
                saloon.updateStock();
            }else if(building instanceof CarpenterShop carpenterShop){
                System.out.println("carpenterShop found ..");
                carpenterShop.updateStock();
            }else if(building instanceof Jojamart jojamart){
                System.out.println("jojamart found ..");
                jojamart.updateStock();
            }else if(building instanceof FishShop fishShop){
                System.out.println("fishShop found ..");
                fishShop.updateStock();
            }else if(building instanceof GeneralStore generalStore){
                System.out.println("generalStore found ..");
                generalStore.updateStock();
            }
        }
        (new AnimalController()).resetAndCheck();
        for (Player player : App.getGame().getPlayers()) {
            player.dailyUpdates();
        }

        App.getGame().setWeatherType(App.getGame().getTmrwWeatherType());
        predictTmrwWeather();
        for(Farm farm : App.getGame().getFarms()){
            farm.update();
        }
        FriendshipController.dailyUpdate();

        day++;
        if(day >= 28){
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
