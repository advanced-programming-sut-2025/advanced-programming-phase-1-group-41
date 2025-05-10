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
        else if((matcher=GameMainCommands.NewGame.getMatcher(input))!=null){
            handleNewGame(input, matcher, scanner);
        }else if((matcher=GameMainCommands.ExitGame.getMatcher(input))!=null){
            System.out.println(controller.exitGame(matcher));
        }else if((matcher=GameMainCommands.DeleteGame.getMatcher(input))!=null){
            System.out.println(controller.deleteGame(matcher, scanner));
        }else if((matcher=GameMainCommands.NextTurn.getMatcher(input))!=null){
            System.out.println(controller.nextTurn(matcher));
        }else if(TimeLineView.check(input)){

        }

    }
    private void handleNewGame(String input, Matcher matcher, Scanner scanner){
        HashSet<Integer> pickedFarms = new HashSet<>();
        Result result=controller.newGame(matcher);
        System.out.println(result);
        for(int i=0;i<4;i++) {
            System.out.println("choosing for user: "+App.getGame().getPlayers().get(i));
            input = scanner.nextLine();
            while (GameMainCommands.GameMap.getMatcher(input) == null) {
                System.out.println("Invalid command");
                input = scanner.nextLine();
            }
            matcher = GameMainCommands.GameMap.getMatcher(input);
            boolean passTurn = false;
            while(!passTurn) {
                result = controller.selectMap(matcher,pickedFarms);
                System.out.println(result);
                if(result.success()){
                    passTurn = true;
                }
            }
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
