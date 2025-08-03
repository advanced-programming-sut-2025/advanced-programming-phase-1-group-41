package com.CEliconValley.server.controller;

import com.CEliconValley.common.HandshakeData;
import com.CEliconValley.common.OnlineData;
import com.CEliconValley.common.UserData;
import com.CEliconValley.common.messages.*;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.*;
import com.google.gson.Gson;
import org.java_websocket.WebSocket;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class ServerAuthentication {
    public static Message handleLogin(LoginCred creds, WebSocket conn) throws NoSuchAlgorithmException {
        String username = creds.username;
        String password = creds.password;
        boolean stayLoggedIn = false;
        if (username.isEmpty() || password.isEmpty()) {
            return new ErrorMessage("login_request","Please enter username and password.");
        }
        if(!AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("login_request","Username does not exist!");
        }
        User user = Finder.getUserByUsername(username);
        if(App.getServer().getOnlineConnections().containsValue(user)){
            System.out.println("user "+username+" is already online.");
            return new ErrorMessage("login_request","Username is already online.");
        }
        String passHash = getHash(password);
        assert user != null;
        if(!passHash.equals(user.getPassword())){
            return new ErrorMessage("login_request","Password does not match!");
        }
        login(user, stayLoggedIn, conn);
        GameMessage<HandshakeData> msg = new GameMessage<>("handshake-data",
            new HandshakeData(null, App.lobbies, App.onlinePlayers));
        String json = new Gson().toJson(msg);
        conn.send(json);
        return new SuccessMessage("login_request",new UserData(user).toJson());
    }
    public static Message showForgotPassword(String username) {
        if(username.isEmpty()){
            return new ErrorMessage("forgotpass_request","Please fill username field.");
        }
        if(!AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("forgotpass_request","Username does not exist!");
        }
        return new SuccessMessage("forgotpass_request",
            new UserData(Finder.getUserByUsername(username)).toJson());
    }
    public static Message forgotPass(ForgotpassCred cred) {
        String username = cred.username;
        String answer = cred.answer;
        String newPass = cred.newPassword;
        if (username.isEmpty() || answer == null || answer.isEmpty() ||
            newPass == null || newPass.isEmpty()) {
            return new ErrorMessage("fp_request","Please fill all fields.");
        }
        User user = Finder.getUserByUsername(username);
        assert user != null;
        if(!answer.equals(user.getAnswer())){
            return new ErrorMessage("fp_request","Wrong answer.");
        }
        Result validation = AuthenticationValidator.passwordValidation(newPass);
        if(!validation.success()){
            return new ErrorMessage("fp_request",validation.message());
        }
        try{
            user.setPassword(getHash(newPass));
        } catch (Exception e){
            e.printStackTrace();
        }
        UserDB.saveUser(user);
        return new SuccessMessage("fp_request",newPass);
    }

    public static Message handlePreReg(PreRegisterCred cred) {
        String username = cred.username;
        String password = cred.password;
        String confirm = cred.confirm;
        String nickname = cred.nickname;
        String email = cred.email;
        if(AuthenticationValidator.usernameExists(username)){
            return new ErrorMessage("prereg_request","Username already exists!");
        }
        if(!username.matches("^[a-zA-Z0-9-]{1,8}$")){
            return new ErrorMessage("prereg_request","Invalid username format!");
        }
        if(!email.matches("^[a-zA-Z0-9][a-zA-Z0-9_-]*\\.?[a-zA-Z0-9_-]*[a-zA-Z0-9]@[a-zA-Z0-9-]+(\\.[a-zA-Z]{2,})+$")){
            return new ErrorMessage("prereg_request","Invalid email format!");
        }
        Result result = AuthenticationValidator.passwordValidation(password);
        if(!result.success()){
            return new ErrorMessage("prereg_request",result.message());
        }
        if (!password.equals(confirm)) {
            return new ErrorMessage("prereg_request","Passwords do not match!");
        }
        return new SuccessMessage("prereg_request","success");
    }

    public static Message register(RegisterCred cred) throws NoSuchAlgorithmException {
        String username = cred.username;
        String password = cred.password;
        String email = cred.email;
        String nickname = cred.nickname;
        Gender gender = cred.gender;
        String question = cred.question;
        String answer = cred.answer;
        password = getHash(password);
        User user = new User(username, password, email, nickname, gender, question, answer);
        UserDB.saveUser(user);
        App.addUser(user);
        return new SuccessMessage("register_request",new UserData(user).toJson());
    }

    private static void login(User user, boolean stayLoggedIn, WebSocket conn){
        user.setStayLoggedIn(stayLoggedIn);
        App.putOnlinePlayer(new OnlineData(user.getUsername(), false));
        App.getServer().getOnlineConnections().put(conn, user);
    }
    public static String getHash(String pass) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hashBytes = digest.digest(pass.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(hashBytes);
    }
}
