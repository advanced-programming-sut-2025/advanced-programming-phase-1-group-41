package models;

import java.util.ArrayList;

public class Game {
    private static TimeLine time = TimeLine.getInstance();
    private static Player currentPlayer;
    private static WeatherType weatherType;

    public static TimeLine getTime() {
        return time;
    }

    public static Player getCurrentPlayer() {
        return currentPlayer;
    }

    public static WeatherType getWeatherType() {
        return weatherType;
    }
}
