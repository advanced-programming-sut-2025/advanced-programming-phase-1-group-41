package org.example.models;

public enum Occupation {
    Broker("Broke"),
    Farmer("Farmer"),
    Rancher("Rancher"),
    Fisher("Fisher"),
    Chef("Chef"),
    Carpenter("Carpenter"),
    Bartender("Bartender"),
    BlackSmith("BlackSmith"),
    Jobless("Jobless");

    private String name;
    public String getName() {
        return name;
    }
    Occupation(String name){
        this.name = name;
    }
}
