package models;

import models.items.Backpack;
import models.locations.Farm;
import models.skills.Skill;

import java.util.ArrayList;

public class Player {
    Point point;
    Farm farm;
    ArrayList<Animal> animals;
    // TODO
    ArrayList<Skill> skills;
    Energy energy;
    User user;
    Backpack backpack;
}
