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
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

public class ClientLobbyHandler {
    public static void handle(String type, JsonObject body, Gson gson, long timestamp){
        switch (type) {
            case "new-lobby" -> {
                Lobby lobby = gson.fromJson(body, Lobby.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.addLobby(lobby);
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                        if(view.getJoinLobby()){
                            view.showJoinLobbyForm();
                        }
                    }
                    if(AppClient.getCurrentLobby() != null && AppClient.getCurrentLobby().equals(lobby)){
                        AppClient.setCurrentLobby(lobby);
                        System.out.println("new number of players "+lobby.getPlayerNames().size());
                        if(AppClient.getMenu().getScreen() instanceof LobbyScreen view){
                            AppClient.getMenu().resetMenu();
                        }
                    }else{
                        System.out.println(AppClient.getCurrentLobby());
                    }
                });

            }
            case "join-lobby" -> {
                Lobby lobby = gson.fromJson(body, Lobby.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.addLobby(lobby);
                    AppClient.setCurrentLobby(lobby);
                    Menu.Lobby.resetMenu();
                    AppClient.setMenu(Menu.Lobby);
                    Main.getMain().setScreen(AppClient.getMenu().getScreen());
                });
            }
            case "delete-lobby" -> {
                Lobby lobby = gson.fromJson(body, Lobby.class);
                Gdx.app.postRunnable(() -> {
                    AppClient.getLobbies().remove(lobby);
                    if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                        if(view.getJoinLobby()) {
                            view.showJoinLobbyForm();
                        }
                    }
                });
                System.out.println("Cmessage: deleted lobby");
            }

        }
    }
    public static void handle(String type, String msg, Gson gson, long timestamp){
        switch (type) {
            case "leave-lobby" -> {
                System.out.println("trying to leave lobby");
                Gdx.app.postRunnable(() -> {
                    AppClient.setCurrentLobby(null);
                    Menu.Main.resetMenu();
                    AppClient.setMenu(Menu.Main);
                    Main.getMain().setScreen(AppClient.getMenu().getScreen());
                });
            }
            case "lobby-error" -> {
                if(AppClient.getMenu().menu instanceof LobbyScreen view){
                    view.setErrorMessage(msg);
                }
            }
        }
    }


}
