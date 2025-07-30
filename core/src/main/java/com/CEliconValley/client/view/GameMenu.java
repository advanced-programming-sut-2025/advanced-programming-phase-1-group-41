package com.CEliconValley.client.view;

import java.util.Scanner;
import java.util.regex.Matcher;

import com.CEliconValley.controllers.CheckerController;
import com.CEliconValley.controllers.GameMenuController;
import com.CEliconValley.models.App;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;
import com.CEliconValley.views.subGames.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

public class GameMenu implements AppMenu, Screen {
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
        } else if((matcher = GameMainCommands.StartTrade.getMatcher(input))!=null){
            System.out.println(controller.startTrade(matcher));
        }
        if(App.getGame().getCurrentPlayer().getEnergy() <= 0){//todo sepehr(exit game error mikhore)
            System.out.println(App.getGame().getCurrentPlayer().getEnergy());
            System.out.println("you've passed out.. either exit or pass the turn");
        }else if(App.getGame().getRoundEnergy() > 50){
            System.out.println("you've used too much energy.. either exit or pass the turn");
        }
        else{
            if(TimeLineView.check(input)){

            }else if(WeatherView.check(input)){

            }else if(MapView.check(input)){

            }else if(PlayerView.check(input)){

            }else if(ToolView.check(input)){

            }else if(FarmingView.check(input)){

            }else if(CookingView.check(input)){

            }else if(AnimalView.check(input)){

            }else if(CraftingView.check(input)){

            }else if(FriendshipView.check(input)){

            }else if(MarketplaceView.check(input)){

            }else if(NPCView.check(input)){

            }
            else{
//                System.out.println("Invalid command");
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
