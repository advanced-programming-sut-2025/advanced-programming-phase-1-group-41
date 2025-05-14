package org.example.controllers;

import org.example.models.*;
import org.example.models.buildings.GreenHouse.Greenhouse;
import org.example.models.foragings.*;
import org.example.models.foragings.Nature.Grass;
import org.example.models.foragings.Nature.Tree;
import org.example.models.foragings.Nature.TreeType;
import org.example.models.items.Inventory;
import org.example.models.items.Slot;
import org.example.models.locations.Farm;

import java.util.*;
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
