package org.example.views;

import org.example.controllers.MainMenuController;
import org.example.models.App;
import org.example.models.Menu;
import org.example.views.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    MainMenuController mainMenuController = new MainMenuController();
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = MainMenuCommands.MenuEnter.getMatcher(command)) != null) {
            System.out.println(mainMenuController.enterMenu(matcher));
        }
        else if(MainMenuCommands.MenuExit.getMatcher(command) != null) {
            App.setMenu(Menu.Authentication);
            System.out.println("Redirecting to Authentication Menu...");
        }
        else if(MainMenuCommands.ShowCurrentMenu.getMatcher(command) != null) {
            System.out.println("Main Menu");
        }
        else if(MainMenuCommands.UserLogout.getMatcher(command) != null) {
            mainMenuController.logout();
            System.out.println("Logged out successfully.");
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}
