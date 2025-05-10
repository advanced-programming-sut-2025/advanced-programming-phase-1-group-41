package org.example.models;

public enum Colors {
    BLACK("\u001B[30m", "\u001B[40m"),
    RED("\u001B[31m", "\u001B[41m"),
    GREEN("\u001B[32m", "\u001B[42m"),
    YELLOW("\u001B[33m", "\u001B[43m"),
    BLUE("\u001B[34m", "\u001B[44m"),
    PURPLE("\u001B[35m", "\u001B[45m"),
    CYAN("\u001B[36m", "\u001B[46m"),
    WHITE("\u001B[37m", "\u001B[47m"),
    RESET("\u001B[0m", "\u001B[0m");

    private final String foreGround;
    private final String backGround;

    Colors(String foreGround, String backGround) {
        this.foreGround = foreGround;
        this.backGround = backGround;
    }

    public String foreGround() {
        return foreGround;
    }

    public String backGround() {
        return backGround;
    }

    public String applyForeGround(String text) {
        return foreGround + text + RESET.foreGround;
    }

    public String applyBackGround(String text) {
        return backGround + text + RESET.backGround;
    }

    public String applyBoth(String text) {
        return foreGround + backGround + text + RESET.backGround + RESET.foreGround;
    }
}
