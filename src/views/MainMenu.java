package views;

import views.commands.MainMenuCommands;

import java.util.Scanner;

public class MainMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        if(MainMenuCommands.MenuEnter.getMatcher(command) != null) {}
        else if(MainMenuCommands.MenuExit.getMatcher(command) != null) {}
        else if(MainMenuCommands.ShowCurrentMenu.getMatcher(command) != null) {}
        else if(MainMenuCommands.UserLogout.getMatcher(command) != null) {}
        else {}
    }
}
