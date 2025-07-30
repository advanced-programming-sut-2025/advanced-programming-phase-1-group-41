package com.CEliconValley.server;

import com.CEliconValley.common.messages.ForgotpassCred;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.LoginCred;
import com.CEliconValley.common.messages.Message;
import com.CEliconValley.controllers.authentication.ServerAuthentication;
import com.CEliconValley.models.App;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;


public class Request {
    static Gson gson = new Gson();
    public static void login_req(LoginCred creds, WebSocket conn) {
        try{
            GameMessage<Message> response = new GameMessage<>("login_response",ServerAuthentication.handleLogin(creds));
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
}
