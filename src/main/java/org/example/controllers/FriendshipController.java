package org.example.controllers;

import org.example.models.*;
import java.util.regex.Matcher;

public class FriendshipController {
    public Result friendshipsList(Matcher matcher) {
        StringBuilder result = new StringBuilder();
        result.append("Friendships:\n");
        for(Friendship friendship : App.getGame().getCurrentPlayer().getFriendships()){
            result.append(friendship.showResult(App.getGame().getCurrentPlayer()));
        }
        result.delete(result.length() - 1, result.length());
        return new Result(true, result.toString());
    }
}
