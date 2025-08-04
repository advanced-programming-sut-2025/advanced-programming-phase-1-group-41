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
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

import java.util.HashSet;

public class GeneralHandler {
    public static void handle(String type, String message, Gson gson){
        switch (type) {
            case "handshake-data" -> {
                GameMessage<HandshakeData> msg = gson.fromJson(message, new TypeToken<GameMessage<HandshakeData>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.setLobbies(
                        new HashSet<>(msg.body.getCurrentLobbies())
                    );
                    AppClient.setGames(null);
                    AppClient.setOnlinePlayers(new HashSet<>(msg.body.getOnlinePlayers()));
                });
                System.out.println("Cmessage: " + message);
            }
            case "app-data" -> {
                GameMessage<AppData> msg = gson.fromJson(message, new TypeToken<GameMessage<AppData>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.setOnlinePlayers(new HashSet<>(msg.body.onlinePlayers));
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView screen){
                        screen.onlinePlayersUpdate();
                    }
                });
                System.out.println("Cmessage: " + message);
            }
            case "avatar-response" -> {
                GameMessage<UserData> msg = gson.fromJson(message, new TypeToken<GameMessage<UserData>>() {}.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.setUserData(msg.body);
                    if(AppClient.getMenu().getScreen() instanceof ProfileMenuView view){
                        Menu.Profile.resetMenu();
                    }
                });
            }
        }
    }
}
