package com.CEliconValley.controllers;

import com.CEliconValley.Main;
import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.GameData;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.client.view.screen.FarmScreen;
import com.CEliconValley.client.view.Lobby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.google.gson.Gson;

public class LobbyController {

    private Lobby view;

    public void setView(Lobby view) {
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
                AppClient.setMenu(Menu.Game);
                Menu.Game.resetMenu();
                App.setGame(new Game(view.getPlayers(),new Player(App.getCurrentUser())));
//                Main.getMain().setScreen(AppClient.getMenu().getScreen());
                ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new FarmScreen(new Farm(1),view.getPlayers().get(0)));
                Gdx.app.postRunnable(() -> {
                    Timer.schedule(new Timer.Task() {
                        @Override
                        public void run() {
                            if(App.getGame() == null) return;
                            GameMessage<GameData> gameMessage = new GameMessage<>("gamedata", new GameData(App.getGame()));
                            Gson gson = new Gson();
                            App.getServer().broadcast(gson.toJson(gameMessage));
                        }
                    }, 1, 1);
                });
            }
        });

        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                AppClient.setMenu(Menu.Main);
                Menu.Main.resetMenu();
                Main.getMain().setScreen(AppClient.getMenu().getScreen());
            }
        });
    }

}
