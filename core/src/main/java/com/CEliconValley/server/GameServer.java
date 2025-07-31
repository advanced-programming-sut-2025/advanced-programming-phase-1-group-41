package com.CEliconValley.server;

import com.CEliconValley.common.OnlineData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.App;
import com.CEliconValley.models.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;

public class GameServer extends WebSocketServer {
    public static final int PORT = 8080;

    private final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());
    private final Map<WebSocket, User> onlineConnections = new HashMap<>();
    public GameServer() {
        super(new InetSocketAddress(PORT));
    }
    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        connections.add(conn);
        System.out.println("New connection: " + conn.getRemoteSocketAddress());
        broadcast("New player joined! Total players: " + connections.size());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        connections.remove(conn);
        App.removeOnlinePlayer(onlineConnections.get(conn).getUsername());
        onlineConnections.remove(conn);
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Gson gson = new Gson();
        try{
            GameMessage<Object> genericMsg = gson.fromJson(message, new TypeToken<GameMessage<Object>>() {}.getType());
            switch (genericMsg.type){
                case "login_request" -> {
                    GameMessage<LoginCred> loginMsg =
                        gson.fromJson(message, new TypeToken<GameMessage<LoginCred>>() {}.getType());
                    LoginCred creds = loginMsg.body;
                    Request.login_req(creds, conn);
                }
                case "forgotpass_request" -> {
                    GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                    Request.showForgotPassword(msg.body, conn);
                }
                case "fp_request" -> {
                    GameMessage<ForgotpassCred> msg = gson.fromJson(message, new TypeToken<GameMessage<ForgotpassCred>>() {}.getType());
                    Request.forgotPassword(msg.body, conn);
                }
                case "prereg_request" ->{
                    GameMessage<PreRegisterCred> msg = gson.fromJson(message, new TypeToken<GameMessage<PreRegisterCred>>() {}.getType());
                    Request.preRegister(msg.body, conn);
                }
                case "register_request" ->{
                    GameMessage<RegisterCred> msg = gson.fromJson(message, new TypeToken<GameMessage<RegisterCred>>() {}.getType());
                    Request.register(msg.body, conn);
                }
                case "profile_request" ->{
                    GameMessage<ProfCred> msg = gson.fromJson(message, new TypeToken<GameMessage<ProfCred>>() {}.getType());
                    Request.profile(msg.body, conn);
                }
                case "logout_request" -> {
                    GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                    Request.logout(msg.body, conn);
                }
                default -> {
                    System.out.println("invalid type: "+genericMsg.type);
                }
            }
        } catch (Exception e) {
            System.out.println("ESmessage: "+message);
        }
        System.out.println("Smessage: "+message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("starting server..");
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
        System.out.println("GameServer started on port " + PORT);
    }

    public Map<WebSocket, User> getOnlineConnections() {
        return onlineConnections;
    }
}
