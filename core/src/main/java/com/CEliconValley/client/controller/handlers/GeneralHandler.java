package com.CEliconValley.client.controller.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.MainMenuView;
import com.CEliconValley.client.view.ProfileMenuView;
import com.CEliconValley.common.AppData;
import com.CEliconValley.common.HandshakeData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.Menu;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.HashSet;

public class GeneralHandler {
    public static void handle(String type, JsonObject body, Gson gson , long timestamp){
        switch (type) {
            case "handshake-data" -> {
                HandshakeData handshakeData = gson.fromJson(body, HandshakeData.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.setLobbies(
                        new HashSet<>(handshakeData.getCurrentLobbies())
                    );
                    AppClient.setGames(null);
                    AppClient.setOnlinePlayers(new HashSet<>(handshakeData.getOnlinePlayers()));
                });
            }
            case "app-data" -> {
                AppData appData = gson.fromJson(body, AppData.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.setOnlinePlayers(new HashSet<>(appData.onlinePlayers));
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView screen){
                        screen.onlinePlayersUpdate();
                    }
                });
            }
            case "avatar-response" -> {
                UserData userData = gson.fromJson(body, UserData.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.setUserData(userData);
                    if(AppClient.getMenu().getScreen() instanceof ProfileMenuView view){
                        Menu.Profile.resetMenu();
                    }
                });
            }
        }
    }
}
