package org.example.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.ArrayList;

@Entity
public class Game {
    @Id
    private ObjectId _id;
    private TimeLine time = new TimeLine();
    private Player loader;
    private Player currentPlayer;
    private ArrayList<Player> players;
//    private WeatherType weatherType;
    public TimeLine getTime() {
        return time;
    }

    public Game(){

    }

    public Game(ArrayList<Player> players, Player loader) {
        this.players = players;
        this.loader = loader;
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
}
