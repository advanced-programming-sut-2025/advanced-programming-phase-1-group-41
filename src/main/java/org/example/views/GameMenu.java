package org.example.views;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;

import org.example.controllers.CheckerController;
import org.example.controllers.GameMenuController;
import org.example.models.App;
import org.example.models.Menu;
import org.example.models.Result;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.subGames.TimeLineView;

public class GameMenu implements AppMenu {
    GameMenuController controller = new GameMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;
        if(CheckerController.checkCommand(input)) {

        }
        else if((matcher=GameMainCommands.ExitGame.getMatcher(input))!=null){
            System.out.println(controller.exitGame(matcher));
        }else if((matcher=GameMainCommands.DeleteGame.getMatcher(input))!=null){
            System.out.println(controller.deleteGame(matcher, scanner));
        }else if((matcher=GameMainCommands.NextTurn.getMatcher(input))!=null){
            System.out.println(controller.nextTurn(matcher));
        }else if(TimeLineView.check(input)){

        }

    }

    public static boolean handleDeleteGame(Scanner scanner){
        boolean answer = true;
        for(int i=0;i<4;i++) {
            System.out.println("answer: "+answer);
            System.out.println("what's your opinion "+App.getGame().getPlayers().get(i)+"?");
            while(true){
                String input = scanner.nextLine();
                if(input.matches("\\s*[+-]\\s*")){
                    input = input.trim();
                    switch(input){
                        case "-" -> {
                            answer = false;
                        }
                    }
                    break;
                }else{
                    System.out.println("Invalid input");
                }
            }
        }
        return answer;
    }
}
