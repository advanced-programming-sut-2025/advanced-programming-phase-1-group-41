package org.example.models;

import dev.morphia.annotations.Entity;

@Entity
public enum WeatherType {
    Sunny("Sunny"),
    Rainy("Rainy"),
    Stormy("Stormy"),
    Snowy("Snowy"),
    ;

    private final String name;


    WeatherType(String name) {
        this.name = name;
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
