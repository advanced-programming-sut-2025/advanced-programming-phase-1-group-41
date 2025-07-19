package com.CEliconValley.controllers;

import com.CEliconValley.Main;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;
import com.CEliconValley.views.MainMenuView;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuController {

    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setupListeners() {
        view.getStartGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Game);
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });

        view.getProfileButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Profile);
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });

        view.getLogoutButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Authentication);
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });
    }
}
