package com.CEliconValley.controllers.Spawner;

import com.CEliconValley.models.Cell;
import com.CEliconValley.models.animals.Animal;
import com.CEliconValley.models.buildings.animalContainer.Barn;
import com.CEliconValley.models.foragings.Nature.Grass;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class AnimalSpawner {

    private final Farm farm;
    private final Map<Cell, TextureRegion> animalRenderCache = new HashMap<>();

    private final Texture cowTexture = new Texture("game/animals/cow.png");
    private final Texture chickenTexture = new Texture("game/animals/chicken.png");
    private final Texture sheepTexture = new Texture("game/animals/sheep.png");

    public AnimalSpawner(Farm farm) {
        this.farm = farm;
    }

    public boolean renderAnimal(SpriteBatch batch, Cell cell, Location location) {
        Animal theAnimal=null;
        if (!(cell.getObjectMap() instanceof Animal animal)) return false;
        if(location instanceof Farm farm){
            for(Barn barn:farm.getBarns()){
                for(Animal animal1:barn.getAnimals()){
                    if(animal1.isHome())continue;
                    if(animal1.getX()==cell.getX() && animal1.getY()==cell.getY()){
                        theAnimal=animal1;
                        break;
                    }
                }
                if(theAnimal!=null){
                    break;
                }
            }
        }

        float x = cell.getX() * CELL_SIZE;
        float y = cell.getY() * CELL_SIZE;

        if (animalRenderCache.containsKey(cell)) {
            batch.draw(animalRenderCache.get(cell), x, y, CELL_SIZE, CELL_SIZE);
            return true;
        }

        TextureRegion textureRegion = switch (animal.getAnimalType()) {
            case "Cow" -> new TextureRegion(cowTexture);
            case "Chicken" -> new TextureRegion(chickenTexture);
            case "Sheep" -> new TextureRegion(sheepTexture);
            default -> throw new IllegalStateException("Unexpected value: " + animal.getAnimalType());
        };

        animalRenderCache.put(cell, textureRegion);
        batch.draw(textureRegion, x, y, CELL_SIZE, CELL_SIZE);
        return true;
    }

    public void removeAnimal(Cell cell) {
        if (cell.getObjectMap() instanceof Animal) {
            animalRenderCache.remove(cell);
            cell.setObjectMap(new Grass());
        }
    }

    public void clearCache() {
        animalRenderCache.clear();
    }
}

