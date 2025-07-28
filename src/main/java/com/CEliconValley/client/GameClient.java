package com.CEliconValley.client;

import com.CEliconValley.common.GameData;
import com.CEliconValley.models.Game;
import com.CEliconValley.models.locations.Farm;
import com.google.gson.Gson;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class GameClient extends WebSocketClient {

    public GameClient(URI serverUri) {
        super(serverUri);
    }

    public static void main(String[] args) {
        try {
            String first = "ws://localhost:8080";
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
            GameData data = gson.fromJson(message, GameData.class);
            System.out.println("received the data :D");
            System.out.println("client code:");
            Game game = data.makeGame();
            for (Farm farm : game.getFarms()) {
                farm.printMap();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
//            System.out.println("received message: "+message);
        }
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
