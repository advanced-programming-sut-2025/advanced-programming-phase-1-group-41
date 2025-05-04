package org.example.models;

import org.example.views.*;

import java.util.Scanner;

public enum Menu {
    Authentication(new AuthenticationMenu()),
    Profile(new ProfileMenu()),
    Main(new MainMenu()),
    Exit(new ExitMenu()),
    Game(new GameMenu()),
    Trade(new TradeMenu()),
    ;


    public final AppMenu menu;

    public AppMenu getMenu() {
        return menu;
    }

    Menu(AppMenu menu) {
        this.menu = menu;
    }
}
