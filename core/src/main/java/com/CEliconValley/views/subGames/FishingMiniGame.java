package com.CEliconValley.views.subGames;

import com.CEliconValley.controllers.ItemManager;
import com.CEliconValley.models.animals.FishType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

public class FishingMiniGame {

    private Animation<TextureRegion> bgAnimation;
    private TextureRegion fishTexture;
    private Texture greenBarTexture;

    private float stateTime;
    private float fishY;
    private float fishSpeed;
    private boolean goingUp = true;

    private float greenBarY;
    private float greenBarHeight;
    private float greenBarWidth;

    private int attemptsLeft = 3;
    private boolean success = false;

    private OrthographicCamera camera;

    public FishingMiniGame(OrthographicCamera camera, FishType fishType) {
        this.camera = camera;

        this.fishTexture = ItemManager.getTexture(fishType);
        greenBarTexture = new Texture("game/Buildings/Screen/greenBar.png");

        Texture fMG = new Texture("game/Buildings/Screen/fishingMiniGame.png");
        TextureRegion[][] bgFrames = TextureRegion.split(fMG, fMG.getWidth(), fMG.getHeight() / 4);
        TextureRegion[] bGframes = new TextureRegion[4];
        for (int i = 0; i < 4; i++) {
            bGframes[i] = bgFrames[i][0];
        }
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
        return MathUtils.random(480f, 900f);
    }

    public void update(float delta) {
        stateTime += delta;

        float viewportHeight = camera.viewportHeight;
        float viewportWidth = camera.viewportWidth;

        float bgHeight = viewportHeight * 0.8f;
        float bgWidth = bgHeight * (156f / 471f);
        float bgY = camera.position.y - (bgHeight / 2f);

        float fishWidth = greenBarWidth;
        float fishHeight = getFishHeight(fishWidth);

        if (goingUp) {
            fishY += fishSpeed * delta;
            if (fishY + fishHeight >= bgHeight* 449f / 471f) {
                fishY =  bgHeight* 449f / 471f-fishHeight;
                goingUp = false;
            }
        } else {
            fishY -= fishSpeed * delta;
            if (fishY <=bgHeight* 26f / 471f) {
                fishY = bgHeight* 26f / 471f;
                goingUp = true;
            }
        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
            fishWidth = greenBarWidth;
             fishHeight = getFishHeight(fishWidth);
            float fishCenterY = bgY + fishY + fishHeight / 2f;

            float greenBarY = bgY + greenBarYRatio * bgHeight;
            float greenBarHeight = greenBarHeightRatio * bgHeight;

            if (fishCenterY >= greenBarY && fishCenterY <= greenBarY + greenBarHeight) {
                success = true;
                System.out.println("Yes");
                resetGreenBarRelative();
                resetFishPosition();
            } else {
                attemptsLeft--;
                System.out.printf("NO");
                if (attemptsLeft <= 0) {
                    System.out.println("nonononononono");
                }
            }
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
    }


    public boolean isFinished() {
        return success || attemptsLeft <= 0;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setFish(FishType fishType) {
        fishTexture = ItemManager.getTexture(fishType);
    }
}
