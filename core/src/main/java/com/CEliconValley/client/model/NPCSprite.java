package com.CEliconValley.client.model;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.common.NPCData;
import com.CEliconValley.common.messages.TGPoint;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.locations.Location;
import com.CEliconValley.models.npc.npcCharacters.Abigail;
import com.CEliconValley.models.npc.npchomes.AbigailHome;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;

public class NPCSprite {
    public Texture texture;
    public TextureRegion[][] npcActs;
    public int currentDirection;
    String npcName;
    public int columns;
    public int rows;
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;
    public Location location;
    private final Texture buffTexture;
    private TextureRegion[][] buff;

    public boolean reachedDestination(){
        return getNPCData().x == getNPCData().randomX && getNPCData().y == getNPCData().randomY;
    }



    public NPCSprite(Location location, NPCData npcData) {
        this.npcName = npcData.getName();
        setCR();
        this.texture = GameAssetManager.getGameAssetManager().getNPCAssets(npcName);
        currentDirection=3;
        this.npcActs = TextureRegion.split(texture, texture.getWidth()/columns, texture.getHeight()/rows);
        this.location = location;
        this.currentAnimation = walk(false, currentDirection);
        buffTexture = GameAssetManager.getGameAssetManager().getHeroTexture("Particles.png");
        buff = TextureRegion.split(buffTexture, buffTexture.getWidth()/8,buffTexture.getHeight()/2);
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
                return new Animation<>(0.25f,wantedActs);
            }else{
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = npcActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.25f,wantedActs);
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
                return new Animation<>(0.25f,wantedActs);
            }else{
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = npcActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.25f,wantedActs);
            }
        }
    }

    private ArrayList<TGPoint> walkAnime(boolean canwalk){
        ArrayList<TGPoint> anime = new ArrayList<>();
        int delta = canwalk ? 0 : 0;
        switch (currentDirection){
            case 1 -> {
                if(canwalk){
                    for(int i=0;i<npcActs[2+delta].length;i++){
                        anime.add(new TGPoint(2+delta,i));
                    }
                }else{
                    anime.add(new TGPoint(2,0));
                }
            }
            case 2, 4 -> {
                if(canwalk){
                    for(int i=0;i<npcActs[1+delta].length;i++){
                        anime.add(new TGPoint(1+delta,i));
                    }
                }else{
                    anime.add(new TGPoint(1,0));
                }
            }
            case 3 -> {
                if(canwalk){
                    for(int i=0;i<npcActs[0].length;i++){
                        anime.add(new TGPoint(0+delta,i));
                    }
                }else{
                    anime.add(new TGPoint(0,0));
                }

            }
        }
        return anime;
    }


    public NPCData getNPCData() {
        for (NPCData npCsDatum : AppClient.getGameData().getVillageData().getNPCsData()) {
            if(npCsDatum.getName().equals(npcName)){
                return npCsDatum;
            }
        }
        return null;
    }

    private void setCR(){
        this.columns = 4;
        switch (npcName){
            case "Willy" -> this.rows = 10;
            case "Mohsen" -> this.rows = 9;
            case "Morris" -> this.rows = 5;
            case "Gus" ->this.rows = 13 ;
            case "Marnie" -> this.rows = 9;
            case "Clint" -> this.rows = 10;
            case "Robin" -> this.rows = 9;
            case "Pierre" -> this.rows = 6;
            case "Sebastien" -> this.rows = 14;
            case "leah" -> this.rows = 12;
            case "Harvey" -> this.rows = 14;
            case "Abigail" -> this.rows = 14;
        }
    }

    public Animation<TextureRegion> dialogue(){
        ArrayList<TGPoint> tgPoints = getBuff();
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        for (int i = 0; i < tgPoints.size(); i++) {
            TGPoint tgPoint = tgPoints.get(i);
            TextureRegion flippedFrame = new TextureRegion(buff[tgPoint.row][tgPoint.col]);
            flippedFrame.flip(true, false);
            wantedActs[i] = flippedFrame;
        }
        return new Animation<>(0.20f,wantedActs);
    }

    private ArrayList<TGPoint> getBuff(){
        ArrayList<TGPoint> pet = new ArrayList();
        for (int i = 0; i < 8; i++) {
            pet.add(new TGPoint(1, i));
        }
        for (int i = 0; i < 8; i++) {
            pet.add(new TGPoint(1, i));
        }
        for (int i = 0; i < 8; i++) {
            pet.add(new TGPoint(1, i));
        }
        for (int i = 0; i < 8; i++) {
            pet.add(new TGPoint(1, i));
        }
        for (int i = 0; i < 8; i++) {
            pet.add(new TGPoint(1, i));
        }
        return pet;
    }


}
