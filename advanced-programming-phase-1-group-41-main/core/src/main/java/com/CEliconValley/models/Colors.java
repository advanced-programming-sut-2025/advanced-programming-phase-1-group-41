package com.CEliconValley.models;

public class Colors {
    public static String foreColor(int colorCode) {
        return "\u001B[38;5;" + colorCode + "m";
    }

    public static String backColor(int colorCode) {
        return "\u001B[48;5;" + colorCode + "m";
    }

    public static final String RESET = "\u001B[0m";

    public static String colorize(int foreColor, int backColor, String text) {
        return foreColor(foreColor) + backColor(backColor) + text + RESET;
    }
}
