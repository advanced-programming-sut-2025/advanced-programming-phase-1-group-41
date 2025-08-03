package com.CEliconValley.client.model;

import com.CEliconValley.client.view.screen.maps.CoopMap;
import com.CEliconValley.common.AnimalData;
import com.CEliconValley.models.Cell;
import com.CEliconValley.models.Hero;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.views.maps.BarnMap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Random;

public class AnimalSprite {
    public Texture texture;
    public TextureRegion [][] animalActs;
    public int currentDirection;
    public AnimalData animalData;
    public int columns;
    public int rows;
    public boolean isMoving = false;
    public int x;
    public int y;
    public int targetX;
    public int targetY;
    public float renderX;
    public float renderY;
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;
    public Location location;
    public int randomX;
    public int randomY;
    public boolean randomSetter = false;

    public AnimalSprite(Location location, AnimalData animalData, int X, int Y) {
        this.texture = new Texture("game/Hero/NPC/"+animalData.getAnimalType()+".png");
        this.animalData = animalData;
        setCR();
        currentDirection=3;
        this.animalActs = TextureRegion.split(texture, texture.getWidth()/columns, texture.getHeight()/rows);
        this.x = X;
        this.y = Y;
        this.targetX = x;
        this.targetY = y;
        this.location = location;
        setRandomPoint();
    }

    public void setRandomPoint(){
        if(location instanceof BarnMap barnMap){
            Random random = new Random();
            int randomIndex = random.nextInt(barnMap.getCells().size());
            Cell cell = barnMap.getCells().get(randomIndex);
            this.randomX = cell.getX();
            this.randomY = cell.getY();
        }
        if(location instanceof CoopMap coopMap){
            Random random = new Random();
            int randomIndex = random.nextInt(coopMap.getCells().size());
            Cell cell = coopMap.getCells().get(randomIndex);
            this.randomX = cell.getX();
            this.randomY = cell.getY();
        }
    }
    public boolean reachedDestination(){
        return x == randomX && y == randomY;
    }

    private void setCR(){
        switch (this.animalData.getAnimalType()){

            case "Pig", "Goat", "Dino", "Rabbit","Cow", "Sheep" -> {
                columns = 4;
                rows = 7;
            }
            case "Chicken" -> {
                columns=4;
                rows=8;
            }
        }
    }

    public Animation<TextureRegion> walk(boolean canWalk, int direction) {
        TextureRegion[] wantedActs=new TextureRegion[4];
        TextureRegion[] animation = new TextureRegion[1];
        currentDirection=direction;
        if(canWalk){
            switch(direction){
                case 1:
                    for(int i=0;i<animalActs[2].length;i++){
                        wantedActs[i] = animalActs[2][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 2:
                    for(int i=0;i<animalActs[1].length;i++){
                        wantedActs[i] = animalActs[1][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 3:
                    for(int i=0;i<animalActs[0].length;i++){
                        wantedActs[i] = animalActs[0][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 4:
                    for (int i = 0; i < animalActs[1].length; i++) {
                        TextureRegion flippedFrame = new TextureRegion(animalActs[1][i]);
                        flippedFrame.flip(true, false);
                        wantedActs[i] = flippedFrame;
                    }
                    return new Animation<>(0.15f, wantedActs);

            }
        }else {
            switch (direction) {
                case 1:
                    for(int i=0;i<animalActs[3].length;i++){
                        wantedActs[i] = animalActs[5][i];
                    }
//                    animation[0] = animalActs[2][0];
                    return new Animation<>(0.15f, wantedActs);
                case 2:
//                    animation[0] = animalActs[1][0];
                    for(int i=0;i<animalActs[3].length;i++){
                        wantedActs[i] = animalActs[4][i];
                    }
//                    animation[0] = animalActs[2][0];
                    return new Animation<>(0.15f, wantedActs);
                case 3:
                    for(int i=0;i<animalActs[3].length;i++){
                        wantedActs[i] = animalActs[3][i];
                    }
//                    animation[0] = animalActs[2][0];
                    return new Animation<>(0.15f, wantedActs);
                case 4:
                    for(int i=0;i<animalActs[3].length;i++){
                        wantedActs[i] = animalActs[3][i];
                    }
//                    animation[0] = animalActs[2][0];
                    return new Animation<>(0.15f, wantedActs);

            }
        }
        return null;
    }
}
