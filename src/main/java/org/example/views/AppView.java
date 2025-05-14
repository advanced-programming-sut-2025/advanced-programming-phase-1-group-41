package org.example.views;

import org.example.database.UserDB;
import org.example.models.App;
import org.example.models.Menu;
import org.example.models.items.CookingRecipe;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Scanner;

public class AppView {
    public void runApp() throws NoSuchAlgorithmException {
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
        App.setQuestions(questions);
        Scanner scanner = new Scanner(System.in);
        App.setMenu(Menu.Authentication);
        CookingRecipe.updateRecipe();
        UserDB.connect();
        while(App.getMenu() != Menu.Exit){
            App.getMenu().getMenu().check(scanner);
            if(App.getMenu() == Menu.Trade){
                System.out.print("trade ");
            }
//            System.out.println(App.getMenu().getMenu().toString());
            System.out.print("> ");
        }
        UserDB.disconnect();
    }
}
