package com.CEliconValley.common;

import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Village;
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
    Lobby lobby;
    ArrayList<PlayerMessage> playerMessages;
    public GameData() {
    }

    public GameData(Game game) {
        this._id = game.get_id();
        this.time = game.getTime();
        this.weatherType = game.getWeatherType();
        this.tmrwWeatherType = game.getTmrwWeatherType();
        this.farmsData = new ArrayList<>();
        this.playersData = new ArrayList<>();
        this.roundEnergy = game.getRoundEnergy();
        this.loaderName = game.getLoader().getUser() == null ? null : game.getLoader().getUser().getUsername();
        this.currentPlayerName = game.getCurrentPlayer().getUser() == null ? null : game.getCurrentPlayer().getUser().getUsername();
        fillPlayers(game);
        fillFarms(game);
        this.villageData = new VillageData(game.getVillage());
        this.lobby = game.getLobby();
        this.playerMessages = game.getPlayerMessages();
    }

    private void fillFarms(Game game) {
        for (Farm farm : game.getFarms()) {
            this.farmsData.add(new FarmData(farm));
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

    public Game makeGame() {
        // players
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 0; i < playersData.size(); i++) {
            PlayerData playerData = playersData.get(i);
            players.add(playerData.getPlayer());
        }
        // player post load
        for (int i = 0; i < playersData.size(); i++) {
            PlayerData playerData = playersData.get(i);
            Player player = playerData.getPlayer();
            ArrayList<Friendship> friendships = getFriendships(players, playerData.getFriendshipsData());
            ArrayList<Gift> newGifts = getGifts(players, playerData.getNewGiftsData());
            ArrayList<Gift> receivedGifts = getGifts(players, playerData.getReceivedGiftsData());
            ArrayList<Gift> sendGifts = getGifts(players, playerData.getSendGiftsData());
            ArrayList<Trade> trades = getTrades(players, playerData.getTradesListData());
            ArrayList<Trade> newTrades = getTrades(players, playerData.getNewTradesListData());
            ArrayList<Trade> totalTrades = getTrades(players, playerData.getTotalTradesListData());
            player.handmadePostLoad(friendships, newGifts, newTrades,
                    receivedGifts, sendGifts, totalTrades, trades);
        }
        Village village = this.villageData.getVillage(players);
        ArrayList<Farm> farms = getFarms(players);
        Game game = new Game(this.time, village, this.weatherType, this.tmrwWeatherType,
                this.roundEnergy, farms, this.playerMessages);
        Player currentPlayer = PlayerFinder.getPlayerByName(players, currentPlayerName);
        Player loader = PlayerFinder.getPlayerByName(players, loaderName);
        game.handmadePostLoad(currentPlayer, loader, players);
        setUsersCurrentGame(game);
        game.set_id(this._id);
        game.setLobby(this.lobby);
        return game;
    }

    // TODO remember to unset it as well
    private void setUsersCurrentGame(Game game){
        for (Player player : game.getPlayers()) {
            player.getUser().setCurrentGame(game);
        }
    }

    public ArrayList<Farm> getFarms(ArrayList<Player> players){
        ArrayList<Farm> farms = new ArrayList<>();
        for (FarmData farmsDatum : farmsData) {
            Player player = PlayerFinder.getPlayerByFarm(players, farmsDatum.id);
            farms.add(farmsDatum.getFarm(player));
        }
        return farms;
    }

    public ArrayList<Friendship> getFriendships(ArrayList<Player> players,ArrayList<FriendshipData> friendshipsData) {
        ArrayList<Friendship> friendships = new ArrayList<>();
        if(friendshipsData == null){
            return friendships;
        }
        for (FriendshipData fd : friendshipsData) {
            Player player1 = PlayerFinder.getPlayerByName(players, fd.getPlayer1Name());
            Player player2 = PlayerFinder.getPlayerByName(players, fd.getPlayer2Name());
            Player proposer = fd.getProposerName() == null ? null : PlayerFinder.getPlayerByName(players, fd.getProposerName());
            friendships.add(fd.getFriendship(player1, player2, proposer));
        }
        return friendships;
    }
    public ArrayList<Gift> getGifts(ArrayList<Player> players,ArrayList<GiftData> giftsData) {
        ArrayList<Gift> gifts = new ArrayList<>();
        if(giftsData == null) return gifts;
        for (GiftData gd : giftsData) {
            Player from = PlayerFinder.getPlayerByName(players, gd.getFromName());
            Player to = PlayerFinder.getPlayerByName(players, gd.getToName());
            gifts.add(gd.getGift(from, to));
        }
        return gifts;
    }
    public ArrayList<Trade> getTrades(ArrayList<Player> players,ArrayList<TradeData> tradesListData) {
        ArrayList<Trade> tradesList = new ArrayList<>();
        if(tradesListData == null) return tradesList;
        for (TradeData td : tradesListData) {
            Player from = PlayerFinder.getPlayerByName(players, td.getFromName());
            Player to = PlayerFinder.getPlayerByName(players, td.getToName());
            tradesList.add(td.getTrade(from, to));
        }
        return tradesList;
    }

    public ObjectId get_id() {
        return _id;
    }

    public String getCurrentPlayerName() {
        return currentPlayerName;
    }

    public ArrayList<FarmData> getFarmsData() {
        return farmsData;
    }

    public String getLoaderName() {
        return loaderName;
    }

    public ArrayList<PlayerData> getPlayersData() {
        return playersData;
    }

    public double getRoundEnergy() {
        return roundEnergy;
    }

    public TimeLine getTime() {
        return time;
    }

    public WeatherType getTmrwWeatherType() {
        return tmrwWeatherType;
    }

    public VillageData getVillageData() {
        return villageData;
    }

    public WeatherType getWeatherType() {
        return weatherType;
    }

    public Lobby getLobby() {
        return lobby;
    }

    public ArrayList<PlayerMessage> getPlayerMessages() {
        return playerMessages;
    }

    public void setPlayerMessages(ArrayList<PlayerMessage> playerMessages) {
        this.playerMessages = playerMessages;
    }
}
