package com.CEliconValley.client;

import com.CEliconValley.Main;
import com.CEliconValley.client.view.MainMenuView;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.common.AppData;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.HandshakeData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.badlogic.gdx.Gdx;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.util.HashSet;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    public static void main(String[] args) {
        try {
            String first = "ws://localhost:6969";
            String second = "wss://5d92bdf5fa08.ngrok-free.app";
            URI serverUri = new URI(first);
            GameClient client = new GameClient(serverUri);
            client.connect(); // Starts async connection
            while (!client.isOpen()) {
                Thread.sleep(50);
            }
            client.send("sup");
            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("Connected to server!");
        send("Hello from client!");
    }

    @Override
    public void onMessage(String message) {
        Gson gson = new Gson();
        try {
            GameMessage<Object> genericMsg = gson.fromJson(message, new TypeToken<GameMessage<Object>>() {
            }.getType());
            JsonObject jsonObject = JsonParser.parseString(message).getAsJsonObject();
            JsonElement bodyElement = jsonObject.get("body");
            GameMessage<SuccessMessage> successMsg = null;
            GameMessage<ErrorMessage> errorMsg = null;
            if (bodyElement.getAsJsonObject().has("success")) {
                successMsg = gson.fromJson(message, new TypeToken<GameMessage<SuccessMessage>>() {
                }.getType());
            } else if (bodyElement.getAsJsonObject().has("error")) {
                errorMsg = gson.fromJson(message, new TypeToken<GameMessage<ErrorMessage>>() {
                }.getType());
            }
            if (successMsg != null) {
                Response.successResponse(successMsg.body);
            } else if (errorMsg != null) {
                Response.errorResponse(errorMsg.body);
            } else {
                switch (genericMsg.type) {
                    case "login_response", "forgotpass_response",
                         "fp_response", "profile_response" -> {
                        System.out.println("Cmessage: " + message);
                    }

                    case "gamedata" -> {
                        GameMessage<GameData> gameDataMessage = gson.fromJson(message, new TypeToken<GameMessage<GameData>>() {
                        }.getType());
                        Gdx.app.postRunnable(() -> {
                            AppClient.setGameData(gameDataMessage.body);
                        });
                        System.out.println("Cmessage: updated gamedata");
                    }
                    case "new-game" -> {
                        GameMessage<GameData> gameDataMessage = gson.fromJson(message, new TypeToken<GameMessage<GameData>>() {
                        }.getType());
                        Gdx.app.postRunnable(() -> {
                            AppClient.setMenu(Menu.Game);
                            Menu.Game.resetMenu();
                            AppClient.setGameData(gameDataMessage.body);
                            ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new FarmScreen(new Farm(1),
                                gameDataMessage.body.getPlayersData().get(0).getPlayer()));
                        });
                    }
                    case "new-lobby" -> {
                        GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                        }.getType());
                        Gdx.app.postRunnable(() -> {
                            AppClient.getLobbies().add(msg.body);
                            if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                                if(view.getJoinLobby()){
                                    view.showJoinLobbyForm();
                                }
                            }
                            System.out.println("updated lobbies in view");
                        });

                        System.out.println("Cmessage: updated lobby");
                    }
                    case "join-lobby" -> {
                        GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                        }.getType());
                        Gdx.app.postRunnable(() -> {
                            AppClient.getLobbies().add(msg.body);
                            AppClient.setCurrentLobby(msg.body);
                            Menu.Lobby.resetMenu();
                            AppClient.setMenu(Menu.Lobby);
                            Main.getMain().setScreen(AppClient.getMenu().getScreen());
                        });
                    }
                    case "leave-lobby" -> {
                        AppClient.setCurrentLobby(null);
                        AppClient.setMenu(Menu.Main);
                        Menu.Main.resetMenu();
                        Main.getMain().setScreen(AppClient.getMenu().getScreen());
                    }
                    case "delete-lobby" -> {
                        GameMessage<Lobby> msg = gson.fromJson(message, new TypeToken<GameMessage<Lobby>>() {
                        }.getType());
                        // maybe check if need to iterate annd remove
                        Gdx.app.postRunnable(() -> {
                            AppClient.getLobbies().remove(msg.body);
                            if(AppClient.getMenu().getScreen() instanceof MainMenuView view){
                                if(view.getJoinLobby()) {
                                    view.showJoinLobbyForm();
                                }
                            }
                        });
                        System.out.println("Cmessage: updated lobby");
                    }
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
                                screen.onlineplayersUpdate();
                            }
                        });
                        System.out.println("Cmessage: " + message);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("CmessageE: " + message);
        }
//        try {
//            GameData data = gson.fromJson(message, GameData.class);
//            System.out.println("received the data :D");
//            System.out.println("client code:");
//            Game game = data.makeGame();
//            for (Farm farm : game.getFarms()) {
//                farm.printMap();
//            }
//        } catch (Exception e) {
//            System.out.println(e.getMessage());
////            System.out.println("received message: "+message);
//        }
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("Connection closed: " + reason + " (Code: " + code + ")");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("WebSocket Error:");
        ex.printStackTrace();
    }
}
