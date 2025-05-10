package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Reference;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;

import java.util.ArrayList;

@Entity("games")
public class Game {
    @Id
    private ObjectId _id;
    private TimeLine time = new TimeLine();
    @Reference
    private Player loader;
    @Reference
    private Player currentPlayer;
    @Reference
    private ArrayList<Player> players;
    private WeatherType weatherType;
    private WeatherType tmrwWeatherType;

    public Game(){

    }

    public Game(ArrayList<Player> players, Player loader) {
        this.players = players;
        this.loader = loader;
        this.weatherType = WeatherType.Sunny;
        this.tmrwWeatherType = WeatherType.Sunny;


        setPlayerGames(players);
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

    private void setPlayerGames(ArrayList<Player> players) {
        for (Player player : players) {
            player.getUser().setCurrentGame(this);
        }
    }
}
