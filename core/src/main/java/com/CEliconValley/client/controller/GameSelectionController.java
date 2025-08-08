package com.CEliconValley.client.controller;

import com.CEliconValley.client.AppClient;
import com.CEliconValley.client.view.GameSelectionView;
import com.CEliconValley.common.messages.GameMessage;
import com.CEliconValley.models.ui.GameAssetManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.google.gson.Gson;

public class GameSelectionController {
    private TextButton selectedButton = null;
    private GameSelectionView view;

    public void setView(GameSelectionView view) {
        this.view = view;
    }

    public void setupListeners() {
        for (int i = 0; i < view.getGameButtons().size; i++) {
            final int index = i;
            final TextButton button = view.getGameButtons().get(i);
            view.getGameButtons().get(i).addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
//                    if (selectedButton != null) {
//                        selectedButton.setStyle(createDefaultStyle(view.getGames().get(view.getGameButtons().indexOf(selectedButton, true))));
//                    }
//                    button.setStyle(createSelectedStyle(view.getGames().get(index)));
                    selectedButton = button;

                    handleGameSelect(index);
                }
            });
        }

        view.getBackButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleBack();
            }
        });
        view.getJoinButton().addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                handleJoin();
            }
        });
    }


    private void handleJoin(){
        System.out.println(selectedButton == null ? "null" : selectedButton.getText());
        if(selectedButton != null){
            GameMessage<String> msg = new GameMessage<>("load-game", selectedButton.getText().toString());
            AppClient.getClient().send(new Gson().toJson(msg));
        }
    }

    private void handleGameSelect(int index) {
        String selectedgame = view.getGames().get(index);
        view.showPreviewGame();
        view.setMessage("Game selected: " + selectedgame);

//        GameMessage<String> msg = new GameMessage<>("avatar-request",avatarPath);
//        AppClient.getClient().send(new Gson().toJson(msg));
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

    public TextButton getSelectedButton() {
        return selectedButton;
    }
}
