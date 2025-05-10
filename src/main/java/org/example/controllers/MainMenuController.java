package org.example.controllers;

import org.example.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;

public class MainMenuController {
    public void logout(){
        App.setCurrentUser(null);
        App.setMenu(Menu.Authentication);
    }
    public Result enterMenu(Matcher matcher){
        if(!matcher.matches()) {
            return new Result(false, "Invalid command!");
        }
        String menuName = matcher.group("menuName");
        if(menuName.equalsIgnoreCase("Profile")){
            App.setMenu(Menu.Profile);
            return new Result(true, "Redirecting to Profile Menu...");
        } else if(menuName.equalsIgnoreCase("Authentication") || menuName.equalsIgnoreCase("Auth")){
            App.setMenu(Menu.Authentication);
            return new Result(true, "Redirecting to Authentication Menu...");
        } else if(menuName.equalsIgnoreCase("Game")){
            App.setMenu(Menu.Game);
        }
        System.out.println("here");
        return new Result(false, "Invalid menu name!\nMenu options:\nProfile\nAuthentication\nGame");
    }
    public Result newGame(Matcher matcher){
        if(matcher.group("flag")==null){
            return new Result(false,"Empty flag");
        }
        String username1 = matcher.group(2);
        String username2 = matcher.group(3);
        String username3 = matcher.group(4);
        if(username3 == null){
            return new Result(false,"Fewer than 3 players");
        }
        username1 = username1.trim();
        username2 = username2.trim();
        username3 = username3.trim();
        if(matcher.group("trash")!=null&&!matcher.group("trash").trim().isEmpty()){
            return new Result(false,matcher.group("More than 3 players"));
        }
        User user1 = null, user2 = null, user3 = null, user4 = null;
        if(Finder.getUserByUsername(username1)==null||
                Finder.getUserByUsername(username2)==null||
                Finder.getUserByUsername(username3)==null
        ){
            return new Result(false,matcher.group("Invalid Username"));
        }

        // TODO check if needed to be less than 4 players
//        if(Finder.getGameByUsername(matcher.group(username1))!=null||
//           Finder.getGameByUsername(matcher.group(username2))!=null||
//           Finder.getGameByUsername(matcher.group(username3))!=null){
//            return new Result(false,matcher.group("Username already in use"));
//        }
        user1 = App.getCurrentUser();
        user2 = Finder.getUserByUsername(username1);
        user3 = Finder.getUserByUsername(username2);
        user4 = Finder.getUserByUsername(username3);
        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        Player player3 = new Player(user3);
        Player player4 = new Player(user4);

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));


        // TODO save the game
        App.setGame(new Game(players, player1));
        App.getGame().setCurrentPlayer(player1);
        return new Result(true,"Game created successfully");

    }
    public Result selectMap(Matcher matcher){
        String numberRaw = matcher.group("mapNumber");
        int mapNumber = Integer.parseInt(numberRaw);
        if(!numberRaw.matches("\\d+")||mapNumber<=0||mapNumber>4){
            return new Result(false,matcher.group("Invalid number"));
        }

//        App.getGame().getCurrentPlayer().setFarm(new Farm(mapNumber));
        App.getGame().passTurn();
        return new Result(true,"Selected"+matcher.group("mapNumber"));
    }

    public Result loadGame(Matcher matcher){ // TODO
        return null ;
    }

}
