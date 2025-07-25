package com.CEliconValley.server;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.Set;

public class GameServer extends WebSocketServer {
    private static final int PORT = 8080;
    private final Set<WebSocket> connections = new HashSet<>();
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
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        System.out.println("Received: " + message);
        broadcast("Echo: " + message); // Echo back to all clients
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
}
