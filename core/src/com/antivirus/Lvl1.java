package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

import java.util.ArrayList;

import Projectiles.Bullet;
import Units.Player;
import Units.Trojan;
import Units.Worm;

/**
 * Created by Caroline on 22/03/2016.
 */
public class Lvl1 implements Screen, InputProcessor{
    AntiVirus game;

    //Player
    public Player player;
    public Sprite playerSprite;

    //Enemy (Worm)
    ArrayList<Worm> worms;
    float wormSpawnTime;
    int wormsSpawned;
    int wormWidth;
    int wormHeight;
    Worm removeWorm;


    //Enemy (Trojan)
    ArrayList<Trojan> trojans;
    float trojanSpawnTime;
    int trojansSpawned;
    int trojanWidth;
    int trojanHeight;
    Trojan removeTrojan;

    //Screen size?
    public static int WIDTH;
    public static int HEIGHT;

    //Creates the camera that will view the game
    public static OrthographicCamera camera;

    SpriteBatch batch;
    Texture img;
    TextureRegion texture;
    //Sprite sprite;

    //Touch mechanics
    Vector2 origin = new Vector2();

    //Movement cooldowns
    float movementCd;
    float bulletCd;
    float spawnCd;
    float elapsedTime;
    float lastTime;

    //Bullet array
    ArrayList<Bullet> bullets;
    Bullet removeBullet;

    //Background
    Image bg1;
    Image bg2;
    int posXBackground1;
    int posXBackground2;

    public Lvl1(AntiVirus game){this.game = game;}

    public void create() {
        Gdx.app.log("Lvl1: ", "level1 create");

        //Gets width and height of screen, ans sets them to variables
        WIDTH = Gdx.graphics.getWidth();
        Gdx.app.log("Lvl1: ", "WIDTH: " + WIDTH);
        HEIGHT = Gdx.graphics.getHeight();
        Gdx.app.log("Lvl1: ", "HEIGHT: " + HEIGHT);

        //Set camera to screen size
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        //Moves camera from focusing on origin, to focusing on centre of game screen
        camera.translate(WIDTH / 2, HEIGHT / 2);
        camera.update();

        //Player
        player = new Player();
        playerSprite = player.getSprite();
        //sets player in middle of screen, 1/4th of the way up from the bottom
        playerSprite.setPosition(WIDTH/2 - (playerSprite.getWidth()/2), HEIGHT/4 - (playerSprite.getHeight()/2));
        //Creates collision box for player
        player.setBounds(new Rectangle(playerSprite.getX(), playerSprite.getY(), player.getSprite().getWidth(), player.getSprite().getHeight()));

        //Enemy (Worm)
        worms = new ArrayList<Worm>();
        wormSpawnTime = 0.0f;
        wormsSpawned = 0;
        wormWidth = 50;
        wormHeight = HEIGHT;

        //Enemy (Trojan)
        trojans = new ArrayList<Trojan>();
        trojanSpawnTime = 0.0f;
        trojansSpawned = 0;
        trojanWidth = WIDTH / 4;
        trojanHeight = HEIGHT;


        //creating sprite
        batch = new SpriteBatch();

        //creating background texture and image
        texture = new TextureRegion(new Texture("Background.jpg"));
        bg1 = new Image(texture);
        bg2 = new Image(texture);
        bg1.setPosition(0, 0);
        bg2.setPosition(0, bg1.getHeight());
        posXBackground1 = 0;
        posXBackground2 = (int) bg1.getHeight();

        //Sets movement processor
        movementCd = 0.0f;
        bulletCd = 0.0f;
        spawnCd = 0.0f;

        //Initializing bullets
        //playerBullet = new Bullet(playerSprite.getX(), playerSprite.getY(), player.getId(), player.getDamage());
        bullets = new ArrayList<Bullet>();

        //Sets input for touch, click, etc.
        Gdx.input.setInputProcessor(this);
    }

