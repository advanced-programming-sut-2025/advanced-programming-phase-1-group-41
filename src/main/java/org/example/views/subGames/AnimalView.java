package org.example.views.subGames;

import org.example.controllers.AnimalController;
import org.example.views.commands.gameCommands.AnimalCommands;
import org.example.views.commands.gameCommands.FriendShipCommands;
import org.example.views.commands.gameCommands.GameMainCommands;

import java.util.regex.Matcher;

public class AnimalView {
    static AnimalController controller =  new AnimalController();
    public static boolean check(String input){
        Matcher matcher;
        if((matcher = GameMainCommands.Build.getMatcher(input))!=null){
            System.out.println(controller.build(matcher));
        }else if((matcher = AnimalCommands.BuyAnimal.getMatcher(input))!=null){
            System.out.println(controller.buyAnimal(matcher));
        }else if((matcher = AnimalCommands.Pet.getMatcher(input))!=null){
            System.out.println(controller.pet(matcher));
        }else if((matcher = FriendShipCommands.CheatSetFriendship.getMatcher(input))!=null){
            System.out.println(controller.cheatSetFriendship(matcher));
        }else if((matcher = AnimalCommands.Animals.getMatcher(input))!=null){
            System.out.println(controller.animalsList(matcher));
        }else if((matcher = AnimalCommands.ShepherdAnimals.getMatcher(input))!=null){
            System.out.println(controller.shepherdAnimals(matcher));
        }else if((matcher = AnimalCommands.SellAnimal.getMatcher(input))!=null){
            System.out.println(controller.sellAnimal(matcher));
        }else if((matcher = AnimalCommands.FeedHay.getMatcher(input))!=null){
            System.out.println(controller.feedHay(matcher));
        }else if((matcher = AnimalCommands.CollectProduce.getMatcher(input))!=null){
            System.out.println(controller.collectProduct(matcher));
        }else if((matcher = AnimalCommands.Produces.getMatcher(input))!=null){
            System.out.println(controller.producesList(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
