package org.example.models;

import dev.morphia.annotations.Embedded;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;

import java.util.Date;

@Entity
public class TimeLine {
    @Id
    private ObjectId _id;
    private int hour;
    private int day;
    private Season season;
    private int year;

    public TimeLine() {
        this.hour = 9;
        this.day = 0;
        this.season = Season.Spring;
        this.year = 1990;
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

    public void advanceOneHour(){
        hour++;
        if(hour >= 24){
            hour = 0;
            advanceOneDay();
        }
    }
    public void advanceOneDay(){
        day++;
        if(day >= 7){
            day = 0;
            advanceOneSeason();
        }
    }
    public void advanceOneSeason(){
        Season[] seasons = Season.values();
        season = seasons[(season.ordinal()+1) % seasons.length];
        if(season.ordinal() == 0){
            advanceOneYear();
        }
    }
    public void advanceOneYear(){
        year++;
    }

    public void sateDate(int hour, int day,Season season, int year){
        this.hour = hour;
        this.day = day;
        this.season = season;
        this.year = year;
    }



}
