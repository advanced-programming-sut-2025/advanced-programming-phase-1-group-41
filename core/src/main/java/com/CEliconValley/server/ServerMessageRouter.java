package com.CEliconValley.server;

import com.CEliconValley.server.handlers.AuthRequestHandler;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;


public class ServerMessageRouter {
    public static void route(String type, String rawJson, WebSocket conn, Gson gson) {
        switch (type) {
            case "login_request", "forgotpass_request", "fp_request",
                 "prereg_request", "register_request", "profile_request" ,
                 "logout_request" -> AuthRequestHandler.handle(type, rawJson, conn, gson);
            default -> System.out.println("Unknown type: " + type);
        }
    }

}
