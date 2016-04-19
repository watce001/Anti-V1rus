package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import javafx.stage.Stage;

/**
 * Created by Caroline on 15/04/2016.
 */
public class LevelSelect implements Screen {
    AntiVirus game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    public void create(){
        Gdx.app.log("LevelSelect: ", "levelSelect create");
    }

    public void render (float f){
        Gdx.app.log("LevelSelect: ", "levelSelect render");
    }

    @Override
    public void dispose(){}
    @Override
    public void resize(int width, int height){}
    @Override
    public void pause(){}
    @Override
    public void resume(){}

    @Override
    public void show(){
        Gdx.app.log("LevelSelect: ", "levelSelect show called");
    }

    @Override
    public void hide(){
        Gdx.app.log("LevelSelect: ", "levelSelect hide called");
    }
}
