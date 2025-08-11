package com.CEliconValley.server;

import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameServer extends WebSocketServer {
    public static final int PORT = 6969;

    private final Set<WebSocket> connections = Collections.synchronizedSet(new HashSet<>());
    private final Map<WebSocket, User> onlineConnections = Collections.synchronizedMap(new HashMap<>());
    public GameServer() {
        super(new InetSocketAddress("0.0.0.0",PORT));
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
        Iterator<Lobby> lobbyIterator = App.lobbies.iterator();
        while (lobbyIterator.hasNext()) {
            Lobby lobby = lobbyIterator.next();
            if(lobby.getPlayerNames().contains(onlineConnections.get(conn).getUsername())){
                lobby.removePlayer(onlineConnections.get(conn).getUsername());
            }
            if(lobby.getPlayerNames().isEmpty()){
                System.out.println("im here bitch");
                App.lobbies.remove(lobby);
                GameMessage<Lobby> response2 = new GameMessage<>("delete-lobby", lobby);
                App.getServer().broadcast(new Gson().toJson(response2));
            }
        }


        for (Player player : App.getGame().getPlayers()) {
            if(player.getUser().getUsername().equals(onlineConnections.get(conn).getUsername())){
                App.getDcguys().add(new DCguy(App.getGame().get_id(), player.getUser()));
                GameMessage<GameCommand> msg = new GameMessage<>("game-command",
                    new GameCommand("dc-game", ":)"));
                ArrayList<String> names = new ArrayList<>();
                for (Player p : App.getGame().getPlayers()) {
                    if(!p.getUser().getUsername().equals(player.getUser().getUsername())){
                        names.add(p.getUser().getUsername());
                    }
                }
                sendToGroup(names, new Gson().toJson(msg));
                System.out.println(player.getUser().getUsername()+" from inside the game got out :(");
                App.getGame().stopScheduler();
                App.dcTimestamp = System.currentTimeMillis();
            }
        }

        onlineConnections.remove(conn);
        System.out.println("Closed connection: " + conn.getRemoteSocketAddress());
    }

    @Override
    public void onMessage(WebSocket conn, String message) {
        Gson gson = new Gson();
        try{
            GameMessage<Object> genericMsg = gson.fromJson(message, new TypeToken<GameMessage<Object>>() {}.getType());
            ServerMessageRouter.route(genericMsg.type, message, conn, gson);
            System.out.println(onlineConnections);
        } catch (Exception e) {
            System.out.println("ESmessage: "+message);
            e.printStackTrace();
        }
        System.out.println("Smessage: "+message);
    }

    @Override
    public void onError(WebSocket conn, Exception ex) {
        ex.printStackTrace();
    }

    @Override
    public void onStart() {
        System.out.println("server started ;)");
    }

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.start();
        System.out.println("GameServer started on port " + PORT);
    }

    public Map<WebSocket, User> getOnlineConnections() {
        return onlineConnections;
    }

    public void sendToUsername(String username,String message){
        onlineConnections.forEach((k,v)->{
            if(v.getUsername().equals(username)){
                k.send(message);
            }
        });
    }

    public void sendToGroupByPlayers(ArrayList<Player> players, String message){
        ArrayList<String> names = new ArrayList<>();
        for (Player player : players) {
            names.add(player.getUser().getUsername());
        }
        sendToGroup(names, message);
    }

    public void sendToGroup(ArrayList<String> names, String message) {
        AtomicInteger checker = new AtomicInteger();
        onlineConnections.forEach((k,v)->{
            if(names.contains(v.getUsername())){
                k.send(message);
                checker.addAndGet(1);
            }
        });
        if(checker.get() != names.size()){
            System.out.println("smth went wrong");
            System.out.println("checker is " + checker);
            System.out.println("names size is " + names.size());
        }
    }

    public void sendToPlayername(String playername, String message){
        onlineConnections.forEach((k,v)->{
            if(v.getUsername().equals(playername)){
                k.send(message);
            }
        });
    }

    public void sendToPlayer(Player player, String message){
        onlineConnections.forEach((k,v)->{
            if(v.getUsername().equals(player.getUser().getUsername())){
                k.send(message);
            }
        });
    }
}
