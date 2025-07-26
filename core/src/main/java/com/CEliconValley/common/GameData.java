package com.CEliconValley.common;

import com.CEliconValley.models.Game;
import com.CEliconValley.models.Player;
import com.CEliconValley.models.TimeLine;
import com.CEliconValley.models.WeatherType;
import com.CEliconValley.models.locations.Farm;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;

@Entity("gamedata")
public class GameData {
    @Id
    private ObjectId _id;
    TimeLine time;
    ArrayList<FarmData> farmsData;
    String loaderName;
    String currentPlayerName;
    ArrayList<PlayerData> playersData;
    WeatherType weatherType;
    WeatherType tmrwWeatherType;
    double roundEnergy;
    VillageData villageData;


    public GameData(Game game) {
        this.time = game.getTime();
        this.weatherType = game.getWeatherType();
        this.tmrwWeatherType = game.getTmrwWeatherType();
        this.farmsData = new ArrayList<>();
        this.playersData = new ArrayList<>();
        this.roundEnergy = game.getRoundEnergy();
        this.loaderName = game.getLoader().getUser().getUsername();
        this.currentPlayerName = game.getCurrentPlayer().getUser().getUsername();
        fillPlayers(game);
        fillFarms(game);
    }

    private void fillFarms(Game game) {
        for (Farm farm : game.getFarms()) {

        }
    }

    private void fillPlayers(Game game){
        for (Player player : game.getPlayers()) {
            this.playersData.add(new PlayerData(player));
        }
    }

    public GameData cloneGame(){
        return null;
    }

    public Game makeGame(){
        return null;
    }
}
