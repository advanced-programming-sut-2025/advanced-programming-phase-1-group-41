package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.subgames.MarketplaceController;
import com.CEliconValley.views.commands.gameCommands.StoreCommands;

import java.util.regex.Matcher;

public class MarketplaceView {
    static MarketplaceController controller = new MarketplaceController();
    public static boolean check(String input){
        Matcher matcher;
        if ((matcher = StoreCommands.ShowAllProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllProducts(matcher));
        }else if ((matcher = StoreCommands.ShowAllAvailableProducts.getMatcher(input)) != null) {
            System.out.println(controller.showAllAvailableProducts(matcher));
        }else if ((matcher = StoreCommands.Purchase.getMatcher(input)) != null) {
            System.out.println(controller.purchaseProduct(matcher));
        }else if ((matcher = StoreCommands.Sell.getMatcher(input)) != null) {
            System.out.println(controller.sellProduct(matcher));
        }
        else if ((matcher = StoreCommands.CheatAddDollars.getMatcher(input)) != null) {
            System.out.println(controller.cheatAddMoney(matcher));
        }
        else{
            return false;
        }
        return true;
    }
}
