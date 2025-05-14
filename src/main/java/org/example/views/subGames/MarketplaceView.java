package org.example.views.subGames;

import org.example.controllers.FarmingController;
import org.example.controllers.MarketplaceController;
import org.example.models.App;
import org.example.models.Finder;
import org.example.models.tools.WateringCan;
import org.example.views.commands.gameCommands.FarmingCommands;
import org.example.views.commands.gameCommands.GameMainCommands;
import org.example.views.commands.gameCommands.StoreCommands;

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
