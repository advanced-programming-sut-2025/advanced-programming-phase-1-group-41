package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import org.example.models.locations.Farm;
import org.example.models.locations.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity("games")
public class Game {
    private ArrayList<Farm> farms = new ArrayList<>();
    @Id
    private ObjectId _id;
    private TimeLine time;
    @Transient
    private Player loader;
    @Transient
    private Player currentPlayer;
    @Transient
    private ArrayList<Player> players;

    private ObjectId loaderId;
    private ObjectId currentPlayerId;
    private ArrayList<ObjectId> playersId;

    private WeatherType weatherType;
    private WeatherType tmrwWeatherType;

//    private Map map;

    public Game(ArrayList<Player> players, Player loader) {
        this.players = players;
        this.loader = loader;
        this.weatherType = WeatherType.Sunny;
        this.tmrwWeatherType = WeatherType.Sunny;
        this.time = new TimeLine();
//        this.map = new Map();
        this.farms.add(new Farm(1));
        this.farms.add(new Farm(2));
        this.farms.add(new Farm(3));
        this.farms.add(new Farm(4));
        this._id = new ObjectId();
    }

    public void setPlayers(List<Player> players) {
        this.players = new ArrayList<>(players); // Ensure it's explicitly an ArrayList
        this.playersId = players.stream()
                .map(Player::get_id)
                .collect(Collectors.toCollection(ArrayList::new)); // Convert IDs safely to ArrayList
    }

    public void prepareForSaving() {
        if (loader != null) loaderId = loader.get_id();
        if (currentPlayer != null) currentPlayerId = currentPlayer.get_id();

        playersId = players.stream()
                .map(Player::get_id)
                .collect(Collectors.toCollection(ArrayList::new));
    }



    public Player getCurrentPlayer() {
        return currentPlayer;
    }

//    public WeatherType getWeatherType() {
//        return weatherType;
//    }

    public void passTurn() {
        for(int i=0;i<4;i++){
            if(players.get(i).equals(currentPlayer)){
                currentPlayer = players.get((i+1) % 4);
                if(i==3){
                    // TODO continue on with timeline
                }
                break;
            }
        }
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
}
