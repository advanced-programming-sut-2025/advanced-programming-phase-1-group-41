package com.CEliconValley.models;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.controller.*;
import com.CEliconValley.client.view.*;
import com.CEliconValley.views.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public enum Menu {
    Authentication(new AuthenticationMenuView(new AuthenticationMenuController()),"AuthenticationMenu"),
    Profile(new ProfileMenuView(new ProfileMenuController()),"ProfileMenu"),
    Main(new MainMenuView(new MainMenuController()),"MainMenu"),
    Exit(new ExitMenu(),"ExitMenu"),
    Game(new GameMenu(),"GameMenu"),
    Trade(new TradeMenu(),"TradeMenu"),
    Lobby(new LobbyScreen(new LobbyController()), "Lobby"),
    AvatarSelection(new AvatarSelectionView(new AvatarSelectionController()), "AvatarSelectionMenu"),
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

    public AppMenu getMenu() {
        return (AppMenu) menu;
    }

    public void resetMenu(){
        Gdx.app.postRunnable(() -> {
            try{
                AppClient.getMenu().getScreen().dispose();
                System.out.println(this.menuName + " disposed");
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            if(menu.equals(Menu.Authentication.menu)){
                Menu.Authentication.menu = new AuthenticationMenuView(new AuthenticationMenuController());
            } else if(menu.equals(Menu.Profile.menu)){
                Menu.Profile.menu = new ProfileMenuView(new ProfileMenuController());
            } else if(menu.equals(Menu.Main.menu)) {
                Menu.Main.menu = new MainMenuView(new MainMenuController());
            } if(menu.equals(Menu.Lobby.menu)){
                Menu.Lobby.menu = new LobbyScreen(new LobbyController());
            } if(menu.equals(Menu.AvatarSelection.menu)){
                Menu.AvatarSelection.menu = new AvatarSelectionView(new AvatarSelectionController());
            }
            if(menu == Menu.Main.menu || menu == Profile.menu || menu == Authentication.menu
            || menu == Lobby.menu || menu == AvatarSelection.menu){
                com.CEliconValley.Main.getMain().setScreen(AppClient.getMenu().getScreen());
            }
        });
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
