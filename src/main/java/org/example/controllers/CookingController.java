package org.example.controllers;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.Result;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.items.CookingRecipe;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.regex.Matcher;

public class CookingController {


    private boolean inHome(){
        int x = App.getGame().getCurrentPlayer().getX();
        int y = App.getGame().getCurrentPlayer().getY();
        Cell cell = App.getGame().getCurrentPlayerFarm().getCell(x, y);
        if(cell == null) return false;
        if(cell.getObjectMap() instanceof Cottage){
            return true;
        }
        return false;
    }

    private Result pickFromRef(Item item) {
        // receive slot
        return null;
    }

    private Result putInRef(Item item){
        Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
        if(slot==null){
            return new Result(false,"Slot not found");
        }
        // get the home and ref

        return new Result(true,slot.getItem()+" "+slot.getQuantity());
    }

    public Result cookingRef(Matcher matcher) {
        if(!inHome()){
            return new Result(false, "You're not in a home");
        }
        String pickput = matcher.group("which");
        String itemName = matcher.group(2);
        Item item = Finder.parseItem(itemName);
        if(item == null){
            return new Result(false,"Item not found");
        }
        if(pickput.equals("put")){
            return this.putInRef(item);
        }else{
            return this.pickFromRef(item);
        }
    }
    public Result showRef(Matcher matcher){
        if(!inHome()){
            return new Result(false, "You're not in a home");
        }
        return null;
    }

    public Result learnRecipe(Matcher matcher) {
        if(!inHome()){
            return new Result(false, "You're not in a home");
        }
        return null;
    }

    public Result prepareFood(Matcher matcher) {
        if(!inHome()){
            return new Result(false, "You're not in a home");
        }
        return null;
    }

    public Result showRecepies(Matcher matcher) {
        if(!inHome()){
            return new Result(false, "You're not in a home");
        }
        for (CookingRecipe recipe : App.getGame().getCurrentPlayer().getRecipes()) {
            System.out.println(recipe.toString());
        }
        return new Result(true,"that was all..");
    }

}
