package org.example.models.buildings.animalContainer;

import org.example.models.Cell;
import org.example.models.Colors;
import org.example.models.Finder;
import org.example.models.animals.Animal;
import org.example.models.buildings.Building;
import org.example.models.buildings.Wall;
import org.example.models.locations.Farm;

import java.util.ArrayList;

public class Coop implements Building {
    private final ArrayList<Animal> animals = new ArrayList<>();
    private int x;
    private int y;

    public Coop(int x, int y, Farm farm, CoopType coopType) {
        this.x = x;
        this.y = y;
        int size = 5 + coopType.getCapacity() / 4;
        int xWall;
        int yWall;
        yWall = y;
        while(yWall<=y + size) {
            for (int i = x; i <= x + size; i++) {
                Cell cell = Finder.findCellByCoordinates(i, yWall, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            yWall+=size;
        }
        xWall = x;
        while(xWall<=x+size) {
            for (int j = y+1; j <= y+size; j++) {
                Cell cell = Finder.findCellByCoordinates(xWall, j, farm);
                assert cell != null;
                cell.setObjectMap(new Wall());
            }
            xWall+=size;
        }
        x++;
        y++;
        for(int i = x; i< size +x - 1; i++) {
            for(int j = y; j< size +y - 1; j++) {
                Cell cell=Finder.findCellByCoordinates(i, j, farm);
                assert cell != null;
                cell.setObjectMap(this);
            }
        }
    }

    @Override
    public String getChar() {
        return Colors.colorize(243,234,"cc");
    }

    @Override
    public String getName() {
        return "Barn";
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public void addAnimal(Animal animal) {
        animals.add(animal);
    }
}
