package org.example.views;

import org.example.controllers.authentication.AuthenticationController;
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

        }
        else if((matcher = AuthenticationCommands.MenuExit.getMatcher(command)) != null) {

        }
        else if((matcher = AuthenticationCommands.ShowCurrentMenu.getMatcher(command)) != null) {

        }
        else if((matcher = AuthenticationCommands.Register.getMatcher(command)) != null) {
            Result result = authenticationController.registerInput(matcher);
            System.out.println(result);
        }
        else if ((matcher = AuthenticationCommands.PickQuestion.getMatcher(command)) != null) {

        }
        else if((matcher = AuthenticationCommands.Login.getMatcher(command)) != null) {

        }
        else if((matcher = AuthenticationCommands.ForgetPassword.getMatcher(command)) != null) {

        }
        else if((matcher = AuthenticationCommands.Answer.getMatcher(command)) != null) {

        }
        else {

        }
    }
}
