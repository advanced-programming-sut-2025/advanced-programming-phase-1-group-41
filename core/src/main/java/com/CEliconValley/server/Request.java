package com.CEliconValley.server;

import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.LoginCred;
import com.CEliconValley.common.messages.Message;
import com.CEliconValley.controllers.authentication.ServerAuthentication;
import com.CEliconValley.models.App;
import com.google.gson.Gson;

public class Request {
    static Gson gson = new Gson();
    public static void login_req(LoginCred creds) {
        try{
            GameMessage<Message> response = new GameMessage<>("login_response",ServerAuthentication.handleLogin(creds));
            App.getServer().broadcast(gson.toJson(response));
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
