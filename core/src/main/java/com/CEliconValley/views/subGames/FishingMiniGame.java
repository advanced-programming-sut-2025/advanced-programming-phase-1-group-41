package com.CEliconValley.views.subGames;

import com.CEliconValley.client.view.screen.GameScreen;
import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.animals.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class FishingMiniGame {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();

    private Animation<TextureRegion> bgAnimation;
    private TextureRegion fishTexture;
    private Texture greenBarTexture;
    private enum FishBehavior { NORMAL, SUDDEN_TURN, HEAVY_DOWN, LIGHT_UP }
    private FishBehavior currentBehavior = FishBehavior.NORMAL;
    private float timeSinceLastBehaviorChange = 0f;
    private float behaviorChangeInterval = MathUtils.random(1f, 10f);
    private float stateTime;
    private float fishY;
    private float fishSpeed;
    private boolean goingUp = true;
    private int score = 500;
    boolean isPerfect = true;


    private float greenBarY;
    private float greenBarHeight;
    private float greenBarWidth;
    private float greenBarSpeed;
    private boolean greenBarGoingUp = true;
    private FishType fishType;
    private Texture legendFishes=new Texture("game/Hero/NPC/LegendFishes.png");


    private int attemptsLeft = 3;
    private boolean success = false;

    private OrthographicCamera camera;

    public FishingMiniGame(OrthographicCamera camera, FishType fishType) {
        this.camera = camera;
        this.fishType = fishType;
        TextureRegion[][] legends=TextureRegion.split(legendFishes,legendFishes.getWidth()/4,legendFishes.getHeight());
        switch(fishType) {
            case Legend:
                this.fishTexture=legends[0][0];
                break;
            case Glacierfish:
                this.fishTexture=legends[0][1];
                break;
            case Angler:
                this.fishTexture=legends[0][2];
                break;
            case Crimsonfish:
                this.fishTexture=legends[0][3];
                break;
            default:
                this.fishTexture = ItemManager.getTexture(fishType);
                break;

        }
        greenBarTexture = new Texture("game/Buildings/Screen/greenBar.png");

        Texture fMG = new Texture("game/Buildings/Screen/fishingMiniGame.png");
        TextureRegion[][] bgFrames = TextureRegion.split(fMG, fMG.getWidth(), fMG.getHeight() / 4);
        TextureRegion[] bGframes = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            bGframes[i] = bgFrames[i][0];
        }
        greenBarSpeed = getRandomGreenBarSpeed();
        greenBarGoingUp = MathUtils.randomBoolean();
        this.bgAnimation = new Animation<>(0.15f, bGframes);
        this.bgAnimation.setPlayMode(Animation.PlayMode.LOOP);


        resetGreenBarRelative();
        resetFishPosition();
    }

    private void resetFishPosition() {

        fishY = 0f;
        fishSpeed = getRandomSpeed();
        goingUp = true;
    }

    private float greenBarYRatio;
    private float greenBarHeightRatio;

    private void resetGreenBarRelative() {

        greenBarHeightRatio = MathUtils.random(27f / 471f, 108f / 471f);

        greenBarYRatio = MathUtils.random(26f / 471f, 449f / 471f - greenBarHeightRatio);
    }

    private float getRandomSpeed() {
        return MathUtils.random(400f, 900f);
    }
    private float getRandomGreenBarSpeed() {
        return MathUtils.random(90f, 500f);
    }

    public void update(float delta) {
        stateTime += delta;
        timeSinceLastBehaviorChange += delta;

        if(timeSinceLastBehaviorChange >= behaviorChangeInterval){
            timeSinceLastBehaviorChange = 0f;
            behaviorChangeInterval = MathUtils.random(1f, 3f);
            currentBehavior = FishBehavior.values()[MathUtils.random(0, FishBehavior.values().length - 1)];


            switch(currentBehavior){
                case SUDDEN_TURN:
                    goingUp = !goingUp;
                    break;
                case NORMAL:
                    fishSpeed=getRandomSpeed();
                    break;
            }
        }

        float viewportHeight = camera.viewportHeight;
        float viewportWidth = camera.viewportWidth;

        float bgHeight = viewportHeight * 0.8f;
        float bgWidth = bgHeight * (156f / 471f);
        float bgY = camera.position.y - (bgHeight / 2f);

        float fishWidth = greenBarWidth;
        float fishHeight = getFishHeight(fishWidth);
        float fishCurrentSpeed=fishSpeed;
           switch(currentBehavior){
                case SUDDEN_TURN:
                    break;
                case HEAVY_DOWN:
                    if(!goingUp) fishCurrentSpeed =fishSpeed*1.3f;
                    if(goingUp) fishCurrentSpeed= fishSpeed/1.3f;
                    break;
                case LIGHT_UP:
                    if(goingUp) fishCurrentSpeed= fishSpeed*1.3f;
                    if(!goingUp) fishCurrentSpeed= fishSpeed/1.3f;
                    break;
                case NORMAL:
                    break;
        }

        if (goingUp) {
            fishY += fishCurrentSpeed * delta;
            if (fishY + fishHeight >= bgHeight* 449f / 471f) {
                fishY =  bgHeight* 449f / 471f-fishHeight;
                goingUp = false;
            }
        } else {
            fishY -= fishCurrentSpeed * delta;
            if (fishY <=bgHeight* 26f / 471f) {
                fishY = bgHeight* 26f / 471f;
                goingUp = true;
            }
        }
        if (greenBarGoingUp) {
            greenBarYRatio += (greenBarSpeed * delta) / bgHeight;
            if (greenBarYRatio + greenBarHeightRatio >= 449f / 471f) {
                greenBarYRatio = 449f / 471f - greenBarHeightRatio;
                greenBarGoingUp = false;
                greenBarSpeed = getRandomGreenBarSpeed();
            }
        } else {
            greenBarYRatio -= (greenBarSpeed * delta) / bgHeight;
            if (greenBarYRatio <= 26f / 471f) {
                greenBarYRatio = 26f / 471f;
                greenBarGoingUp = true;
                greenBarSpeed = getRandomGreenBarSpeed();
            }
        }
        fishWidth = greenBarWidth;
             fishHeight = getFishHeight(fishWidth);
            float fishCenterY = bgY + fishY + fishHeight / 2f;

            float greenBarY = bgY + greenBarYRatio * bgHeight;
            float greenBarHeight = greenBarHeightRatio * bgHeight;
        if (fishCenterY >= greenBarY && fishCenterY <= greenBarY + greenBarHeight) {
            score += 4;
        } else {
            score -= 1;
            isPerfect=false;
        }

        if (score >= 1000) {
            System.out.println("You Win!");
            success=true;

        }
        if (score <= 0) {
            System.out.println("You Lose!");
            if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
                        .getScreen()  instanceof GameScreen screen) {
                screen.updateMessage("you lost :(", Color.RED);
                new Timer().schedule(new Timer.Task() {

                    @Override
                    public void run() {
                        screen.removeMessage();
                    }
                }, 5);
            }
            attemptsLeft=0;
        }
