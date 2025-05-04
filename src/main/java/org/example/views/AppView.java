package org.example.views;

import org.example.models.App;
import org.example.models.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class AppView {
    public void runApp(){
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
        while(App.getMenu() != Menu.Exit){
            App.getMenu().getMenu().check(scanner);
        }
    }
}
