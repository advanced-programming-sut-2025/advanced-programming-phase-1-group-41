package models.entities.individuals;

import models.entities.Entity;
import models.items.Item;

import java.util.ArrayList;

public abstract class Human implements Entity {
    String name;
    ArrayList<Item> items;
}
