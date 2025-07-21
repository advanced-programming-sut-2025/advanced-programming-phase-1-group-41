package com.CEliconValley.views;

import com.CEliconValley.controllers.CheckerController;
import com.CEliconValley.controllers.TradeMenuController;
import com.CEliconValley.views.commands.TradeCommands;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu, Screen {
    TradeMenuController controller = new TradeMenuController();
//    @Override
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

    @Override
    public void show() {

    }

    @Override
    public void render(float v) {

    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void setMessage(String message, Color color) {

    }
}
