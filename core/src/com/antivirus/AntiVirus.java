package com.antivirus;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class AntiVirus extends Game implements ApplicationListener {
    //Menu Class
    public static MenuScreen menuScreen;
    //Level1 Class
    public static Lvl1 level1;


    @Override
    public void create() {
        Gdx.app.log("AntiVirus: ", "create");
        level1 = new Lvl1(this);
        menuScreen = new MenuScreen(this);
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


