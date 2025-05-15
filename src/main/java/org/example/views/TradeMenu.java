package org.example.views;

import org.example.controllers.CheckerController;
import org.example.controllers.TradeMenuController;
import org.example.views.commands.TradeCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu{
    TradeMenuController controller = new TradeMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher;
        if(CheckerController.checkCommand(input)) {

        } else if(TradeCommands.Trade.getMatcher(input) != null){
            System.out.println("Enter only one Target!");
        } else if((matcher = TradeCommands.TradeToItem.getMatcher(input)) != null){
            System.out.println(controller.tradeToItem(matcher));
        } else if((matcher = TradeCommands.TradeToMoney.getMatcher(input)) != null){
            System.out.println(controller.tradeToMoney(matcher));
        } else if((matcher = TradeCommands.TradeList.getMatcher(input)) != null){
            System.out.println(controller.tradeList(matcher));
        } else if((matcher = TradeCommands.TradeHistory.getMatcher(input)) != null){
            System.out.println(controller.tradeHistory(matcher));
        } else if((matcher = TradeCommands.TradeResponse.getMatcher(input)) != null){
            System.out.println(controller.tradeResponse(matcher));
        }
    }
}
