package org.example.controllers;

import org.example.models.App;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.models.Menu.goToLastMenu;
import static org.example.models.Menu.goToMenu;

public class CheckerController {
    public static void checkCommand(String input) {
        String currentMenuName = App.getMenu().getMenuName();

        if (input.matches("^menu exit$")) {
            System.out.println("Exiting the menu .");
            App.setMenu(goToLastMenu(currentMenuName));

        } else if (input.matches("^show current menu$")) {
            System.out.println("Current menu is "+currentMenuName);

        } else if (input.matches("^menu enter .*$")) {

            Pattern pattern = Pattern.compile("^menu enter (\\S+)$");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String newMenuName = matcher.group(1);
                System.out.println("Entering menu: " + newMenuName);
                App.setMenu(goToMenu(newMenuName));
            }else{
                System.out.println("Empty menu");
            }
        } else {
            System.out.println("Invalid command");
        }
    }

}