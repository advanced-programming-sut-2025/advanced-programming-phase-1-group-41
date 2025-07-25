package com.CEliconValley.common;

import com.CEliconValley.models.Game;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Transient;

import java.util.ArrayList;

@Entity("gamedata")
public class GameData {
    private Game game;

    ArrayList<FarmData> farms;
    String loaderName;
    String currentPlayerName;
    ArrayList<PlayerData> players;

    public GameData(Game game) {
        this.game = game;
        this.farms = new ArrayList<>();
        this.players = new ArrayList<>();
        this.loaderName = game.getLoader().getUser().getUsername();
        this.currentPlayerName = game.getCurrentPlayer().getUser().getUsername();
        fillPlayers();
    }

    private void fillPlayers(){
        for (PlayerData player : players) {

        }
    }

    public GameData cloneGame(){
        return null;
    }

    public Game makeGame(){
        return null;
    }
}
