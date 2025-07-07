package com.CEliconValley.views;

import com.CEliconValley.controllers.CheckerController;
import com.CEliconValley.controllers.authentication.AuthenticationController;
import com.CEliconValley.models.Result;
import com.CEliconValley.views.commands.AuthenticationCommands;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;
import java.util.regex.Matcher;

public class AuthenticationMenu implements AppMenu {
    AuthenticationController authenticationController = new AuthenticationController();
    @Override
    public void check(Scanner scanner) throws NoSuchAlgorithmException {
        String command = scanner.nextLine();
        Matcher matcher = null;

        if(CheckerController.checkCommand(command)) {

        }
        else if((matcher = AuthenticationCommands.Register.getMatcher(command)) != null) {
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
