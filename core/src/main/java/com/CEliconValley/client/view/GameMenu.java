package com.CEliconValley.client.view;

import java.util.Scanner;
import java.util.regex.Matcher;

import com.CEliconValley.client.controller.CheatCodeController;
import com.CEliconValley.controllers.CheckerController;
import com.CEliconValley.controllers.GameMenuController;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Result;
import com.CEliconValley.views.commands.gameCommands.GameMainCommands;
import com.CEliconValley.views.subGames.*;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;

public class GameMenu implements Screen {
    GameMenuController controller = new GameMenuController();
    public void check(String input, String playerName) {
        Matcher matcher = null;
//        if(CheckerController.checkCommand(input)) {
//
//        }
        if((matcher=GameMainCommands.ExitGame.getMatcher(input))!=null){
            System.out.println(controller.exitGame(matcher));
        }else if((matcher=GameMainCommands.DeleteGame.getMatcher(input))!=null){
            System.out.println(controller.deleteGame(matcher));
        }else if((matcher=GameMainCommands.NextTurn.getMatcher(input))!=null){
            System.out.println(controller.nextTurn(matcher));
        } else if((matcher = GameMainCommands.StartTrade.getMatcher(input))!=null){
            System.out.println(controller.startTrade(matcher));
        }
//        if(App.getGame().getCurrentPlayer().getEnergy() <= 0){//todo sepehr(exit game error mikhore)
//            System.out.println(App.getGame().getCurrentPlayer().getEnergy());
//            System.out.println("you've passed out.. either exit or pass the turn");
//        }
//        else if(App.getGame().getRoundEnergy() > 50){
//            System.out.println("you've used too much energy.. either exit or pass the turn");
//        }
        else{
            if(CheatCodeController.cheatCodeHandler(input, playerName)){
                System.out.println("received a cheat code command for "+playerName);
                System.out.println("    "+input);
            }else if(TimeLineView.check(input)){

            }else if(WeatherView.check(input)){

            }else if(MapView.check(input)){

            }else if(PlayerView.check(input, playerName)){

            }else if(ToolView.check(input, playerName)){

            }else if(FarmingView.check(input, playerName)){

            }else if(CookingView.check(input, playerName)){

            }else if(AnimalView.check(input, playerName)){

            }else if(CraftingView.check(input, playerName)){

            }else if(FriendshipView.check(input)){

            }else if(MarketplaceView.check(input, playerName)){

            }else if(NPCView.check(input)){

            }
            else{
                System.out.println(new Result(false,"Invalid command"));
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

}
