package com.CEliconValley.models;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameCommand;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.npc.npcCharacters.NPC;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Transient;
import org.bson.types.ObjectId;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private Lobby lobby;
    private ArrayList<PlayerMessage> playerMessages;

    private double roundEnergy;
    @Transient
    private ArrayList<Farm> farms = new ArrayList<>();
//    private Map map;

    public Village getVillage() {
        return village;
    }

    public ScheduledExecutorService scheduler;
    public Thread commandThread;
    private int howManyInHome = 0;
    private int howManyForVote = 0;
    private int howManyForTer = 0;
    private String whichToVote = "";

    public Game() {
    }


    public Game(TimeLine time, Village village,
                WeatherType weatherType, WeatherType tmrwWeatherType,
                double roundEnergy, ArrayList<Farm> farms, ArrayList<PlayerMessage> playerMessages) {
        this.time = time;
        this.village = village;
        this.weatherType = weatherType;
        this.tmrwWeatherType = tmrwWeatherType;
        this.roundEnergy = roundEnergy;
        this.farms = new ArrayList<>(farms);
        this.playerMessages = playerMessages;
    }


    public void handmadePostLoad(Player currentPlayer, Player loader, ArrayList<Player> players) {
        this.currentPlayer = currentPlayer;
        this.loader = loader;
        this.players = new ArrayList<>(players);
    }

    public Game(ArrayList<Player> players, Player loader, Lobby lobby) {
        this.players = new ArrayList<>(players);
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
            players.get(i).setFarmId(i);
        }
        this.lobby = lobby;
        this.playerMessages = new ArrayList<>();
        this.playerMessages = new ArrayList<>();
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

    public void startScheduler() {
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(() -> {
            try {
                getTime().advanceOneHour(false);
                GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(this));
                App.getServer().sendToGroupByPlayers(getPlayers(), new Gson().toJson(msg));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 10, 10, TimeUnit.SECONDS);
    }

    public void stopScheduler() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdownNow();
        }
    }


    public int getHowManyInHome() {
        return howManyInHome;
    }

    public void setHowManyInHome(int howManyInHome) {
        this.howManyInHome = howManyInHome;
    }
    public void incHowManyInHome(){
        howManyInHome++;
        if(howManyInHome == players.size()){
            startScheduler();
            howManyInHome = 0;
            time.advanceOneDay();
        }
    }

    public int getHowManyForVote() {
        return howManyForVote;
    }

    public void setHowManyForVote(int howManyForVote) {
        this.howManyForVote = howManyForVote;
    }
    public void incHowManyForVote(){
        howManyForVote++;
        if(howManyForVote == players.size()){
            // todo logic to remove player
            Player player = Finder.getPlayerByUsername(whichToVote);
            App.getGame().removePlayerFromGame(player);
            GameMessage<String> response = new GameMessage<>("game-command",
                "terminate-vote");
            App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), new Gson().toJson(response));
            howManyForVote = 0;
            whichToVote = "";
            GameMessage<String> exiter = new GameMessage<>("game-command","exit-game");
            App.getServer().getOnlineConnections().forEach((k,v) -> {
                if(v.equals(player.getUser())){
                    k.send(new Gson().toJson(exiter));
                }
            });
            GameMessage<GameData> msg = new GameMessage<>("game-data", new GameData(this));
            App.getServer().sendToGroupByPlayers(getPlayers(), new Gson().toJson(msg));
        }else{
            GameMessage<GameCommand> response = new GameMessage<>("game-command",
                new GameCommand("update-vote", ""+App.getGame().getHowManyForVote()));
            App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), new Gson().toJson(response));

        }
    }
    public int getHowManyForTer() {
        return howManyForTer;
    }

    public void setHowManyForTer(int howManyForTer) {
        this.howManyForTer = howManyForTer;
    }
    public void incHowManyForTer(){
        howManyForTer++;
        if(howManyForTer == players.size()){
            this.stopScheduler();
            GameMessage<String> exiter = new GameMessage<>("game-command","exit-game");
            App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), new Gson().toJson(exiter));
            App.setGame(null);
            App.setPreGame(null);
        }else{
            GameMessage<GameCommand> response = new GameMessage<>("game-command",
                new GameCommand("update-ter", ""+App.getGame().getHowManyForTer()));
            App.getServer().sendToGroupByPlayers(App.getGame().getPlayers(), new Gson().toJson(response));
        }
    }

    public String getWhichToVote() {
        return whichToVote;
    }

    public void setWhichToVote(String whichToVote) {
        this.whichToVote = whichToVote;
    }


    public void removePlayerFromGame(Player player) {
        this.players.remove(player);
        if(this.loader.equals(player)){
            this.loader = this.players.get(0);
        }
        for (Farm farm : this.farms) {
            if(farm.getId() == player.getFarmId()){
                removeFarmFromGame(farm);
                break;
            }
        }
        for (Player p : players) {
            Iterator fi = p.getFriendships().iterator();
            while (fi.hasNext()) {
                Friendship f = (Friendship) fi.next();
                if(f.getPlayer1().equals(player) || f.getPlayer2().equals(player)){
                    fi.remove();
                }
            }
        }
        for (NPC npc : village.getNPCs()) {
            npc.getFriendShip().remove(player);
            npc.getIsTalkedToday().remove(player);
            npc.getIsGiftedToday().remove(player);
        }
    }
    public void removeFarmFromGame(Farm farm) {
        this.farms.remove(farm);
    }

    public Lobby getLobby() {
        return lobby;
    }

    public void setLobby(Lobby lobby) {
        this.lobby = lobby;
    }

    public ArrayList<PlayerMessage> getPlayerMessages() {
        return playerMessages;
    }
}
