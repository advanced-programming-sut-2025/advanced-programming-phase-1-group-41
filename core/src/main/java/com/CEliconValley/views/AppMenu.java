package com.CEliconValley.views;

import com.badlogic.gdx.graphics.Color;

import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public interface AppMenu {
    void check(Scanner scanner);
    void setMessage(String message, Color color);

}
