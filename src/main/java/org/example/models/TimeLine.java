package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.example.controllers.subgames.*;
import org.example.controllers.MapController;
import org.example.controllers.subgames.AnimalController;
import org.example.controllers.subgames.FriendshipController;
import org.example.controllers.subgames.MarketplaceController;
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


    public final int firstHour = 9;
    public final int firstDay = 0;
    public final Season firstSeason = Season.Spring;
    public final int firstYear = 1990;

    public TimeLine() {
        this.hour = firstHour;
        this.day = firstDay;
        this.season = firstSeason;
        this.year = firstYear;
    }

    public int howManyDaysPassed() {
        int sum=0;
        sum+=(getYear()-firstYear)*4*28;
        sum+=(getSeason().ordinal()-firstSeason.ordinal())*28;
        sum+=(getDay()-firstDay);
        return sum;
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
        MarketplaceController.updateHourly();
        for(Player player : App.getGame().getPlayers()){
            player.updateHourly();
        }
    }
    public void advanceOneDay(){
        if(hour == 0){
            goHome();
            for (Player player : App.getGame().getPlayers()) {
                player.resetEnergy();
            }
        }

        for (Building building : App.getGame().getVillage().getBuildings()) {
            if(building instanceof Marketplace marketplace){
                marketplace.updateStock();
            }
        }
        System.out.println("updated marketplace stocks..");

        (new AnimalController()).resetAndCheck();
        (new NPCController()).resetAndCheck();
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

        if(hour == 0){

            for (int i = 0; i < 9; i++) {
                advanceOneHour();
            }
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

    public void goHome(){
        for (Player player : App.getGame().getPlayers()) {
            Farm farm = Finder.findFarmByPlayer(player);
            MapController controller = new MapController();
            Cell villageCell = App.getGame().getVillage().getTransferCells().getFirst();
            Cell playerCell = farm.getStartPoints().getFirst();
            if(player.isPlayerIsInVillage()){
                Result preResult = controller.walk(null,villageCell.getX(),villageCell.getY());
                if(!preResult.success()){
                    System.out.println("you're stuck "+preResult);
                }
            }
            System.out.println(controller.walk(null, playerCell.getX(),playerCell.getY()));
        }
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
