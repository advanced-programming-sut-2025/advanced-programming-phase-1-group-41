package org.example.views;

import org.example.views.commands.ProfileCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = ProfileCommands.MenuEnter.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.MenuExit.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.ShowCurrentMenu.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.ChangeUsername.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.ChangeNickname.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.ChangeEmail.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.ChangePassword.getMatcher(command)) != null) {}
        else if((matcher = ProfileCommands.UserInfo.getMatcher(command)) != null) {}
        else {}
    }
}
