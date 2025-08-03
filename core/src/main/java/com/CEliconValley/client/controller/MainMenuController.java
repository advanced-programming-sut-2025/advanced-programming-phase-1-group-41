package com.CEliconValley.client.controller;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.JoinLobbyCred;
import com.CEliconValley.common.messages.MakeLobbyInfo;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.*;
import com.CEliconValley.client.view.MainMenuView;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

import java.util.regex.Matcher;

public class MainMenuController {

    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setupListeners() {
        view.getJoinLobbyButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showJoinLobbyForm();
            }
        });

        view.getNewLobbyButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                view.showNewLobbyForm();
            }
        });

        view.getProfileButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Menu.Profile.resetMenu();
                AppClient.setMenu(Menu.Profile);
                Main.getMain().setScreen(AppClient.getMenu().getScreen());
            }
        });

        view.getLogoutButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMessage<String> msg = new GameMessage<>("logout_request",
                    AppClient.getUserData().getUsername());
                String json = new Gson().toJson(msg);
                AppClient.getClient().send(json);
                AppClient.logout();
                Menu.Authentication.resetMenu();
                AppClient.setMenu(Menu.Authentication);
                Main.getMain().setScreen(AppClient.getMenu().getScreen());
            }
        });

    }

    public void handleJoinLobby() {
        String id = view.getLobbyIdField().getText();

        if(id == null || id.isEmpty()) {
            view.getJoinLobbyMessage().setText("Id field is empty!");
            return;
        }
        String username = AppClient.getUserData().getUsername();
        String password = view.getLobbyPasswordField().getText();
        GameMessage<JoinLobbyCred> msg = new GameMessage<>("join-lobby",
            new JoinLobbyCred(id, username, password));
        String json = new Gson().toJson(msg);
        AppClient.getClient().send(json);
    }

    public void handleNewLobby() {

        String name = view.getLobbyNameField().getText();
        if(name == null || name.isEmpty()) {
            view.getCreateLobbyMessage().setText("Name field is empty!");
            return;
        }

        boolean isVisible = view.getIsVisibleCheckBox().isChecked();
        boolean isPrivate = view.getIsPrivateCheckBox().isChecked();
        String password = null;
        Lobby lobby;
        if(isPrivate){
            password = view.getPasswordField().getText();
            if(password == null || password.isEmpty()) {
                view.getCreateLobbyMessage().setText("Password field is empty!");
                return;
            }
            assert AppClient.getUserData() != null;
//            lobby = new Lobby(name, password, AppClient.getUserData().getUsername(), isVisible);
        } else{
            assert AppClient.getUserData() != null;
//            lobby = new Lobby(name, AppClient.getUserData().getUsername(), isVisible);
        }
//        AppClient.getLobbies().add(lobby);

        GameMessage<MakeLobbyInfo> msg = new GameMessage<>("make-lobby",
            new MakeLobbyInfo(isPrivate, isVisible, name, password, AppClient.getUserData().getUsername()));
        String json = new Gson().toJson(msg);
        AppClient.getClient().send(json);

    }

    public void savePlayer(Player player) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        datastore.save(player);
    }


    public Result loadGameForReal(Matcher matcher){
//        User user = App.getCurrentUser();
//        if(user==null) return new Result(false, "you are guest dummy");
//        Game game = UserDB.loadGame(user.getUsername());
//        App.setGame(game);
//        AppClient.setMenu(Menu.Game);
//        return new Result(true,"Game loaded successfully");
        return null;
    }
}
