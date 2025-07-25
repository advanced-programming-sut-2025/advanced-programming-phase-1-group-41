package com.CEliconValley.models;

import dev.morphia.annotations.Entity;

@Entity
public enum WeatherType {
    Sunny("Sunny", 1),
    Rainy("Rainy", 1.5),
    Stormy("Stormy", 1.5),
    Snowy("Snowy", 2),
    ;

    private final String name;
    private final double energy;


    WeatherType(String name, double energy) {
        this.name = name;
        this.energy = energy;
    }

    public double getEnergy() {
        return energy;
    }
    @Override
    public String toString() {
        return name;
    }


    public static WeatherType parseWeatherType(String name){
        return switch(name){
            case "Sunny" -> Sunny;
            case "Rainy" -> Rainy;
            case "Stormy" -> Stormy;
            case "Snowy" -> Snowy;
            default -> null;
        };
    }
}
