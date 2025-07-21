package com.CEliconValley.models;

import com.CEliconValley.controllers.MainMenuController;
import com.CEliconValley.controllers.ProfileMenuController;
import com.CEliconValley.controllers.authentication.AuthenticationMenuController;
import com.CEliconValley.views.*;
import com.badlogic.gdx.Screen;

public enum Menu {
    Authentication(new AuthenticationMenuView(new AuthenticationMenuController()),"AuthenticationMenu"),
    Profile(new ProfileMenuView(new ProfileMenuController()),"ProfileMenu"),
    Main(new MainMenuView(new MainMenuController()),"MainMenu"),
    Exit(new ExitMenu(),"ExitMenu"),
    Game(new GameMenu(),"GameMenu"),
    Trade(new TradeMenu(),"TradeMenu"),
    ;


    public Screen menu;
    private final String menuName;


    public Screen getScreen() {
        return menu;
    }

    public String getMenuName() {return menuName;}


    Menu(Screen menu, String menuName) {
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

    public void resetMenu(){
        if(menu == Menu.Authentication.menu){
            Menu.Authentication.menu = new AuthenticationMenuView(new AuthenticationMenuController());
        } else if(menu == Menu.Profile.menu){
            Menu.Profile.menu = new ProfileMenuView(new ProfileMenuController());
        } else if(menu == Menu.Main.menu) {
            Menu.Main.menu = new MainMenuView(new MainMenuController());
        }
    }
//    public static Menu goToLastMenu(String input) {
//        if(input.equals("AuthenticationMenu")) {
//            System.out.println("thanks for playing");
//        }
//         return switch (input){
//            case "AuthenticationMenu" -> Exit;
//
//            case "ProfileMenu", "GameMenu" -> Main;
//
//            case "MainMenu" -> Authentication;
//
//            case "TradeMenu" -> Game;
//
//            default -> throw new IllegalStateException("Unexpected value: " + input);
//        };
//    }
//    public static Menu goToMenu(String input) {
//        return switch (input){
//            case "AuthenticationMenu" -> Authentication;
//            case "ProfileMenu" -> Profile;
//            case "g" -> Game;
//            case "TradeMenu" -> Trade;
//            case "MainMenu" -> Main;
//            case "Exit" -> Exit;
//            default -> null;
//        };
//    }


}
