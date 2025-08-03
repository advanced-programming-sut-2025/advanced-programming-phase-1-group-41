package com.CEliconValley.client.controller;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.common.messages.LeaveLobbyCred;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.client.view.LobbyScreen;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;

public class LobbyController {

    private LobbyScreen view;

    public void setView(LobbyScreen view) {
        this.view = view;
    }

    public void setupListeners() {
//        view.getStartGameButton().addListener(new ClickListener() {
//            @Override
//            public void clicked(InputEvent event, float x, float y) {
//                App.setMenu(Menu.Game);
//                Menu.Game.resetMenu();
//                Main.getMain().setScreen(AppClient.getMenu().getScreen());
//            }
//        });

        view.getStartGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMessage<Lobby> msg = new GameMessage<Lobby>("new-game",view.lobby);
                Gson gson = new Gson();
                AppClient.getClient().send(gson.toJson(msg));
            }
        });

        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GameMessage<LeaveLobbyCred> msg = new GameMessage<>("leave-lobby",
                    new LeaveLobbyCred(view.lobby.getLobbyID(), AppClient.getUserData().getUsername()));
                Gson gson = new Gson();
                AppClient.getClient().send(gson.toJson(msg));
                System.out.println("C "+gson.toJson(msg));
            }
        });
    }

}
