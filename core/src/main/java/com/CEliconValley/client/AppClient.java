package com.CEliconValley.client;

import com.CEliconValley.common.GameData;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;

public class AppClient {
    private static GameClient client = null;
    private static String loggedInUsername = "";
    private static boolean loggedIn = false;
    private static Menu menu;
    private static GameData gameData = null;

    public static void login(String username) {
        loggedInUsername = username;
        loggedIn = true;
    }

    public static void logout() {
        loggedIn = false;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static String getLoggedInUsername() {
        if(loggedIn) {
            return loggedInUsername;
        }
        return null;
    }

    public static void setLoggedIn(boolean l) {
        loggedIn = l;
    }

    public static void setLoggedInUsername(String l) {
        loggedInUsername = l;
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
}
