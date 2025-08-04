package com.CEliconValley.client.controller.handlers;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.LobbyScreen;
import com.CEliconValley.client.view.MainMenuView;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.Lobby;
import com.CEliconValley.models.Menu;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class ClientLobbyHandler {
    public static void handle(String type, String message, Gson gson){
        System.out.println("CMessage "+message);
        switch (type) {
            case "new-lobby" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.addLobby(msg.body);
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                        if(view.getJoinLobby()){
                            view.showJoinLobbyForm();
                        }
                    }
                    if(AppClient.getCurrentLobby() != null && AppClient.getCurrentLobby().equals(msg.body)){
                        AppClient.setCurrentLobby(msg.body);
                        System.out.println("new number of players "+msg.body.getPlayerNames().size());
                        if(AppClient.getMenu().getScreen() instanceof LobbyScreen view){
                            AppClient.getMenu().resetMenu();
                        }
                    }else{
                        System.out.println(AppClient.getCurrentLobby());
                    }
                });

            }
            case "join-lobby" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.addLobby(msg.body);
                    AppClient.setCurrentLobby(msg.body);
                    Menu.Lobby.resetMenu();
                    AppClient.setMenu(Menu.Lobby);
                    Main.getMain().setScreen(AppClient.getMenu().getScreen());
                });
            }
            case "leave-lobby" -> {
                Gdx.app.postRunnable(() -> {
                    AppClient.setCurrentLobby(null);
                    Menu.Main.resetMenu();
                    AppClient.setMenu(Menu.Main);
                    Main.getMain().setScreen(AppClient.getMenu().getScreen());
                });
            }
            case "delete-lobby" -> {
                GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                }.getType());
                Gdx.app.postRunnable(() -> {
                    AppClient.getLobbies().remove(msg.body);
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                        if(view.getJoinLobby()) {
                            view.showJoinLobbyForm();
                        }
                    }
                });
            }
        }
    }
}
