package com.CEliconValley;

import com.CEliconValley.views.AppView;

import java.security.NoSuchAlgorithmException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static String api_key = "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";
    public static void main(String[] args) throws NoSuchAlgorithmException {
        AppView appView = new AppView();
        appView.runApp();
    }
}