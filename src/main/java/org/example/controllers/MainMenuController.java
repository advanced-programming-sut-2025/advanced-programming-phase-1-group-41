package org.example.controllers;

import org.example.models.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;

public class MainMenuController {
    public void logout(){
        App.setCurrentUser(null);
        App.setMenu(Menu.Authentication);
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



        // TODO check if needed to be less than 4 players

        user1 = App.getCurrentUser();
        user2 = Finder.getUserByUsername(username1);
        user3 = Finder.getUserByUsername(username2);
        user4 = Finder.getUserByUsername(username3);


        Player player1 = new Player(user1);
        Player player2 = new Player(user2);
        Player player3 = new Player(user3);
        Player player4 = new Player(user4);


        if(user1 == null || user2 == null || user3 == null || user4 == null){
            return new Result(false,"a username doesn't exist");
        }

        HashSet<User> checker = new HashSet<>();
        checker.add(user1);
        checker.add(user2);
        checker.add(user3);
        checker.add(user4);
        if(checker.size()!=4){
            return new Result(false,"usernames are not distinct");
        }

        if(user2.getCurrentGame()!=null||
                user3.getCurrentGame()!=null||
                user4.getCurrentGame()!=null
        ){
            return new Result(false,"a user is already in a game");
        }

        ArrayList<Player> players = new ArrayList<>(Arrays.asList(player1, player2, player3, player4));


        // TODO save the game
        App.setGame(new Game(players, player1));
        App.getGame().setCurrentPlayer(player1);
        return new Result(true,"Game created successfully");

    }
    public Result selectMap(Matcher matcher, HashSet<Integer> pickedFarms){

        String numberRaw = matcher.group("mapNumber");
        int mapNumber = Integer.parseInt(numberRaw);
        if(!numberRaw.matches("\\d+")||mapNumber<=0||mapNumber>4){
            return new Result(false,"Invalid number");
        }
        else if(pickedFarms.contains(mapNumber)){
            return new Result(false,"Invalid number");
        }
        pickedFarms.add(mapNumber);
//        App.getGame().getCurrentPlayer().setFarm(new Farm(mapNumber));
        App.getGame().passTurn();
        return new Result(true,"Selected"+matcher.group("mapNumber"));
    }

    public Result loadGame(Matcher matcher){ // TODO
        return null ;
    }

}
