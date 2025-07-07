package com.CEliconValley.views;

import com.CEliconValley.controllers.ProfileMenuController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;
import com.CEliconValley.views.commands.ProfileCommands;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProfileMenu implements AppMenu {
    ProfileMenuController profileMenuController = new ProfileMenuController();
    @Override
    public void check(Scanner scanner) throws NoSuchAlgorithmException {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = ProfileCommands.MenuEnter.getMatcher(command)) != null) {
            System.out.println(profileMenuController.enterMenu(matcher));
        }
        else if(ProfileCommands.MenuExit.getMatcher(command) != null) {
            App.setMenu(Menu.Main);
            System.out.println("Redirecting to Main Menu...");
        }
        else if(ProfileCommands.ShowCurrentMenu.getMatcher(command) != null) {
            System.out.println("Profile Menu");
        }
        else if((matcher = ProfileCommands.ChangeUsername.getMatcher(command)) != null) {
            System.out.println(profileMenuController.changeUsername(matcher));
        }
        else if((matcher = ProfileCommands.ChangeNickname.getMatcher(command)) != null) {
            System.out.println(profileMenuController.changeNickname(matcher));
        }
        else if((matcher = ProfileCommands.ChangeEmail.getMatcher(command)) != null) {
            System.out.println(profileMenuController.changeEmail(matcher));
        }
        else if((matcher = ProfileCommands.ChangePassword.getMatcher(command)) != null) {
            System.out.println(profileMenuController.changePassword(matcher));
        }
        else if( ProfileCommands.UserInfo.getMatcher(command) != null) {
            System.out.println(profileMenuController.userInfo());
        }
        else {
            System.out.println("Invalid command!");
        }
    }
}
