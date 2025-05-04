package org.example.models;

import java.util.ArrayList;

public class Game {
    private static TimeLine time = TimeLine.getInstance();

    private static Player currentPlayer;

    private static ArrayList<Player> players;

    private static WeatherType weatherType;

    public static TimeLine getTime() {
        return time;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static WeatherType getWeatherType() {
        return weatherType;
    }

    public static void passTurn() {
        for(int i=0;i<4;i++){
            if(players.get(i).equals(currentPlayer)){
                if(i==3){
                    currentPlayer = players.get(0);
                }
                else {
                    currentPlayer = players.get(i + 1);
                }
                break;
            }
        }
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public Game(ArrayList<Player> players) {
        this.players = players;
    }
}