//        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
//            fishWidth = greenBarWidth;
//             fishHeight = getFishHeight(fishWidth);
//            float fishCenterY = bgY + fishY + fishHeight / 2f;
//
//            float greenBarY = bgY + greenBarYRatio * bgHeight;
//            float greenBarHeight = greenBarHeightRatio * bgHeight;
//
//            if (fishCenterY >= greenBarY && fishCenterY <= greenBarY + greenBarHeight) {
//                success = true;
//                System.out.println("Yes");
//                resetGreenBarRelative();
//                resetFishPosition();
//            } else {
//                attemptsLeft--;
//                System.out.printf("NO");
//                if (attemptsLeft <= 0) {
//                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
//                        .getScreen()  instanceof GameScreen screen){
//                        screen.updateMessage("you lost :(", Color.RED);
//                        new Timer().schedule(new Timer.Task() {
//
//                            @Override
//                            public void run() {
//                                screen.removeMessage();
//                            }
//                        }, 5);
//                    }
//                }else{
//                    if(((com.badlogic.gdx.Game) Gdx.app.getApplicationListener())
//                        .getScreen()  instanceof GameScreen screen){
//                        screen.updateMessage("you missed. "+attemptsLeft+" attempts left", Color.RED);
//                        new Timer().schedule(new Timer.Task() {
//                            @Override
//                            public void run() {
//                                screen.removeMessage();
//                            }
//                        }, 2);
//                    }
//                }
//
//            }
//        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            goingUp = !goingUp;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_8)) {
            attemptsLeft=0;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.NUMPAD_9)) {
            isPerfect=true;
            greenBarYRatio = 26f / 471f;
            greenBarHeightRatio = (449f - 26f) / 471f;
        }

    }


    private float getFishHeight(float fishWidth) {
        float fishAspect = (float) fishTexture.getRegionHeight() / fishTexture.getRegionWidth();
        return fishWidth * fishAspect;
    }

    public void render(SpriteBatch batch) {
        float viewportWidth = camera.viewportWidth;
        float viewportHeight = camera.viewportHeight;

        float bgHeight = viewportHeight * 0.8f;
        float bgWidth = bgHeight * (156f / 471f);
        float bgX = camera.position.x - (bgWidth / 2f);
        float bgY = camera.position.y - (bgHeight / 2f);

        TextureRegion currentBgFrame = bgAnimation.getKeyFrame(stateTime);
        batch.draw(currentBgFrame, bgX, bgY, bgWidth, bgHeight);

        greenBarWidth = bgWidth * (27f / 156f);
        float greenBarX = bgX + bgWidth * (81f / 156f);
        greenBarHeight = greenBarHeightRatio * bgHeight;
        greenBarY = bgY + greenBarYRatio * bgHeight;
        batch.draw(greenBarTexture, greenBarX, greenBarY, greenBarWidth, greenBarHeight);

        float fishWidth = greenBarWidth;
        float fishHeight = getFishHeight(fishWidth);
        float fishX = greenBarX + (greenBarWidth - fishWidth) / 2f;
        float fishScreenY = bgY + fishY;
        batch.draw(fishTexture, fishX, fishScreenY, fishWidth, fishHeight);
        batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.DARK_GRAY);
        float barWidth = 30;
        float barHeight = camera.viewportHeight * 0.75f;
        float barX =fishX +bgWidth/2f- barWidth*2;
        float barY = camera.position.y - barHeight / 2f;
        shapeRenderer.rect(barX, barY, barWidth, barHeight);

        shapeRenderer.setColor(Color.GREEN);
        float fillRatio = score / 1000f;
        float filledHeight = barHeight * fillRatio;
        shapeRenderer.rect(barX, barY, barWidth, filledHeight);

        shapeRenderer.end();

        batch.begin();

    }


    public boolean isFinished() {
        if( success || attemptsLeft <= 0){
            resetGreenBarRelative();
            return true;
        };
        return false;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setFish(FishType fishType) {
        fishTexture = ItemManager.getTexture(fishType);
    }
    public FishType getFishType() {
        return fishType;
    }
    public boolean isPerfect(){
        return isPerfect;
    }
}
