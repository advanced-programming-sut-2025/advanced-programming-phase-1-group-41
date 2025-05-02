package views;

import views.commands.ProfileCommands;

import java.util.Scanner;

public class ProfileMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        if(ProfileCommands.MenuEnter.getMatcher(command) != null) {}
        else if(ProfileCommands.MenuExit.getMatcher(command) != null) {}
        else if(ProfileCommands.ShowCurrentMenu.getMatcher(command) != null) {}
        else if(ProfileCommands.ChangeUsername.getMatcher(command) != null) {}
        else if(ProfileCommands.ChangeNickname.getMatcher(command) != null) {}
        else if(ProfileCommands.ChangeEmail.getMatcher(command) != null) {}
        else if(ProfileCommands.ChangePassword.getMatcher(command) != null) {}
        else if(ProfileCommands.UserInfo.getMatcher(command) != null) {}
        else {}
    }
}
