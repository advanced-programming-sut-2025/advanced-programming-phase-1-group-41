package com.CEliconValley.client.model;

import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.messages.TGPoint;
import com.CEliconValley.models.locations.Location;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

import static com.CEliconValley.client.view.screen.FarmScreen.CELL_SIZE;

public class NPCSprite {
    public Texture texture;
    public TextureRegion[][] npcActs;
    public int currentDirection;
    public NPCData npcData;
    public int columns;
    public int rows;
    public boolean isMoving = false;
    public int x;
    public int y;
    public int targetX;
    public int targetY;
    public float renderX;
    public float renderY;
    public boolean isOutside = false;
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;
    public Location location;
    public int randomX;
    public int randomY;
    public boolean randomSetter = false;


    public NPCSprite(Location location, NPCData npcData, int x, int y, boolean isOutside) {
        this.texture = new Texture("game/Hero/NPC/Sheep.png");
        this.npcData = npcData;
        setCR();
        currentDirection=3;
        this.npcActs = TextureRegion.split(texture, texture.getWidth()/columns, texture.getHeight()/rows);
        this.x = x;
        this.y = y;
        this.targetX = x;
        this.targetY = y;
        this.randomX = x;
        this.randomY = y;
        this.renderX = this.x*CELL_SIZE;
        this.renderY = this.y*CELL_SIZE;
        this.location = location;

    }

    private void setCR(){
        this.rows = 7;
        this.columns = 4;
    }

    public Animation<TextureRegion> walk(boolean canWalk, int direction) {
        currentDirection=direction;
        if(canWalk){
            ArrayList<TGPoint> tgPoints = walkAnime(true);
            TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
            if(currentDirection==4){
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    TextureRegion flippedFrame = new TextureRegion(npcActs[tgPoint.row][tgPoint.col]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.15f,wantedActs);
            }else{
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = npcActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.15f,wantedActs);
            }
        }else {
            ArrayList<TGPoint> tgPoints = walkAnime(false);
            TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
            if(currentDirection==4){
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    TextureRegion flippedFrame = new TextureRegion(npcActs[tgPoint.row][tgPoint.col]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.15f,wantedActs);
            }else{
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = npcActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.15f,wantedActs);
            }
        }
    }

    private ArrayList<TGPoint> walkAnime(boolean canwalk){
        ArrayList<TGPoint> anime = new ArrayList<>();
        int delta = canwalk ? 0 : 3;
        switch (currentDirection){
            case 1 -> {
                for(int i=0;i<npcActs[2+delta].length;i++){
                    anime.add(new TGPoint(2+delta,i));
                }
            }
            case 2, 4 -> {
                for(int i=0;i<npcActs[1+delta].length;i++){
                    anime.add(new TGPoint(1+delta,i));
                }
            }
            case 3 -> {
                for(int i=0;i<npcActs[0].length;i++){
                    anime.add(new TGPoint(0+delta,i));
                }
            }
        }
        return anime;
    }
}
