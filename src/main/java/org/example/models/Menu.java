package org.example.models;

import org.example.views.*;

import java.util.Scanner;

public enum Menu {
    Authentication(new AuthenticationMenu(),"AuthenticationMenu"),
    Profile(new ProfileMenu(),"ProfileMenu"),
    Main(new MainMenu(),"MainMenu"),
    Exit(new ExitMenu(),"ExitMenu"),
    Game(new GameMenu(),"GameMenu"),
    Trade(new TradeMenu(),"TradeMenu"),
    ;


    public final AppMenu menu;
    private final String menuName;


    public AppMenu getMenu() {
        return menu;
    }
    public String getMenuName() {return menuName;}


    Menu(AppMenu menu, String menuName) {
        this.menu = menu;
        this.menuName=menuName;
    }

    public static Menu findMenu(String menuName) {
        for (Menu menu : values()) {
            if (menu.getMenuName().equals(menuName)) {
                return menu;
            }
        }
        return null;
    }
    public static Menu goToLastMenu(String input) {
        if(input.equals("AuthenticationMenu")) {
            System.out.println("thanks for playing");
        }
         return switch (input){
            case "AuthenticationMenu" -> Exit;

            case "ProfileMenu", "GameMenu", "TradeMenu" -> Main;

            case "MainMenu" -> Authentication;

            default -> throw new IllegalStateException("Unexpected value: " + input);
        };
    }
    public static Menu goToMenu(String input) {
        return switch (input){
            case "AuthenticationMenu" -> Authentication;
            case "ProfileMenu" -> Profile;
            case "g" -> Game;
            case "TradeMenu" -> Trade;
            case "MainMenu" -> Main;
            case "Exit" -> Exit;
            default -> null;
        };
    }


}
