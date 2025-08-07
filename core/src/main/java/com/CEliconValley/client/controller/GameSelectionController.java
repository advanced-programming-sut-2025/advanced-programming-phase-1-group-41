package com.CEliconValley.client.controller;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.AvatarSelectionView;
import com.CEliconValley.client.view.GameSelectionView;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;

public class GameSelectionController {
    private ImageButton selectedButton = null;
    private GameSelectionView view;

    public void setView(GameSelectionView view) {
        this.view = view;
    }

    public void setupListeners() {
        for (int i = 0; i < view.getAvatarButtons().size; i++) {
            final int index = i;
            final ImageButton button = view.getAvatarButtons().get(i);
            view.getAvatarButtons().get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    handleAvatarSelect(index);
                    if (selectedButton != null) {
                        selectedButton.setStyle(createDefaultStyle(view.getAvatarPaths().get(view.getAvatarButtons().indexOf(selectedButton, true))));
                    }
                    button.setStyle(createSelectedStyle(view.getAvatarPaths().get(index)));
                    selectedButton = button;

                    handleAvatarSelect(index);
                }
            });
        }

        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleBack();
            }
        });
    }

    private void handleAvatarSelect(int index) {
        String avatarPath = view.getAvatarPaths().get(index);
        view.showPreviewAvatar(avatarPath);
        view.setMessage("Game selected: " + avatarPath.substring(15, avatarPath.length() - 4));
        GameMessage<String> msg = new GameMessage<>("avatar-request",avatarPath);
        AppClient.getClient().send(new Gson().toJson(msg));
    }

    private void handleBack() {
        view.goBack();
    }

    private ImageButton.ImageButtonStyle createDefaultStyle(String imagePath) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(texture);
        return style;
    }

    private ImageButton.ImageButtonStyle createSelectedStyle(String imagePath) {
        Texture texture = new Texture(Gdx.files.internal(imagePath));
        ImageButton.ImageButtonStyle style = new ImageButton.ImageButtonStyle();
        style.imageUp = new TextureRegionDrawable(texture);

        Texture border = GameAssetManager.getGameAssetManager().getBackgroundTexture("AvatarBackground.png");
        style.up = new TextureRegionDrawable(border);

        return style;
    }

}
