package com.antivirus;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

/**
 * Created by Caroline on 22/03/2016.
 * Co-Authored by Corey
 */
public class AntiVirus extends Game implements ApplicationListener {
    //Menu Class
    public static MenuScreen menuScreen;
    //Level Select Class
    public static LevelSelect levelSelectScreen;
    //Level1 Class
    public static GameClass level1;


    @Override
    public void create() {
        Gdx.app.log("AntiVirus: ", "create");
        level1 = new GameClass(this);
        menuScreen = new MenuScreen(this);
        levelSelectScreen = new LevelSelect(this);
        Gdx.app.log("AntiVirus: ", "About to change screen to menuScreen");
        //change screen to menu
        setScreen(menuScreen);
        Gdx.app.log("AntiVirus: ", "Changed screen to menuScreen");

    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    //This method calls the super class render
    //which in turn calls the render of the actual screen being used
    public void render() {
        super.render();
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }
}


