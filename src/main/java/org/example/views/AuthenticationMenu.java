package org.example.views;

import org.example.controllers.authentication.AuthenticationController;
import org.example.models.App;
import org.example.models.Menu;
import org.example.models.Result;
import org.example.views.commands.AuthenticationCommands;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthenticationMenu implements AppMenu {
    AuthenticationController authenticationController = new AuthenticationController();
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if((matcher = AuthenticationCommands.MenuEnter.getMatcher(command)) != null) {

        } else if(AuthenticationCommands.MenuExit.getMatcher(command) != null) {
            App.setMenu(Menu.Exit);
            System.out.println("Thanks for playing :)");
        } else if(AuthenticationCommands.ShowCurrentMenu.getMatcher(command) != null) {
            System.out.println("Authentication Menu");
        } else if((matcher = AuthenticationCommands.Register.getMatcher(command)) != null) {
            Result result = authenticationController.registerInput(matcher, scanner);
            System.out.println(result);
        } else if((matcher = AuthenticationCommands.Login.getMatcher(command)) != null) {
            Result result = authenticationController.loginInput(matcher);
            System.out.println(result);
        } else if((matcher = AuthenticationCommands.ForgetPassword.getMatcher(command)) != null) {
            System.out.println(authenticationController.forgotPassword(matcher, scanner));
        } else {
            System.out.println("Invalid command!");
        }
    }
}
