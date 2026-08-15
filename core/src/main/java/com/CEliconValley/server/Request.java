package com.CEliconValley.server;

import com.CEliconValley.common.messages.*;
import com.CEliconValley.models.App;
import com.CEliconValley.server.controller.ServerAuthentication;
import com.CEliconValley.server.controller.ServerProfile;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;


public class Request {
    static Gson gson = new Gson();
    public static void login_req(LoginCred creds, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("login_response",ServerAuthentication.handleLogin(creds, conn));
            String json = gson.toJson(response);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void showForgotPassword(String username, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("forgotpass_response",ServerAuthentication.showForgotPassword(username));
            String json = gson.toJson(response);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void forgotPassword(ForgotpassCred creds, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("fp_response",ServerAuthentication.forgotPass(creds));
            String json = gson.toJson(response);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void preRegister(PreRegisterCred creds, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("prereg_response",ServerAuthentication.handlePreReg(creds));
            String json = gson.toJson(response);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void register(RegisterCred creds, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("register_response",ServerAuthentication.register(creds));
            String json = gson.toJson(response);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void profile(ProfCred creds, WebSocket conn) {
        try{
            Message message = null;
            switch (creds.key){
                case "username" -> {
                    message = ServerProfile.handleChangeUsername(creds.value, creds.userData.getUsername());
                }
                case "password" -> {
                    message = ServerProfile.handleChangePassword(creds.value, creds.userData.getUsername());
                }
                case "email" -> {
                    message = ServerProfile.handleChangeEmail(creds.value, creds.userData.getUsername());
                }
                case "nickname" -> {
                    message = ServerProfile.handleChangeNickname(creds.value, creds.userData.getUsername());
                }
                case "delete" -> {
                    message = ServerProfile.delete(creds.userData.getUsername());
                }
                default -> {
                    System.out.println("unknown key");
                }
            }
            GameMessage<Message> messageResponse = new GameMessage<>("profile_response",message);
            String json = gson.toJson(messageResponse);
            conn.send(json);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void logout(String username ,WebSocket conn) {
        App.removeOnlinePlayer(username);
        App.getServer().getOnlineConnections().remove(conn);
    }

}
