package com.CEliconValley.client;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.OnlineData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Lobby;
import com.CEliconValley.models.Menu;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class AppClient {
    private static GameClient client = null;
    private static UserData userData = null;
    private static boolean loggedIn = false;
    private static Menu menu;
    private static GameData gameData = null;
    public final static ArrayList<String> questions = new ArrayList<>();
    private static Set<Lobby> lobbies = new HashSet<>();
    private static Lobby currentLobby;
    private static Set<GameData> games = new HashSet<>();
    private static Set<OnlineData> onlinePlayers = new HashSet<>();

    public static void login(UserData ud) {
        userData = ud;
        loggedIn = true;
        Menu.Main.resetMenu();
        AppClient.setMenu(Menu.Main);
    }

    public static void logout() {
        loggedIn = false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static UserData getUserData() {
        if(loggedIn) {
            return userData;
        }
        return null;
    }

    public static void setLoggedIn(boolean l) {
        loggedIn = l;
    }


    public static GameClient getClient() {
        if(client == null) {
            client = App.getClient();
        }
        return client;
    }

    public static GameData getGameData() {
        return gameData;
    }

    public static void setGameData(GameData gameData) {
        AppClient.gameData = gameData;
    }

    public static void setUserData(UserData userData) {
        AppClient.userData = userData;
    }

    public static Menu getMenu() {
        return menu;
    }

    public static void setMenu(Menu menu) {
        AppClient.menu = menu;
    }

    public static void setQuestions(ArrayList<String> questions){
        AppClient.questions.clear();
        AppClient.questions.addAll(questions);
    }

    public static Set<Lobby> getLobbies() {
        return lobbies;
    }


    public static void setLobbies(Set<Lobby> lobbies) {
        AppClient.lobbies = lobbies;
    }

    public static void setClient(GameClient client) {
        AppClient.client = client;
    }

    public static Set<GameData> getGames() {
        return games;
    }

    public static void setGames(Set<GameData> games) {
        AppClient.games = games;
    }

    public static Set<OnlineData> getOnlinePlayers() {
        return onlinePlayers;
    }

    public static void setOnlinePlayers(Set<OnlineData> onlinePlayers) {
        AppClient.onlinePlayers = onlinePlayers;
    }

    public static Lobby getCurrentLobby() {return currentLobby;}

    public static void setCurrentLobby(Lobby lobby) {currentLobby = lobby;}

    public static void addLobby(Lobby lobby){
        if(lobbies.contains(lobby)){
            lobbies.remove(lobby);
        }
        lobbies.add(lobby);
    }
}
