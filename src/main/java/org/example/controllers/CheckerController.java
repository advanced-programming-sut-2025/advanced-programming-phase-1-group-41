package org.example.controllers;

import org.example.models.App;
import org.example.models.Menu;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.example.models.Menu.goToLastMenu;
import static org.example.models.Menu.goToMenu;

public class CheckerController {
    public static boolean checkCommand(String input) {
        String currentMenuName = App.getMenu().getMenuName();

        if (input.matches("^menu exit$")) {
            System.out.println("Exiting the menu .");
            App.setMenu(Menu.goToLastMenu(currentMenuName));
        } else if (input.matches("^show current menu$")) {
            System.out.println("Current menu is "+currentMenuName);

        } else if (input.matches("^menu enter .*$")) {

            Pattern pattern = Pattern.compile("^menu enter (\\S+)$");
            Matcher matcher = pattern.matcher(input);

            if (matcher.find()) {
                String newMenuName = matcher.group(1);
                Menu next = goToMenu(newMenuName);
                if (next == null) {
                    StringBuilder message = new StringBuilder();
                    message.append("options:\n");
                    for (Menu value : Menu.values()) {
                        message.append(value.getMenuName()).append("\n");
                    }
                }else{
                    System.out.println("Entering menu: " + newMenuName);
                    App.setMenu(next);
                }
            }else{
                System.out.println("Empty menu");
            }
        } else {
            return false;
        }
        return true;
    }

}