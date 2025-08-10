package com.CEliconValley.models;

import com.CEliconValley.client.view.screen.randomwalk.Node;
import com.CEliconValley.common.messages.TGPoint;
import com.CEliconValley.models.locations.Farm;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Hero {
    private final Texture playerTexture;
    private final Texture toolsTexture_Front;
    private final Texture toolsTexture_Back;
    private final Texture toolsTexture_Side;
    private TextureRegion[][] playerActs;
    private TextureRegion[][] toolsActs_Front;
    private TextureRegion[][] toolsActs_Back;
    private TextureRegion[][] toolsActs_Side;

    private final Texture generalToolsTexture_Front;
    private final Texture generalToolsTexture_Back;
    private final Texture generalToolsTexture_Side;
    private TextureRegion[][] otherToolsActs_Front;
    private TextureRegion[][] otherToolsActs_Back;
    private TextureRegion[][] otherToolsActs_Side;
    private Farm farm;
    public int currentDirection=3;

    public AtomicInteger playerX = new AtomicInteger();
    public final AtomicInteger playerY = new AtomicInteger();
    public final AtomicInteger targetX = new AtomicInteger();
    public final AtomicInteger targetY = new AtomicInteger();
    public float renderX;
    public float renderY;
    public AtomicBoolean isMoving = new AtomicBoolean(false);
    public final AtomicBoolean isActing = new AtomicBoolean(false);
    public float stateTime = 0f;
    public Animation<TextureRegion> currentAnimation;
    public int destX;
    public int destY;
    public Queue<Node> movementQueue = new LinkedList<>();
    public String selectedItemname = null;


    public boolean reachedDestination(){
        return playerX.get() == destX && playerY.get() == destY;
    }



    public Hero() {
        playerTexture = GameAssetManager.getGameAssetManager().getHeroTexture("generalActs.png");
        toolsTexture_Front = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Front.png");
        toolsTexture_Back = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Back.png");
        toolsTexture_Side = GameAssetManager.getGameAssetManager().getHeroTexture("tools_Side.png");
        generalToolsTexture_Front = GameAssetManager.getGameAssetManager().getHeroTexture("generalTools_Front.png");
        generalToolsTexture_Back = GameAssetManager.getGameAssetManager().getHeroTexture("generalTools_Back.png");
        generalToolsTexture_Side = GameAssetManager.getGameAssetManager().getHeroTexture("generalTools_Side.png");
        playerActs = TextureRegion.split(playerTexture, playerTexture.getWidth() / 8, playerTexture.getHeight() / 11);
        toolsActs_Front = TextureRegion.split(toolsTexture_Front, toolsTexture_Front.getWidth()/5,toolsTexture_Front.getHeight()/15 );
        toolsActs_Back = TextureRegion.split(toolsTexture_Back, toolsTexture_Back.getWidth()/5,toolsTexture_Back.getHeight()/15);
        toolsActs_Side= TextureRegion.split(toolsTexture_Side,toolsTexture_Side.getWidth()/5,toolsTexture_Side.getHeight()/15);
        otherToolsActs_Front = TextureRegion.split(generalToolsTexture_Front, generalToolsTexture_Front.getWidth()/6,generalToolsTexture_Front.getHeight()/8);
        otherToolsActs_Back = TextureRegion.split(generalToolsTexture_Back, generalToolsTexture_Front.getWidth()/6,generalToolsTexture_Front.getHeight()/8);
        otherToolsActs_Side = TextureRegion.split(generalToolsTexture_Side, generalToolsTexture_Front.getWidth()/6,generalToolsTexture_Front.getHeight()/8);
//        this.farm =(Farm) location;
    }

    public Animation<TextureRegion> walk(boolean canWalk, int direction) {
        TextureRegion[] wantedActs=new TextureRegion[8];
        TextureRegion[] animation = new TextureRegion[1];
        currentDirection=direction;
        if(canWalk){
            switch(direction){
                case 1:
                    for(int i=0;i<playerActs[2].length;i++){
                        wantedActs[i] = playerActs[2][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 2:
                    for(int i=0;i<playerActs[1].length;i++){
                        wantedActs[i] = playerActs[1][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 3:
                    for(int i=0;i<playerActs[0].length;i++){
                        wantedActs[i] = playerActs[0][i];
                    }
                    return new Animation<>(0.15f,wantedActs);
                case 4:
                    for (int i = 0; i < playerActs[1].length; i++) {
                        TextureRegion flippedFrame = new TextureRegion(playerActs[1][i]);
                        flippedFrame.flip(true, false);
                        wantedActs[i] = flippedFrame;
                    }
                    return new Animation<>(0.15f, wantedActs);

            }
        }else {
            switch (direction) {
                case 1:
                    animation[0] = playerActs[2][0];
                    return new Animation<>(0.15f, animation);
                case 2:
                    animation[0] = playerActs[1][0];
                    return new Animation<>(0.15f, animation);
                case 4:
                    TextureRegion baseFrame = new TextureRegion(playerActs[1][0]);
                    baseFrame.flip(true, false);
                    animation[0] = baseFrame;
                    return new Animation<>(0.15f, animation);
                default:
                    animation[0] = playerActs[0][0];
                    return new Animation<>(0.15f, animation);

            }
        }
        return null;
    }
    public Animation<TextureRegion> useTool(int level){
        TextureRegion[] wantedActs=new TextureRegion[5];

        switch(currentDirection){
            case 1:
                for(int i=0;i<toolsActs_Back[level].length;i++){
                    wantedActs[i] = toolsActs_Back[level][i];
                }
                return new Animation<>(0.10f,wantedActs);
            case 2:
                for(int i=0;i<toolsActs_Side[level].length;i++){
                    wantedActs[i] = toolsActs_Side[level][i];
                }
                return new Animation<>(0.10f,wantedActs);
            case 3:
                for(int i=0;i<toolsActs_Front[level].length;i++){
                    wantedActs[i] = toolsActs_Front[level][i];
                }
                return new Animation<>(0.10f,wantedActs);
            case 4:
                for (int i = 0; i <toolsActs_Side[level].length; i++) {
                    TextureRegion flippedFrame = new TextureRegion(toolsActs_Side[level][i]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.10f, wantedActs);

        }
        return null;
    }

    public Animation<TextureRegion> generalAct(ArrayList<TGPoint> tgPoints){
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        for (int i = 0; i < tgPoints.size(); i++) {
            TGPoint tgPoint = tgPoints.get(i);
            wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
        }
        return new Animation<>(0.10f,wantedActs);
    }

    public Animation<TextureRegion> useOtherTool(ArrayList<TGPoint> tgPoints){
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        switch(currentDirection){
            case 1:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = otherToolsActs_Back[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.10f,wantedActs);
            case 2:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = otherToolsActs_Side[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.10f,wantedActs);
            case 3:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = otherToolsActs_Front[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.10f,wantedActs);
            case 4:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    TextureRegion flippedFrame = new TextureRegion(otherToolsActs_Side[tgPoint.row][tgPoint.col]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.10f, wantedActs);

        }
        return null;
    }

    public Animation<TextureRegion> eat(){
        ArrayList<TGPoint> tgPoints = getEatTG();
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        switch(currentDirection){
            case 1:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.15f,wantedActs);
            case 2:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.15f,wantedActs);
            case 3:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
                }
                return new Animation<>(0.15f,wantedActs);
            case 4:
                for (int i = 0; i < tgPoints.size(); i++) {
                    TGPoint tgPoint = tgPoints.get(i);
                    TextureRegion flippedFrame = new TextureRegion(playerActs[tgPoint.row][tgPoint.col]);
                    flippedFrame.flip(true, false);
                    wantedActs[i] = flippedFrame;
                }
                return new Animation<>(0.15f, wantedActs);

        }
        return null;
    }
    public Animation<TextureRegion> shear(){
        ArrayList<TGPoint> tgPoints = getShearTG();
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        if(currentDirection==4){
            for (int i = 0; i < tgPoints.size(); i++) {
                TGPoint tgPoint = tgPoints.get(i);
                TextureRegion flippedFrame = new TextureRegion(playerActs[tgPoint.row][tgPoint.col]);
                flippedFrame.flip(true, false);
                wantedActs[i] = flippedFrame;
            }
            return new Animation<>(0.15f,wantedActs);
        }else{
            for (int i = 0; i < tgPoints.size(); i++) {
                TGPoint tgPoint = tgPoints.get(i);
                wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
            }
            return new Animation<>(0.15f,wantedActs);
        }
    }
    public Animation<TextureRegion> milk(){
        ArrayList<TGPoint> tgPoints = getMilkTG();
        TextureRegion[] wantedActs=new TextureRegion[tgPoints.size()];
        if(currentDirection==4){
            for (int i = 0; i < tgPoints.size(); i++) {
                TGPoint tgPoint = tgPoints.get(i);
                TextureRegion flippedFrame = new TextureRegion(playerActs[tgPoint.row][tgPoint.col]);
                flippedFrame.flip(true, false);
                wantedActs[i] = flippedFrame;
            }
            return new Animation<>(0.15f,wantedActs);
        }else{
            for (int i = 0; i < tgPoints.size(); i++) {
                TGPoint tgPoint = tgPoints.get(i);
                wantedActs[i] = playerActs[tgPoint.row][tgPoint.col];
            }
            return new Animation<>(0.15f,wantedActs);
        }
    }
    public Farm getFarm() {
        return farm;
    }


    private ArrayList<TGPoint> getEatTG(){
        ArrayList<TGPoint> tgp = new ArrayList();
        tgp.add(new TGPoint(5,4));
        tgp.add(new TGPoint(5,5));
        tgp.add(new TGPoint(5,6));
        tgp.add(new TGPoint(5,7));
        tgp.add(new TGPoint(6,0));
        tgp.add(new TGPoint(6,1));
        tgp.add(new TGPoint(6,2));
        tgp.add(new TGPoint(6,3));


        tgp.add(new TGPoint(4,4));
        tgp.add(new TGPoint(4,5));
        tgp.add(new TGPoint(4,4));
        tgp.add(new TGPoint(4,5));

        tgp.add(new TGPoint(4,6));
        tgp.add(new TGPoint(4,7));
        tgp.add(new TGPoint(5,0));
        tgp.add(new TGPoint(5,1));
        tgp.add(new TGPoint(5,2));
        tgp.add(new TGPoint(5,3));
        tgp.add(new TGPoint(4,6));
        tgp.add(new TGPoint(4,7));
        tgp.add(new TGPoint(5,0));
        tgp.add(new TGPoint(5,1));
        tgp.add(new TGPoint(5,2));
        tgp.add(new TGPoint(5,3));
        return tgp;
    }

    private ArrayList<TGPoint> getShearTG(){
        ArrayList<TGPoint> tgp = new ArrayList();
        switch (currentDirection){
            case 1 -> {
                tgp.add(new TGPoint(9,0));
                tgp.add(new TGPoint(9,1));
                tgp.add(new TGPoint(9,2));
                tgp.add(new TGPoint(9,3));
                tgp.add(new TGPoint(9,0));
                tgp.add(new TGPoint(9,1));
                tgp.add(new TGPoint(9,2));
                tgp.add(new TGPoint(9,3));
                tgp.add(new TGPoint(9,0));
                tgp.add(new TGPoint(9,1));
                tgp.add(new TGPoint(9,2));
                tgp.add(new TGPoint(9,3));
                tgp.add(new TGPoint(9,0));
                tgp.add(new TGPoint(9,1));
                tgp.add(new TGPoint(9,2));
                tgp.add(new TGPoint(9,3));
            }
            case 2, 4 -> {
                tgp.add(new TGPoint(8,4));
                tgp.add(new TGPoint(8,5));
                tgp.add(new TGPoint(8,6));
                tgp.add(new TGPoint(8,7));
                tgp.add(new TGPoint(8,4));
                tgp.add(new TGPoint(8,5));
                tgp.add(new TGPoint(8,6));
                tgp.add(new TGPoint(8,7));
                tgp.add(new TGPoint(8,4));
                tgp.add(new TGPoint(8,5));
                tgp.add(new TGPoint(8,6));
                tgp.add(new TGPoint(8,7));
                tgp.add(new TGPoint(8,4));
                tgp.add(new TGPoint(8,5));
                tgp.add(new TGPoint(8,6));
                tgp.add(new TGPoint(8,7));
            }
            case 3 -> {
                tgp.add(new TGPoint(8,0));
                tgp.add(new TGPoint(8,1));
                tgp.add(new TGPoint(8,2));
                tgp.add(new TGPoint(8,3));
                tgp.add(new TGPoint(8,0));
                tgp.add(new TGPoint(8,1));
                tgp.add(new TGPoint(8,2));
                tgp.add(new TGPoint(8,3));
                tgp.add(new TGPoint(8,0));
                tgp.add(new TGPoint(8,1));
                tgp.add(new TGPoint(8,2));
                tgp.add(new TGPoint(8,3));
                tgp.add(new TGPoint(8,0));
                tgp.add(new TGPoint(8,1));
                tgp.add(new TGPoint(8,2));
                tgp.add(new TGPoint(8,3));
            }
        }
        return tgp;
    }
    private ArrayList<TGPoint> getMilkTG() {
        ArrayList<TGPoint> tgp = new ArrayList();
        switch (currentDirection) {
            case 1 -> {
                tgp.add(new TGPoint(7, 4));
                tgp.add(new TGPoint(7, 5));
                tgp.add(new TGPoint(7, 6));
                tgp.add(new TGPoint(7, 7));
                tgp.add(new TGPoint(7, 4));
                tgp.add(new TGPoint(7, 5));
                tgp.add(new TGPoint(7, 6));
                tgp.add(new TGPoint(7, 7));
                tgp.add(new TGPoint(7, 4));
                tgp.add(new TGPoint(7, 5));
                tgp.add(new TGPoint(7, 6));
                tgp.add(new TGPoint(7, 7));
                tgp.add(new TGPoint(7, 4));
                tgp.add(new TGPoint(7, 5));
                tgp.add(new TGPoint(7, 6));
                tgp.add(new TGPoint(7, 7));
            }
            case 2, 4 -> {
                tgp.add(new TGPoint(7, 0));
                tgp.add(new TGPoint(7, 1));
                tgp.add(new TGPoint(7, 2));
                tgp.add(new TGPoint(7, 3));
                tgp.add(new TGPoint(7, 0));
                tgp.add(new TGPoint(7, 1));
                tgp.add(new TGPoint(7, 2));
                tgp.add(new TGPoint(7, 3));
                tgp.add(new TGPoint(7, 0));
                tgp.add(new TGPoint(7, 1));
                tgp.add(new TGPoint(7, 2));
                tgp.add(new TGPoint(7, 3));
                tgp.add(new TGPoint(7, 0));
                tgp.add(new TGPoint(7, 1));
                tgp.add(new TGPoint(7, 2));
                tgp.add(new TGPoint(7, 3));

            }
            case 3 -> {
                tgp.add(new TGPoint(6, 4));
                tgp.add(new TGPoint(6, 5));
                tgp.add(new TGPoint(6, 6));
                tgp.add(new TGPoint(6, 7));
                tgp.add(new TGPoint(6, 4));
                tgp.add(new TGPoint(6, 5));
                tgp.add(new TGPoint(6, 6));
                tgp.add(new TGPoint(6, 7));
                tgp.add(new TGPoint(6, 4));
                tgp.add(new TGPoint(6, 5));
                tgp.add(new TGPoint(6, 6));
                tgp.add(new TGPoint(6, 7));
                tgp.add(new TGPoint(6, 4));
                tgp.add(new TGPoint(6, 5));
                tgp.add(new TGPoint(6, 6));
                tgp.add(new TGPoint(6, 7));
            }
        }
        return tgp;
    }


}
