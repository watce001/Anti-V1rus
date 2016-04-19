package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Units.Player;

/**
 * Created by Caroline on 22/03/2016.
 */
public class Lvl1 implements Screen, InputProcessor{
    AntiVirus game;
    Player player;

    public Sprite playerSprite;
    //Screen size?
    public static  int WIDTH;
    public static int HEIGHT;

    //Creates the camera that will view the game
    public static OrthographicCamera camera;

    SpriteBatch batch;
    Texture img;
    Sprite sprite;

    public Lvl1(AntiVirus game){this.game = game;}

    public void create() {
        Gdx.app.log("Lvl1: ", "level1 create");

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        //Set camera to screen size
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        //Moves camera from focusing on origin, to focusing on centre of game screen
        camera.translate(WIDTH/2, HEIGHT/2);
        camera.update();

        player = new Player();
        playerSprite = player.getSprite();
        playerSprite.setPosition(WIDTH/2, HEIGHT/2);

        //creating sprite
        batch = new SpriteBatch();
//        img = new Texture("PlayerTest.png");
//        sprite = new Sprite(img);
//        sprite.setPosition(Gdx.graphics.getWidth()/2 - sprite.getWidth(),
//                Gdx.graphics.getHeight()/2 - sprite.getHeight());

        //Sets movement processor
        Gdx.input.setInputProcessor(this);
    }

    public void render(float f){
        Gdx.app.log("Lvl1: ", "level1 render");

        //Clear screen white
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        playerSprite.draw(batch);
        //batch.draw(sprite, sprite.getX(), sprite.getY());
        batch.end();
    }

    @Override
    public void dispose(){
        img.dispose();
        batch.dispose();
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause(){}
    @Override
    public void resume() {}

    @Override
    public void show(){
        Gdx.app.log("Lvl1: ", "level1 show called");
        create();
    }
    @Override
    public void hide(){
        Gdx.app.log("Lvl1: ", "level1 hide called");
    }


    //Movement methods

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {


        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
