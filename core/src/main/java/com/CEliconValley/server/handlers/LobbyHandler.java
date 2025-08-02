package com.CEliconValley.server.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.Finder;
import com.CEliconValley.models.Lobby;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

public class LobbyHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson) {
        switch (type) {
            case "make-lobby" -> {
                GameMessage<MakeLobbyInfo> msg = gson.fromJson(message, new TypeToken<GameMessage<MakeLobbyInfo>>() {
                }.getType());
                MakeLobbyInfo lobbyInfo = msg.body;
//                System.out.println("Lobby: " + msg.body);
                Lobby lobby;
                if (lobbyInfo.isPrivate) {
                    lobby = new Lobby(lobbyInfo.lobbyName, lobbyInfo.password, lobbyInfo.admin, lobbyInfo.isVisible,
                        conn);
                } else {
                    lobby = new Lobby(lobbyInfo.lobbyName, lobbyInfo.admin, lobbyInfo.isVisible,
                        conn);
                }
//                AppClient.setCurrentLobby(lobby);
            }
            case "join-lobby" -> {
                GameMessage<JoinLobbyCred> msg = gson.fromJson(message, new TypeToken<GameMessage<JoinLobbyCred>>() {
                }.getType());
                JoinLobbyCred cred = msg.body;
                Lobby lobby = Finder.getLobbyById(cred.id);
                if (lobby == null) {
                    GameMessage<ErrorMessage> response = new GameMessage<>("join-lobby", new ErrorMessage("404", "Lobby not found!"));
                    conn.send(gson.toJson(response));
                }
                if (lobby.isPrivate()) {
                    if (!lobby.getPassword().equals(cred.password)) {
                        GameMessage<ErrorMessage> response = new GameMessage<>("join-lobby", new ErrorMessage("password-wrong", "Password is wrong!"));
                        conn.send(gson.toJson(response));
                    }
                }
                lobby.addPlayer(cred.username);
                GameMessage<Lobby> response = new GameMessage<>("join-lobby",lobby);
                conn.send(gson.toJson(response));
            }
            case "leave-lobby" -> {
                GameMessage<LeaveLobbyCred> msg = gson.fromJson(message, new TypeToken<GameMessage<LeaveLobbyCred>>() {}.getType());
                LeaveLobbyCred cred = msg.body;
                Lobby lobby = Finder.getLobbyById(cred.id);
                lobby.removePlayer(cred.username);
                GameMessage<String> response = new GameMessage<>("leave-lobby",";)");
                conn.send(gson.toJson(response));
            }
        }
    }
}
