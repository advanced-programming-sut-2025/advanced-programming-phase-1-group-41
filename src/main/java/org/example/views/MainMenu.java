package org.example.views;

import org.example.views.commands.MainMenuCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = MainMenuCommands.MenuEnter.getMatcher(command)) != null) {

        }
        else if((matcher = MainMenuCommands.MenuExit.getMatcher(command)) != null) {

        }
        else if((matcher = MainMenuCommands.ShowCurrentMenu.getMatcher(command)) != null) {

        }
        else if((matcher = MainMenuCommands.UserLogout.getMatcher(command)) != null) {

        }
        else {

        }
    }
}
