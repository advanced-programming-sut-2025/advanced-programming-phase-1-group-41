package com.CEliconValley;

import com.CEliconValley.views.AppView;

import java.security.NoSuchAlgorithmException;

public class TerminalMain {
    public static void main(String[] args) {
        AppView appView = new AppView();
        try {
            appView.runApp();
        } catch (NoSuchAlgorithmException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
