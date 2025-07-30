package com.CEliconValley.client;

import com.CEliconValley.common.GameData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;

import java.util.ArrayList;

public class AppClient {
    private static GameClient client = null;
    private static UserData userData = null;
    private static boolean loggedIn = false;
    private static Menu menu;
    private static GameData gameData = null;
    public final static ArrayList<String> questions = new ArrayList<>();

    public static void login(UserData ud) {
        userData = ud;
        loggedIn = true;
        AppClient.setMenu(Menu.Main);
        Menu.Main.resetMenu();
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
}
