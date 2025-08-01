package com.CEliconValley.models.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class FakeCheckbox extends TextButton {
    private boolean checked = false;

    public FakeCheckbox(String label, Skin skin) {
        super("☐ " + label, skin);
        getLabel().setColor(Color.YELLOW);
        updateText();

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                checked = !checked;
                updateText();
            }
        });
    }

    private void updateText() {
        setText((checked ? "☑ " : "☐ ") + getLabel().getText().toString().substring(2));
        if(checked){
            setColor(Color.GREEN);
            getLabel().setColor(Color.YELLOW);
        } else{
            setColor(Color.GRAY);
            getLabel().setColor(Color.GRAY);
        }
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        updateText();
    }
}
