package com.CEliconValley.controllers;

import com.CEliconValley.Main;
import com.CEliconValley.models.*;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.views.screen.FarmScreen;
import com.CEliconValley.views.Lobby;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

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
//                Main.getMain().setScreen(App.getMenu().getScreen());
//            }
//        });

        view.getStartGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Game);
                Menu.Game.resetMenu();
                App.setGame(new Game(view.getPlayers(),new Player(App.getCurrentUser())));
//                Main.getMain().setScreen(App.getMenu().getScreen());
                ((com.badlogic.gdx.Game) Gdx.app.getApplicationListener()).setScreen(new FarmScreen(new Farm(1),view.getPlayers().get(0)));

            }
        });

        view.getExitButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setCurrentUser(null);
                App.setMenu(Menu.Main);
                Menu.Main.resetMenu();
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });
    }

}
