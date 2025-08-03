package com.CEliconValley.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity("games")
public class Game {
    @Id
    private ObjectId _id;
    private TimeLine time;
    @Transient
    private Player loader;
    @Transient
    private Player currentPlayer;
    @Transient
    private ArrayList<Player> players;
    @Transient
    private Village village;

    private ObjectId loaderId;
    private ObjectId currentPlayerId;
    private ArrayList<ObjectId> playersId;
    private WeatherType weatherType;
    private WeatherType tmrwWeatherType;


    private double roundEnergy;
    @Transient
    private ArrayList<Farm> farms = new ArrayList<>();
//    private Map map;

    public Village getVillage() {
        return village;
    }




    public Game() {
    }


    public Game(TimeLine time, Village village,
                WeatherType weatherType, WeatherType tmrwWeatherType,
                double roundEnergy, ArrayList<Farm> farms) {
        this.time = time;
        this.village = village;
        this.weatherType = weatherType;
        this.tmrwWeatherType = tmrwWeatherType;
        this.roundEnergy = roundEnergy;
        this.farms = new ArrayList<>(farms);
    }


    public void handmadePostLoad(Player currentPlayer, Player loader, ArrayList<Player> players) {
        this.currentPlayer = currentPlayer;
        this.loader = loader;
        this.players = new ArrayList<>(players);
    }

    public Game(ArrayList<Player> players, Player loader) {
        this.players = players;
        this.loader = loader;
        this.weatherType = WeatherType.Sunny;
        this.tmrwWeatherType = WeatherType.Sunny;
        this.time = new TimeLine();
        this.currentPlayer = loader;//todo,not true
//        this.map = new Map();

        this.roundEnergy = 0;
        this._id = new ObjectId();
        App.setGame(this);
        this.village = new Village(false);
        for (int i = 0; i < this.players.size(); i++) {
            this.farms.add(new Farm(i, players.get(i).getFarmType()));
        }
    }


    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void passTurn() {
        for (int i = 0; i < 4; i++) {
            if (players.get(i).equals(currentPlayer)) {
                currentPlayer = players.get((i + 1) % 4);
                if (i == 3) {
                    // TODO continue on with timeline
                }
                break;
            }
        }
        if(village == null) {
            this.village = new Village(false);
        }
    }

    public Farm getCurrentPlayerFarm() {
        if(currentPlayer.getInFarmId() != currentPlayer.getFarmId()) {
            for (Farm farm : farms) {
                if(farm.getId() == currentPlayer.getInFarmId()){
                    return farm;
                }
            }
        }
        for(Farm farm : farms) {
            if(farm.getId() == currentPlayer.getFarmId()){
                return farm;
            }
        }
        return null;
    }
    public void setCurrentFarmId(int id, Player player){
        System.out.println(App.getGame().getCurrentPlayer().getInFarmId()+" "+id+"check");
        App.getGame().getCurrentPlayer().setInFarmId(id);
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Player getLoader() {
        return loader;
    }

    public TimeLine getTime() {
        return time;
    }

    public void setLoader(Player loader) {
        this.loader = loader;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setTime(TimeLine time) {
        this.time = time;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public void setWeatherType(WeatherType weatherType) {
        this.weatherType = weatherType;
    }

    public WeatherType getTmrwWeatherType() {
        return tmrwWeatherType;
    }

    public void setTmrwWeatherType(WeatherType tmrwWeatherType) {
        this.tmrwWeatherType = tmrwWeatherType;
    }


//    public Map getMap() {
//        return map;
//    }
//
//    public void setMap(Map map) {
//        this.map = map;
//    }

    public ObjectId getLoaderId() {
        return loaderId;
    }

    public ObjectId getCurrentPlayerId() {
        return currentPlayerId;
    }

    public ArrayList<ObjectId> getPlayersId() {
        return playersId;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public double getRoundEnergy() {
        return roundEnergy;
    }

    public void setRoundEnergy(double roundEnergy) {
        this.roundEnergy = roundEnergy;
    }

    public void incRoundEnergy(double delta) {
        roundEnergy += delta;
    }
    public void decRoundEnergy(double delta) {
        roundEnergy -= delta;
    }


    public Player getPlayerByUsername(String username) {
        for (Player player : players) {
            if(player.getUser().getUsername().equalsIgnoreCase(username)) {
                return player;
            }
        }
        return null;
    }

}
