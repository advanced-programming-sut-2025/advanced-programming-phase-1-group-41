package com.CEliconValley.views;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.database.UserDB;
import com.CEliconValley.models.App;
import com.CEliconValley.models.Menu;
import com.CEliconValley.models.items.CookingRecipe;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppView {
    public void runApp() throws NoSuchAlgorithmException, InterruptedException {
        ArrayList<String> questions = new ArrayList<>();
        questions.add("What is your favorite color?");
        questions.add("What is your favorite food?");
        questions.add("What is your favorite sport?");
        questions.add("Where were you born?");
        questions.add("What is you father's name?");
        questions.add("What is you mother's name?");
        questions.add("What is the name of your first pet?");
        questions.add("What is name of the city you're living in?");
        questions.add("What is your best friend's name?");
        questions.add("What is your body count?");
        AppClient.setQuestions(questions);
        Scanner scanner = new Scanner(System.in);
        AppClient.setMenu(Menu.Authentication);
        CookingRecipe.updateRecipe();
        UserDB.connect();
        while(AppClient.getMenu() != Menu.Exit){
            AppClient.getMenu().getMenu().check(scanner);
            if(AppClient.getMenu() == Menu.Trade){
                System.out.print("trade ");
            }
            System.out.println(AppClient.getMenu().getMenu().toString());
            System.out.print("> ");
        }
        UserDB.disconnect();
        App.getServer().stop();
    }
}
