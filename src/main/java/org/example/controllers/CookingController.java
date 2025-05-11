package org.example.controllers;

import org.example.models.App;
import org.example.models.Result;
import org.example.models.items.CookingRecipe;

import java.util.regex.Matcher;

public class CookingController {
    public Result pickFromRef(Matcher matcher) {return null;}

    public Result putInRef(Matcher matcher){return null;}

    public Result showRef(Matcher matcher){return null;}

    public Result learnRecipe(Matcher matcher) {return null;}

    public Result prepareFood(Matcher matcher) {return null;}

    public Result showRecepies(Matcher matcher) {
        for (CookingRecipe recipe : App.getGame().getCurrentPlayer().getRecipes()) {
            System.out.println(recipe.toString());
        }
        return new Result(true,"that was all..");
    }

}
