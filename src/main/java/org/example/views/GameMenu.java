package org.example.views;

import java.util.Scanner;
import java.util.regex.Matcher;

import org.example.controllers.GameMenuController;
import org.example.models.App;
import org.example.models.Result;
import org.example.views.commands.gameCommands.GameMainCommands;

public class GameMenu implements AppMenu {
    GameMenuController controller = new GameMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        if((matcher=GameMainCommands.NewGame.getMatcher(input))!=null){
            Result result=controller.newGame(matcher);
            System.out.println(result);
            input = scanner.nextLine();
            for(int i=0;i<4;i++) {
                while (GameMainCommands.GameMap.getMatcher(input) == null) {
                    System.out.println("Invalid command");
                }
                boolean passTurn = false;
                while(!passTurn) {
                    result = controller.selectMap(matcher);
                    System.out.println(result);
                    if(result.success()){
                        passTurn = true;
                    }
                }
            }


        }

    }
}
