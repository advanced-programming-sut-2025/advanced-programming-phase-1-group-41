package org.example.views;

import org.example.controllers.CheckerController;
import org.example.views.commands.TradeCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu{
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        if(CheckerController.checkCommand(input)) {

        } else if((matcher = TradeCommands.Trade.getMatcher(input)) != null){

        }
    }
}
