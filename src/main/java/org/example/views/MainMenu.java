package org.example.views;

import org.example.controllers.CheckerController;
import org.example.controllers.MainMenuController;
import org.example.models.*;
import org.example.models.locations.Farm;
import org.example.models.npc.npcCharacters.NPCBuilder;
import org.example.views.commands.MainMenuCommands;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.HashSet;
import java.util.Scanner;
import java.util.regex.Matcher;

public class MainMenu implements AppMenu {
    MainMenuController controller = new MainMenuController();
    @Override
    public void check(Scanner scanner) {
        String input = scanner.nextLine();
        Matcher matcher = null;

        if(CheckerController.checkCommand(input)) {

        }
        else if((matcher= MainMenuCommands.NewGame.getMatcher(input))!=null){
            handleNewGame(input, matcher, scanner);
        } else if(MainMenuCommands.LoadGame.getMatcher(input)!=null){
            Result result = controller.loadGame(MainMenuCommands.LoadGame.getMatcher(input));
            System.out.println(result);
        }
        else if(MainMenuCommands.UserLogout.getMatcher(input) != null) {
            controller.logout();
            System.out.println("Logged out successfully.");
        }
        else {
            System.out.println("Invalid command!");
        }
    }

    private void handleNewGame(String input, Matcher matcher, Scanner scanner){
        HashSet<Integer> pickedFarms = new HashSet<>();
        Result result=controller.newGame(matcher);
        if(!result.success()){
            System.out.println(result);
            return;
        }
        for(int i=0;i<4;i++) {
            System.out.println("choosing for user: "+App.getGame().getPlayers().get(i));
            input = scanner.nextLine();
            while (GameMainCommands.GameMap.getMatcher(input) == null) {
                System.out.println("Invalid command");
                input = scanner.nextLine();
            }
            boolean passTurn = false;
            while(!passTurn) {
                matcher = GameMainCommands.GameMap.getMatcher(input);
                result = controller.selectFarm(matcher,pickedFarms);
                System.out.println(result);
                if(result.success()){
                    passTurn = true;
                }else{
                    input = scanner.nextLine();
                }
            }
        }
        for(Player player : App.getGame().getPlayers()) {
            Farm farm = Finder.findFarmByPlayer(player);
            assert farm != null;
            player.setX(farm.getStartPoints().get(0).getX());
            player.setY(farm.getStartPoints().get(0).getY());
        }
        for(int i = 0; i < 3; i++) {
            Player player1 = App.getGame().getPlayers().get(i);
            Player player2 = App.getGame().getPlayers().get(i + 1);
            Friendship friendship = new Friendship(player1, player2);
            player1.addFriendship(friendship);
            player2.addFriendship(friendship);
            if(i <= 1){
                Player player11 = App.getGame().getPlayers().get(i);
                Player player22 = App.getGame().getPlayers().get(i + 2);
                Friendship friendship1 = new Friendship(player11, player22);
                player11.addFriendship(friendship1);
                player22.addFriendship(friendship1);
            } if(i == 0){
                Player player11 = App.getGame().getPlayers().get(0);
                Player player22 = App.getGame().getPlayers().get(3);
                Friendship friendship1 = new Friendship(player11, player22);
                player11.addFriendship(friendship1);
                player22.addFriendship(friendship1);
            }
        }
        new NPCBuilder();
        System.out.println("welcome to the game!");
        App.setMenu(Menu.Game);
    }
}
