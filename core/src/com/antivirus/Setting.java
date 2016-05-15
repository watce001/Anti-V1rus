package com.antivirus;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by Tony on 13/05/2016.

public class Setting implements Screen {
    boolean musicOff;
    boolean musicOn;
    private SpriteBatch batch;
    private Skin skin;
    BitmapFont font;
    String txt;
    GlyphLayout layout;

    public void create(){
        batch = new SpriteBatch();
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        txt = "anti virus";
        //Centers font text
        layout = new GlyphLayout();
        layout.setText(font, txt);
    }
}
 */

