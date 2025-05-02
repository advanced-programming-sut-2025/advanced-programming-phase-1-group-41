package views;

import views.commands.AuthenticationCommands;

import java.util.Scanner;

public class AuthenticationMenu implements AppMenu {
    @Override
    public void check(Scanner scanner) {
        String command = scanner.nextLine();
        if(AuthenticationCommands.MenuEnter.getMatcher(command) != null) {}
        else if(AuthenticationCommands.MenuExit.getMatcher(command) != null) {}
        else if(AuthenticationCommands.ShowCurrentMenu.getMatcher(command) != null) {}
        else if(AuthenticationCommands.Register.getMatcher(command) != null) {}
        else if (AuthenticationCommands.PickQuestion.getMatcher(command) != null) {}
        else if(AuthenticationCommands.Login.getMatcher(command) != null) {}
        else if(AuthenticationCommands.ForgetPassword.getMatcher(command) != null) {}
        else if(AuthenticationCommands.Answer.getMatcher(command) != null) {}
        else {}
    }
}
