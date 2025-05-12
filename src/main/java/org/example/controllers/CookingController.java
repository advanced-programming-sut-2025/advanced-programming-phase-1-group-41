package org.example.controllers;

import org.example.models.App;
import org.example.models.Cell;
import org.example.models.Finder;
import org.example.models.Result;
import org.example.models.buildings.Building;
import org.example.models.buildings.Cottage;
import org.example.models.buildings.Refrigerator;
import org.example.models.items.CookingRecipe;
import org.example.models.items.Item;
import org.example.models.items.Slot;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CookingController {

    Refrigerator refrigerator;

    public CookingController() {
        for (Building building : App.getGame().getCurrentPlayerFarm().getBuildings()) {
            if(building instanceof Cottage) {
                refrigerator = (Refrigerator) ((Cottage) building).getRefrigerator();
            }
        }
        System.out.println("ref is : " + refrigerator);
    }


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
        Slot slot = refrigerator.getSlotByItem(item);
        if(slot == null){
            return new Result(false, "Slot not found");
        }
        App.getGame().getCurrentPlayer().getInventory().addToInventory(slot.getItem(),slot.getQuantity());
        refrigerator.removeFromRef(slot.getItem(),slot.getQuantity());
        return new Result(true, "removed from ref :D");
    }

    private Result putInRef(Item item){
        Slot slot = App.getGame().getCurrentPlayer().getInventory().getSlotByItem(item);
        if(slot==null){
            return new Result(false,"Slot not found");
        }
        refrigerator.addToRef(slot.getItem(), slot.getQuantity());
        App.getGame().getCurrentPlayer().getInventory().removeFromInventory(slot.getItem(), slot.getQuantity());

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
        for(Slot slot : refrigerator.slots){
            if(slot.getItem()==null) continue;
            if(slot.getQuantity()>0) {
                System.out.printf(slot.getQuantity() + " " + slot.getItem().getName());
            }
            if(slot.getQuantity()>1){
                System.out.printf("s\n");
            }
            else if(slot.getQuantity()==1){
                System.out.printf("\n");
            }
        }
        return new Result(true,":)");

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
