package com.CEliconValley.controllers;

import com.CEliconValley.Main;
import com.CEliconValley.models.*;
import com.CEliconValley.views.MainMenuView;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.morphia.Datastore;
import dev.morphia.Morphia;

public class MainMenuController {

    private MainMenuView view;

    public void setView(MainMenuView view) {
        this.view = view;
    }

    public void setupListeners() {
        view.getStartGameButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Lobby);
                Menu.Lobby.resetMenu();
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });

        view.getProfileButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setMenu(Menu.Profile);
                Menu.Profile.resetMenu();
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });

        view.getLogoutButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                App.setCurrentUser(null);
                App.setMenu(Menu.Authentication);
                Menu.Authentication.resetMenu();
                Main.getMain().setScreen(App.getMenu().getScreen());
            }
        });
    }
    public void savePlayer(Player player) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        Datastore datastore = Morphia.createDatastore(mongoClient, "ProjectDB");

        datastore.getMapper().map(TimeLine.class);
        datastore.getMapper().map(Player.class);
        datastore.getMapper().map(Game.class);
        datastore.getMapper().map(User.class);

        datastore.save(player);
    }
}
