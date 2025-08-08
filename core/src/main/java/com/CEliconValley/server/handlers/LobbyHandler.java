package com.CEliconValley.server.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
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
                        conn, false);
                } else {
                    lobby = new Lobby(lobbyInfo.lobbyName, lobbyInfo.admin, lobbyInfo.isVisible,
                        conn, false);
                }
//                AppClient.setCurrentLobby(lobby);
            }
            case "join-lobby" -> {
                GameMessage<JoinLobbyCred> msg = gson.fromJson(message, new TypeToken<GameMessage<JoinLobbyCred>>() {
                }.getType());
                JoinLobbyCred cred = msg.body;
                Lobby lobby = Finder.getLobbyById(cred.id);
                if (lobby == null) {
                    GameMessage<ErrorMessage> response = new GameMessage<>("join-lobby", new ErrorMessage("join-lobby", "Lobby not found!"));
                    conn.send(gson.toJson(response));
                    System.out.println("got null!");
                    return;
                }
                if (lobby.isPrivate()) {
                    if (!lobby.getPassword().equals(cred.password)) {
                        GameMessage<ErrorMessage> response = new GameMessage<>("join-lobby", new ErrorMessage("join-lobby", "Password is wrong!"));
                        conn.send(gson.toJson(response));
                        return;
                    }
                }
                lobby.addPlayer(cred.username);
                GameMessage<Lobby> response = new GameMessage<>("join-lobby",lobby);
                conn.send(gson.toJson(response));
            }
            case "leave-lobby" -> {
                System.out.println("Shere ;)");
                GameMessage<LeaveLobbyCred> msg = gson.fromJson(message, new TypeToken<GameMessage<LeaveLobbyCred>>() {}.getType());
                LeaveLobbyCred cred = msg.body;
                Lobby lobby = Finder.getLobbyById(cred.id);
                handleLeaveLobby(lobby, conn, gson, cred.username);
            }
        }
    }

    public static void handleLeaveLobby(Lobby lobby, WebSocket conn, Gson gson, String username){
        if(lobby == null) {
            GameMessage<ErrorMessage> err = new GameMessage<>("leave-lobby",
                new ErrorMessage("leave-lobby", "404"));
            conn.send(gson.toJson(err));
            return;
        }
        Result result = lobby.removePlayer(username);
        System.out.println(result.success()+" result " + result);
        if(result.success()){
            GameMessage<String> response = new GameMessage<>("leave-lobby",";)");
            conn.send(gson.toJson(response));
        }
        else if(!result.success()){
            if(result.message().equals("empty")) {
                GameMessage<String> response = new GameMessage<>("leave-lobby", ";)");
                conn.send(gson.toJson(response));
                System.out.println("sent "+gson.toJson(response));
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                App.lobbies.remove(lobby);
                GameMessage<Lobby> response2 = new GameMessage<>("delete-lobby", lobby);
                App.getServer().broadcast(gson.toJson(response2));
            }
        }
    }
}
