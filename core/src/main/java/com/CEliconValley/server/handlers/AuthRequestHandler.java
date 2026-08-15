package com.CEliconValley.server.handlers;

import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.User;
import com.CEliconValley.server.GameServer;
import com.CEliconValley.server.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.java_websocket.WebSocket;


public class AuthRequestHandler {
    public static void handle(String type, String message, WebSocket conn, Gson gson) {
        switch (type){
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
            case "avatar-request" -> {
                GameMessage<String> msg = gson.fromJson(message, new TypeToken<GameMessage<String>>() {}.getType());
                User user = App.getServer().getOnlineConnections().get(conn);
                user.setAvatarPath(msg.body);
                UserDB.saveUser(user);
                GameMessage<UserData> response = new GameMessage<>("avatar-response",new UserData(user));
                conn.send(gson.toJson(response));
            }
        }
    }
}
