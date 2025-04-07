package models;

import java.util.Date;

public class TimeLine {
    public static TimeLine instance= null;

    public static TimeLine getInstance() {
        if(instance == null) {
            instance = new TimeLine();
        }
        return instance;
    }
    private int hour;
    private int day;
    private Season season;
    private int year;


    private TimeLine(int hour, int day, Season season, int year) {
        this.hour = hour;
        this.day = day;
        this.season = season;
        this.year = year;
    }

    public int getHour() {
        return hour;
    }

    public int getDay() {
        return day;
    }

    public Season getSeason() {
        return season;
    }

    public int getYear() {
        return year;
    }

    public
}
