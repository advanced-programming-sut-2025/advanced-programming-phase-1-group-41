package org.example;

import org.example.database.UserDB;
import org.example.models.locations.Farm;
import org.example.models.locations.Village;
import org.example.views.AppView;

import java.security.NoSuchAlgorithmException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException {
        AppView appView = new AppView();
        appView.runApp();
    }
}