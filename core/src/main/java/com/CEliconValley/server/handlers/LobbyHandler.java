package com.CEliconValley.server.handlers;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.MakeLobbyInfo;
import com.CEliconValley.models.Lobby;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;

public class LobbyHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson){
        switch (type) {
            case "make-lobby" -> {
                GameMessage<MakeLobbyInfo> msg = gson.fromJson(message, new TypeToken<GameMessage<MakeLobbyInfo>>() {}.getType());
                MakeLobbyInfo lobbyInfo = msg.body;
//                Lobby lobby;
//                if(lobbyInfo.isPrivate){
//                    lobby = new Lobby(lobbyInfo.lobbyName, lobbyInfo.admin, lobbyInfo.password, lobbyInfo.isVisible);
//                }else{
//                    lobby = new Lobby(lobbyInfo.lobbyName, lobbyInfo.admin, lobbyInfo.isVisible);
//                }
//                AppClient.setCurrentLobby(lobby);
            }
        }
    }
}