    public void render(float f){
        //Clear screen white
//        Gdx.gl.glClearColor(1, 1, 1, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //update method
        update();

        long currentTime = System.currentTimeMillis();
        //Divide by a thousand to convert from milliseconds to seconds
        elapsedTime = (currentTime - lastTime) % 1000.0f;
        lastTime = currentTime;

        batch.begin();

        bg1.draw(batch, 1);
        bg2.draw(batch, 1);
        playerSprite.draw(batch);

        //Draws bullets if bullet array is holding bullet objects
        if(bullets.size() > 0){
            for(Bullet bullet : bullets){
                bullet.getSprite().draw(batch);
            }
        }

        //Draws worms if worm array is holding worm objects
        if (worms.size() > 0){
            for(Worm worm : worms){
                worm.getSprite().draw(batch);
            }
        }

        //Draws trojans is trojan array is holding trojan objects
        if (trojans.size() > 0){
            for (Trojan trojan: trojans){
                trojan.getSprite().draw(batch);
            }
        }
        batch.end();
    }

    public void update(){
        removeBullet = null;
        removeWorm = null;
        removeTrojan = null;



        //If movement cooldown is zero.
        if(movementCd <= 0.0f){
            spawnCd ++;
            //Gdx.app.log("Lvl1: ", "spawnCd: " + spawnCd);

            //Bullet update
            for(Bullet bullet: bullets) {
                bullet.getBounds().setPosition(bullet.getX(),bullet.getY() + 10);
                bullet.setY(bullet.getY() + 10);
                if (bullet.getY() > 1670) {
                    removeBullet = bullet;
                }
                //If bullet hits worm, remove worm
                for (Worm worm : worms) {
                    if (worm.getBounds().overlaps(bullet.getBounds())){
                        worm.setHp(worm.getHp() - bullet.getDamage());
                        removeBullet = bullet;
                        //If health is zero, remove it
                        if (worm.getHp() <= 0) {
                            removeWorm = worm;
                        }
                    }
                }
            }
            //remove shot worm
            if (removeWorm != null){
                worms.remove(removeWorm);
                removeWorm = null;
            }
            //remove out of bounds worms (worms that have moved past the screen)
            removeOutOfBoundsWorm();
            //remove bullet
            if(removeBullet != null){
                bullets.remove(removeBullet);
            }
            //Bullet shooting incrementer (See shooting in touch dragged)
            bulletCd++;

            //background
            animateBackground();

            //Move worm
            for (Worm worm: worms){
                worm.getBounds().setPosition(worm.getX(), worm.getY() - worm.getSpeed());
                worm.setY(worm.getY() - worm.getSpeed());
                worm.getSprite().setPosition(worm.getX(), worm.getY());
                if (worm.getY() > 1920){
                    removeWorm = worm;
                }
                //if worm collides with player remove player and worm section
                if (worm.getBounds().overlaps(player.getBounds())){
                    removeWorm = worm;
                }
            }
            if (removeWorm != null){
                worms.remove(removeWorm);
            }

            //Worm Enemy
            //Spawns worm
            if (spawnCd == 100){
                spawnWorm();
            }
//            while (worms.size() == 10){
//
//            }

            if (spawnCd == 1000){
                spawnWorm();
            }

            animateWorm();
        }


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

    //Movement methods
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        origin.set(screenX,screenY);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {

        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector2 movement = new Vector2(screenX,screenY);

        // delta will now hold the difference between the last and the current touch positions
        // delta.x > 0 means the touch moved to the right, delta.x < 0 means a move to the left
        Vector2 delta = movement.cpy().sub(origin);
        //Movement
        if(movementCd <= 0.0f){
            //Right
            if (delta.x > 50){
                if (playerSprite.getX() < (980)){
                    player.getBounds().setPosition(playerSprite.getX() + player.getSpeed(), playerSprite.getY());
                    playerSprite.setX(playerSprite.getX() + player.getSpeed());
                }
            }
            //Left
            if (delta.x < -50){
                if (playerSprite.getX() > 0){
                    player.getBounds().setPosition(playerSprite.getX() - player.getSpeed(), playerSprite.getY());
                    playerSprite.setX(playerSprite.getX() - player.getSpeed());
                }

            }
            //Down
            if (delta.y > 50){
                if (playerSprite.getY() > 0){
                    player.getBounds().setPosition(playerSprite.getX(), playerSprite.getY() - player.getSpeed());
                    playerSprite.setY(playerSprite.getY() - player.getSpeed());
                }
            }
            //Up
            if (delta.y < -50){
                if (playerSprite.getY() < (1670)){
                    player.getBounds().setPosition(playerSprite.getX(), playerSprite.getY() + player.getSpeed());
                    playerSprite.setY(playerSprite.getY() + player.getSpeed());
                }
            }
            //Static (centre)
            if((delta.x <= 50 || delta.x >= -50)
                    && (delta.y <= 50 || delta.y >= -50)){
                if (playerSprite.getX() == screenX){
                    if(playerSprite.getY() == screenY) {
                        origin.set(screenX,screenY);
                    }
                }
            }
        }
        if (movementCd > 0.0f) {
            movementCd -= elapsedTime;
        }

        //Shooting
        if (bulletCd >= 20){
            //Gdx.app.log("Lvl1: ", "Adding bullets!");
            if (bullets.size() <= 10){
                Bullet bullet = new Bullet(playerSprite.getX() + 75, playerSprite.getY() + 90, player.getId(), player.getDamage());
                bullet.setBounds(new Rectangle(playerSprite.getX() + 75, playerSprite.getY() + 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                bullets.add(bullet);
            }
            bulletCd = 0;
        }

        playerSprite.setPosition(playerSprite.getX(), playerSprite.getY());

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

    public void animateBackground(){
        bg1.setPosition(bg1.getX(), posXBackground1);
        bg2.setPosition(bg2.getX(), posXBackground2);
        //Gdx.app.log("Lvl1: ", "" + posXBackground1);
        if(posXBackground1 < -1920){
            //Gdx.app.log("Lvl1: ", "Got here! " + HEIGHT);
            bg1.setPosition(0,bg2.getHeight());
            posXBackground1 = (int)bg2.getHeight();
            posXBackground1 -= 5;
        }
        else if(posXBackground2 < -1920){
            bg2.setPosition(0,bg1.getHeight());
            posXBackground2 = (int) bg1.getHeight();
            posXBackground2 -= 5;
        }
        else{
            posXBackground1 -= 5;
            posXBackground2 -= 5;
        }
    }

    public void spawnWorm(){
        while (wormsSpawned != 10){
            //Gdx.app.log("Lvl1: ", "Worm");
            Worm worm = new Worm();


            if (wormHeight == HEIGHT){
                worm.setY(HEIGHT);
                worm.setIsUp(false);
                wormHeight = HEIGHT + 100;
            }
            else{
                worm.setY(HEIGHT+100);
                worm.setIsUp(true);
                wormHeight = HEIGHT;
            }

            worm.setX(wormWidth);
            worm.getSprite().setPosition(worm.getX(), worm.getY());

            //Creates Bounding box
            worm.setBounds(new Rectangle(worm.getX(), worm.getY(), worm.getSprite().getWidth(), worm.getSprite().getHeight()));
            //Adds the worm to the arrayList
            worms.add(worm);

            wormWidth += 100;
            wormsSpawned++;
        }
    }

    public void animateWorm(){
        wormSpawnTime++;
        //Move worm sprite like it's wiggling
        if (wormSpawnTime == 50){
            for (Worm worm : worms){
                if (worm.getIsUp() == true){
                    worm.setY(worm.getY() - 100);
                    worm.setIsUp(false);
                }
                else if (worm.getIsUp() == false){
                    worm.setY(worm.getY() + 100);
                    worm.setIsUp(true);
                }
            }
            wormSpawnTime = 0;
        }
    }

    public void removeOutOfBoundsWorm(){
        removeWorm = null;

        for (Worm worm : worms){
            if (worm.getY() < 0){
                removeWorm = worm;
            }
        }
        if (removeWorm != null){
            worms.remove(removeWorm);
        }
        //resets stats for spawnWorm
        if (worms.size() == 0){
            wormsSpawned = 0;
            wormHeight = HEIGHT;
            wormWidth = 50;
        }
    }
}
