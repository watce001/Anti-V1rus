package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

import Projectiles.Bullet;
import Units.MemoryLeak;
import Units.Player;
import Units.Trojan;
import Units.Worm;


/**
 * Created by Caroline on 22/03/2016.
 */
public class GameClass implements Screen, InputProcessor{

    public enum GameState {PLAYING, PAUSED, COMPLETE, GAMEOVER}
    GameState gameState = GameState.PLAYING;

    InputMultiplexer inputMultiplexer;
    //Screen size
    public static int WIDTH;
    public static int HEIGHT;

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
    //Big trojan
    Trojan bigTrojan;
    Sprite trojanSprite;
    //Small trojans
    ArrayList<Trojan> trojans;
    float trojanSpawnTime;
    int trojansSpawned;
    int trojanWidth;
    int trojanHeight;
    Trojan removeTrojan;

    //Enemy (MemLeak)
    ArrayList<MemoryLeak> memLeaks;
    float memLeakSpawnTime;
    int memLeaksSpawned;
    int memLeakWidth;
    int memLeakHeight;
    MemoryLeak removeMemLeak;

    //Touch mechanics
    Vector2 origin = new Vector2();

    //Movement cooldowns
    float movementCd;
    float playerBulletCd;
    float wormBulletCd;
    float trojanBulletCd;
    float memLeakBulletCd;
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

    //Creates the camera that will view the game
    public static OrthographicCamera camera;

    //UI Data
    SpriteBatch batch;
    Texture img;
    TextureRegion texture;
    //Sprite sprite;

    //Overlay
    Pixmap pixmap;
    TextureRegion pauseBackground;
    Image overlay;

    //In-Game Pause Button
    private Skin skin;
    private Stage stage;
    TextButton pauseButton;

    //Game Over Text
    BitmapFont font;
    String txt;
    GlyphLayout layout;

    //Game Complete Text
    String complete;
    GlyphLayout completeLayout;

    //Gameover/complete time
    long startTime;
    long countdown;

    public GameClass(AntiVirus game){this.game = game;}

    public void create() {
        Gdx.app.log("GameClass: ", "level1 create");

        //Gets width and height of screen, ans sets them to variables
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        //Set camera to screen size
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        //Moves camera from focusing on origin, to focusing on centre of game screen
        camera.translate(WIDTH / 2, HEIGHT / 2);
        camera.update();

        //Player
        player = new Player();
        playerSprite = player.getSprite();
        //sets player in middle of screen, 1/4th of the way up from the bottom
        playerSprite.setPosition(WIDTH / 2 - (playerSprite.getWidth() / 2), HEIGHT / 4 - (playerSprite.getHeight() / 2));
        //Creates collision box for player
        player.setBounds(new Rectangle(playerSprite.getX(), playerSprite.getY(), player.getSprite().getWidth(), player.getSprite().getHeight()));

        //Enemy (Worm)
        worms = new ArrayList<Worm>();
        wormSpawnTime = 0.0f;
        wormsSpawned = 0;
        wormWidth = 50;
        wormHeight = HEIGHT;

        //Enemy (Trojan)
        //Baby trojans
        trojans = new ArrayList<Trojan>();
        trojanSpawnTime = 0.0f;
        trojansSpawned = 0;
        trojanWidth = WIDTH / 4;
        trojanHeight = HEIGHT;

        //Enemy (MemLeak)
        memLeaks = new ArrayList<MemoryLeak>();
        memLeakSpawnTime = 0.0f;
        memLeaksSpawned = 0;
        memLeakWidth = WIDTH/4;
        memLeakHeight = HEIGHT;

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
        playerBulletCd = 0.0f;
        wormBulletCd = 0.0f;
        trojanBulletCd = 0.0f;
        memLeakBulletCd = 0.0f;
        spawnCd = 0.0f;

        //Initializing bullets
        //playerBullet = new Bullet(playerSprite.getX(), playerSprite.getY(), player.getId(), player.getDamage());
        bullets = new ArrayList<Bullet>();

        //Overlay for other screens
        pixmap = new Pixmap(0,0,Pixmap.Format.RGB888);
        pauseBackground = new TextureRegion(new Texture(pixmap));
        overlay = new Image(pauseBackground);
        pixmap.setColor(1, 0, 0, 05f);
        pixmap.fill();
        overlay.setWidth(WIDTH);
        overlay.setHeight(HEIGHT);
        pauseBatch = new SpriteBatch();

        //Creates button to pause game
        stage = new Stage();
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        pauseButton = new TextButton("||",skin,"default");
        pauseButton.getLabel().setFontScale(3);
        pauseButton.setWidth(Gdx.graphics.getWidth() / 8);
        pauseButton.setHeight(pauseButton.getWidth());
        pauseButton.setPosition(WIDTH - pauseButton.getWidth(), HEIGHT - pauseButton.getHeight());

        pauseButton.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.log("GameState: ", "About to call pauseButton");
                pauseScreenCreate();
                gameState = GameState.PAUSED;
                Gdx.app.log("GameState: ", "Level Paused");
            }
        });
        pauseButton.toFront();
        stage.addActor(pauseButton);
        //Game Over Screen
        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        txt = "Game Over!";
        layout = new GlyphLayout();
        layout.setText(font, txt);

        //Game Complete
        complete = "Level Complete!";
        completeLayout = new GlyphLayout();
        completeLayout.setText(font, complete);

        //GameOver/Complete time
        startTime = 0;
        countdown = 0;

        //Managing input from both stage, and sprite batch
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(this);

        //Sets input for touch, click, etc.
        Gdx.input.setInputProcessor(inputMultiplexer);
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

        //Draws trojans if trojan array is holding trojan objects
        if (trojans.size() > 0){
            if (bigTrojan == null){
                for (Trojan trojan: trojans){
                    trojan.getSprite().draw(batch);
                }
            }
            else if(bigTrojan != null){
                bigTrojan.getSprite().draw(batch);
            }
        }
        //Draws memLeaks if memLeak array is holding memLeak objects
        if (memLeaks.size() > 0){
            for (MemoryLeak memLeak : memLeaks){
                memLeak.getSprite().draw(batch);
            }
        }

        switch (gameState){
            case PLAYING:
                pauseButton.draw(batch, 1);
                stage.draw();
                break;
            case PAUSED:
                //Fill in pause code
                pauseScreenRender();
                break;
            case GAMEOVER:
                countdown = ((System.currentTimeMillis() - startTime) / 1000);
                Gdx.app.log("Seconds Elapsed: ", "" + ((System.currentTimeMillis() - startTime) / 1000));
                overlay.draw(batch, 0.5f);
                font.draw(batch, txt, WIDTH/2 - layout.width/2, HEIGHT/2 + layout.height/2);
                if (countdown == 5){
                    gameState = GameState.PLAYING;
                    game.setScreen(AntiVirus.levelSelectScreen);
                }
                break;
            case COMPLETE:
                countdown = ((System.currentTimeMillis() - startTime) / 1000);
                Gdx.app.log("Seconds Elapsed: ", "" + ((System.currentTimeMillis() - startTime) / 1000));
                overlay.draw(batch, 0.5f);
                font.draw(batch, complete, WIDTH/2 - completeLayout.width/2, HEIGHT/2 + completeLayout.height/2);
                if (countdown == 5){
                    gameState = GameState.PLAYING;
                    game.setScreen(AntiVirus.levelSelectScreen);
                }
                break;
        }
        batch.end();
    }

    public void update(){
        removeBullet = null;
        removeWorm = null;
        removeTrojan = null;


        switch (gameState){
            case PLAYING:
                //If movement cooldown is zero.
                if(movementCd <= 0.0f){
                    //Spawing time, for when we want a specific enemy to spawn
                    spawnCd ++;

                    checkPlayerHealth();

                    //FOR FIGURING OUT WHEN TO SPAWN THINGS
                    Gdx.app.log("GameClass: ", "spawnCd: " + spawnCd);

                    //Shooting player bullets
                    playerBulletSpawn();

                    //Bullets
                    bulletUpdate();

                    //background
                    animateBackground();

                    //update bullets;
                   // wormBulletUpdate();

                    if (worms.size() > 0){
                        //Shooting worm bullets
                        wormBulletSpawn();
                        //Move worm:
                        moveWorm();
                        //remove out of bounds worms (worms that have moved past the screen)
                        removeOutOfBoundsWorm();
                    }

                    if (trojans.size() > 0){
                        //Shooting trojan bullets
                        trojanBulletSpawn();
                        //Move trojan
                        moveTrojans();
                        //remove out of bounds trojans (trojans that have moved past the screen)
                        removeOutOfBoundsTrojan();
                    }

                    if (memLeaks.size() > 0){
                        //Spawning memLeak bullets
                        memLeakBulletSpawn();
                        //Move memLeak
                        moveMemLeak();
                        //remove out of bounds memory leaks ( memLeaks that have moved past the screen)
                        removeOutOfBoundsMemLeak();
                    }


                    //SPAWNING
                    //Worm speed = 3
                    //Trojan speed = 2
                    //MemLeak speed = 4

                    //Spawns worm
                    if (spawnCd == 100){
                        spawnWorm(5, HEIGHT, 0);
                    }
                    //Spawns Trojan
                    if (spawnCd == 500){
                        spawnTrojans(WIDTH/2, HEIGHT, 0,0);
                    }

                    //Spawns memLeak
                    if (spawnCd == 1000){
                        spawnMemLeaks(150,HEIGHT,0);
                    }

                    if (spawnCd == 1500){
                        spawnWorm(WIDTH, HEIGHT, -5);
                    }
                    if (spawnCd == 2000){
                        spawnMemLeaks(0 - 500, HEIGHT - HEIGHT / 5, 2);
                    }
                    if (spawnCd == 2500){
                        spawnTrojans(0, HEIGHT, 3, -3);
                    }
                    if (spawnCd == 3500){
                        Gdx.input.setInputProcessor(stage);
                        startTime = System.currentTimeMillis();
                        gameState = GameState.COMPLETE;
                    }
                    animateWorm();
                }
                break;
            case COMPLETE:
                //Fill in complete code
                break;
            case GAMEOVER:
                //Fill in game over code
                break;
            case PAUSED:
                //Got to set inputProcessor to this stage
                break;
        }
    }

    @Override
    public void dispose(){
        img.dispose();
        batch.dispose();
        game.dispose();
    }
    @Override
    public void resize(int width, int height) {}
    @Override
    public void pause(){}
    @Override
    public void resume() {}

    @Override
    public void show(){
       Gdx.app.log("GameClass: ", "level1 show called");
        create();
    }
    @Override
    public void hide(){
       Gdx.app.log("GameClass: ", "level1 hide called");
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
        origin.set(screenX, screenY);
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
                    player.getBounds().setPosition(playerSprite.getX() + player.getxSpeed(), playerSprite.getY());
                    playerSprite.setX(playerSprite.getX() + player.getxSpeed());
                }
            }
            //Left
            if (delta.x < -50){
                if (playerSprite.getX() > 0){
                    player.getBounds().setPosition(playerSprite.getX() - player.getxSpeed(), playerSprite.getY());
                    playerSprite.setX(playerSprite.getX() - player.getxSpeed());
                }

            }
            //Down
            if (delta.y > 50){
                if (playerSprite.getY() > 0){
                    player.getBounds().setPosition(playerSprite.getX(), playerSprite.getY() - player.getySpeed());
                    playerSprite.setY(playerSprite.getY() - player.getySpeed());
                }
            }
            //Up
            if (delta.y < -50){
                if (playerSprite.getY() < (1670)){
                    player.getBounds().setPosition(playerSprite.getX(), playerSprite.getY() + player.getySpeed());
                    playerSprite.setY(playerSprite.getY() + player.getxSpeed());
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

    //PLAYER

    public void checkPlayerHealth(){
        //Gdx.app.log("Player Health: ", "" + player.getHp());
        if (player.getHp() <= 0){
            Gdx.input.setInputProcessor(stage);
            startTime = System.currentTimeMillis();
            gameState = GameState.GAMEOVER;
        }
    }

    //BACKGROUND
    public void animateBackground(){
        bg1.setPosition(bg1.getX(), posXBackground1);
        bg2.setPosition(bg2.getX(), posXBackground2);
        //Gdx.app.log("GameClass: ", "" + posXBackground1);
        if(posXBackground1 < -1920){
            //Gdx.app.log("GameClass: ", "Got here! " + HEIGHT);
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

    //BULLETS
    public void bulletUpdate(){
        for (Bullet bullet: bullets){
            //Player
            if (bullet.getId() == 1){
                playerBulletUpdate(bullet);
            }
//            //Worm
//            else if (bullet.getId() == 2){
//                wormBulletUpdate(bullet);
//            }
//            //BigTrojan
//            else if (bullet.getId() == 3){
//                trojanBulletUpdate(bullet);
//            }
//            //SmallTrojan
//            else if (bullet.getId() == 4){
//                trojanBulletUpdate(bullet);
//            }
//            else if (bullet.getId() == 5){
//                memLeakBulletUpdate(bullet);
//            }
            else{
                enemyBulletUpdate(bullet);
            }
        }
        //remove shot worm
        if (removeWorm != null){
            worms.remove(removeWorm);
            removeWorm = null;
        }
        //remove shot memLeak
        if (removeMemLeak != null){
            memLeaks.remove(removeMemLeak);
            removeMemLeak = null;
        }
        //remove shot trojan
        if (removeTrojan != null){
            trojans.remove(removeTrojan);
            removeTrojan = null;
        }
        //remove bullet
        if(removeBullet != null){
            bullets.remove(removeBullet);
        }
    }
    //PLAYER BULLETS

    public void playerBulletSpawn(){
        //Shooting
        //Gdx.app.log("GameClass: ", "Adding bullets!" + bullets.size());
        if (playerBulletCd >= 20){

            if (bullets.size() <= 10){

                Bullet bullet = new Bullet(playerSprite.getX() + 75, playerSprite.getY() + 90, player.getId(), player.getDamage());
                bullet.setBounds(new Rectangle(playerSprite.getX() + 75, playerSprite.getY() + 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                bullets.add(bullet);
            }
            playerBulletCd = 0;
        }
        //Bullet shooting incrementer (See shooting in touch dragged)
        playerBulletCd++;
    }

    public void playerBulletUpdate(Bullet bullet){
        //Bullet update
        bullet.getBounds().setPosition(bullet.getX(),bullet.getY() + 10);
        bullet.setY(bullet.getY() + 10);
        if (bullet.getY() > 1670) {
            removeBullet = bullet;
        }
        //WORM
        //If bullet hits worm, worm loses health
        if (worms.size() >0){
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
        //MEMLEAK
        //If bullet hits memory leak, memleak loses health
        if (memLeaks.size() > 0){
            for (MemoryLeak memleak : memLeaks){
                if (memleak.getBounds().overlaps(bullet.getBounds())){
                    memleak.setHp(memleak.getHp() - bullet.getDamage());
                    removeBullet = bullet;
                    //if health is zero, remove it
                    if (memleak.getHp() <= 0){
                        removeMemLeak = memleak;
                    }
                }
            }
        }
        //TROJAN
        //If bullet hits trojan, trojan loses health
        //If there is no big trojan, there are only baby trojans to hit
        if (bigTrojan == null){
            if (trojans.size() > 0){
                for (Trojan trojan : trojans){
                    if (trojan.getBounds().overlaps(bullet.getBounds())){
                        trojan.setHp(trojan.getHp() - bullet.getDamage());
                        removeBullet = bullet;
                        if (trojan.getHp() <= 0){
                            removeTrojan = trojan;
                        }
                    }
                }
            }
        }
        //otherwise there is just the big trojan
        else{
            if(bigTrojan.getBounds().overlaps(bullet.getBounds())){
                bigTrojan.setHp(bigTrojan.getHp() - bullet.getDamage());
                removeBullet = bullet;
                if (bigTrojan.getHp() <= 0){
                    bigTrojan = null;
                    //Sets baby trojans to where big trojan died
                    for (Trojan trojan : trojans){
                        trojan.setY(trojanHeight);

                        trojan.setX(trojanWidth);
                        trojan.getSprite().setPosition(trojan.getX(), trojan.getY());

                        //Creates Bounding box
                        trojan.setBounds(new Rectangle(trojan.getX(), trojan.getY(), trojan.getSprite().getWidth(), trojan.getSprite().getHeight()));
                        trojanWidth += 200;
                    }
                }
            }
        }
    }

    //WORM BULLETS

    public void wormBulletSpawn(){
        //Shooting
        //Gdx.app.log("Worm bullets: ", "wormBulletCd: " + wormBulletCd);
        if (wormBulletCd >= 50){
            Random rand = new Random();
            int num;
            if (worms.size() > 1){
                num = rand.nextInt(worms.size()-1);
            }
            else{
                num = 0;
            }
            //Gdx.app.log("Worm bullets: ", "Rand num: " + num);
            Worm worm = worms.get(num);
            //Gdx.app.log("GameClass: ", "Adding bullets!");
            if (bullets.size() <= 10){
                Bullet bullet = new Bullet(worm.getSprite().getX() + worm.getSprite().getWidth()/2, worm.getSprite().getY() - 90, worm.getId(), worm.getDamage());
                bullet.setBounds(new Rectangle(worm.getSprite().getX() - 75, worm.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                bullets.add(bullet);
            }
            wormBulletCd = 0;
        }
        wormBulletCd ++;
    }

    //TROJAN BULLETS

    public void trojanBulletSpawn(){
        //Shooting
        //Gdx.app.log("Trojan bullets: ", "trojanBulletCd: " + trojanBulletCd);
        if (bigTrojan != null){
            //Gdx.app.log("Trojan bullets: ", "In big trojan bullet spawn");
            if (trojanBulletCd >= 150) {
                if (bullets.size() <= 10) {
                    Bullet bullet = new Bullet(bigTrojan.getSprite().getX() + bigTrojan.getSprite().getWidth()/2, bigTrojan.getSprite().getY() - 90, bigTrojan.getId(), bigTrojan.getDamage());
                    bullet.setBounds(new Rectangle(bigTrojan.getSprite().getX() + bigTrojan.getSprite().getWidth()/2, bigTrojan.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                    bullets.add(bullet);
                }
                trojanBulletCd = 0;
            }
        }
        else if (trojans.size() > 0){
            if (trojanBulletCd >= 100){
                Random rand = new Random();
                int num;
                if (trojans.size() > 1){
                    num = rand.nextInt(trojans.size()-1);
                }
                else{
                    num = 0;
                }
                //Gdx.app.log("Trojan bullets: ", "Rand num: " + num);
                Trojan trojan = trojans.get(num);
                //Gdx.app.log("Trojan bullets: ", "Trojan Sprite X: " + trojan.getSprite().getX());
                if (bullets.size() <= 10){
                    Bullet bullet = new Bullet(trojan.getSprite().getX() + trojan.getSprite().getWidth()/2, trojan.getSprite().getY() - 90, trojan.getId(), trojan.getDamage());
                    bullet.setBounds(new Rectangle(trojan.getSprite().getX() + trojan.getSprite().getWidth() / 2, trojan.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                    bullets.add(bullet);
                }
                trojanBulletCd = 0;
            }
        }
        trojanBulletCd ++;
    }

    //MEMLEAK BULLETS

    public void memLeakBulletSpawn(){
        //Shooting
        //Gdx.app.log("Worm bullets: ", "wormBulletCd: " + wormBulletCd);
        if (memLeakBulletCd >= 50){
            Random rand = new Random();
            int num;
            if (memLeaks.size() > 1){
                num = rand.nextInt(memLeaks.size()-1);
            }
            else{
                num = 0;
            }
            //Gdx.app.log("Worm bullets: ", "Rand num: " + num);
            MemoryLeak memLeak = memLeaks.get(num);
            //Gdx.app.log("GameClass: ", "Adding bullets!");
            if (bullets.size() <= 10){
                Bullet bullet = new Bullet(memLeak.getSprite().getX() + memLeak.getSprite().getWidth()/2, memLeak.getSprite().getY() - 90, memLeak.getId(), memLeak.getDamage());
                bullet.setBounds(new Rectangle(memLeak.getSprite().getX() - 75, memLeak.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                bullets.add(bullet);
            }
            memLeakBulletCd = 0;
        }
        memLeakBulletCd ++;
    }

    public void enemyBulletUpdate(Bullet bullet){
        //Bullet update
        bullet.getBounds().setPosition(bullet.getX(), bullet.getY()  - 10);
        bullet.setY(bullet.getY() - 10);
        if (bullet.getY() < 0) {
            removeBullet = bullet;
        }
        //PLAYER
        if (player.getBounds().overlaps(bullet.getBounds())){
            player.setHp(player.getHp() - bullet.getDamage());
            Gdx.app.log("Enemy Bullet Update: ", "Player Hit! Dmg: " + player.getHp());
            removeBullet = bullet;
        }
    }

    //WORMS
    public void spawnWorm(int x, int y, int xSpeed){
        int tempY = y;
        while (wormsSpawned != 10){
            //Gdx.app.log("GameClass: ", "Worm");
            Worm worm = new Worm();

            worm.setxSpeed(xSpeed);

            if (tempY == y){
                worm.setY(tempY);
                worm.setIsUp(false);
                tempY = y + 100;
            }
            else{
                worm.setY(tempY);
                worm.setIsUp(true);
                tempY = y;
            }

            worm.setX(x);
            worm.getSprite().setPosition(worm.getX(), worm.getY());

            //Creates Bounding box
            worm.setBounds(new Rectangle(worm.getX(), worm.getY(), worm.getSprite().getWidth(), worm.getSprite().getHeight()));
            //Adds the worm to the arrayList
            worms.add(worm);

            x += 100;
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

    public void moveWorm(){
        //Move worm
        for (Worm worm: worms){
            worm.getBounds().setPosition(worm.getX() - worm.getxSpeed(), worm.getY() - worm.getySpeed());
            worm.setY(worm.getY() - worm.getySpeed());
            worm.setX(worm.getX() + worm.getxSpeed());
            worm.getSprite().setPosition(worm.getX(), worm.getY());
            //if worm collides with player remove player and worm section
            if (worm.getBounds().overlaps(player.getBounds())){
                removeWorm = worm;
                player.setHp(player.getHp() - (worm.getDamage()*10));
            }
        }
        if (removeWorm != null){
            worms.remove(removeWorm);
        }
    }

    public void removeOutOfBoundsWorm(){
        removeWorm = null;
        for (Worm worm : worms){
            if (worm.getY() < -100 || worm.getX() > WIDTH + 1000 || worm.getX() < -1000){
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

    //TROJANS
    public void spawnTrojans(int x, int y, int bigTrojansXSpeed, int smallTrojansXSpeed){
        //For big trojan

        bigTrojan = new Trojan();
        //trojanSprite = bigTrojan.getSprite();
        bigTrojan.setxSpeed(bigTrojansXSpeed);
        bigTrojan.setX(x - bigTrojan.getSprite().getWidth()/2);
        bigTrojan.setY(y);
        bigTrojan.getSprite().setPosition(x, y);
        bigTrojan.setBounds(new Rectangle(x, y, bigTrojan.getSprite().getWidth(), bigTrojan.getSprite().getHeight()));

        x = (int)bigTrojan.getX() - 150;
        //For the small Trojans
        while (trojansSpawned != 3){
            //Gdx.app.log("GameClass: ", "Trojan");
            Trojan smallTrojan = new Trojan();

            smallTrojan.setxSpeed(smallTrojansXSpeed);
            smallTrojan.setY(trojanHeight);

            smallTrojan.setX(trojanWidth);
            smallTrojan.getSprite().setPosition(smallTrojan.getX(), smallTrojan.getY());

            //Creates Bounding box
            smallTrojan.setBounds(new Rectangle(smallTrojan.getX(), smallTrojan.getY(), smallTrojan.getSprite().getWidth(), smallTrojan.getSprite().getHeight()));
            //Adds the worm to the arrayList
            trojans.add(smallTrojan);

            trojanWidth += 150;
            trojansSpawned++;
        }

        for (Trojan trojan : trojans){
            trojan.setSmallTrojan();
        }
    }

    public void moveTrojans(){
        //Move trojan
        //Move baby trojans
        if (bigTrojan == null){
            for (Trojan trojan: trojans){
                //set X & Y to bigTrojan's death point.

                trojan.getBounds().setPosition(trojan.getX() - trojan.getxSpeed(), trojan.getY() - trojan.getySpeed());
                trojan.setY(trojan.getY() - trojan.getySpeed());
                trojan.setX(trojan.getX() + trojan.getxSpeed());
                trojan.getSprite().setPosition(trojan.getX(), trojan.getY());
                //if trojan collides with player remove player and worm section
                if (trojan.getBounds().overlaps(player.getBounds())){
                    removeTrojan = trojan;
                    player.setHp(player.getHp() - (trojan.getDamage()*5));
                }
            }
            if (removeTrojan != null){
                trojans.remove(removeTrojan);
            }
        }
        //Move big trojan
        else{
            bigTrojan.getBounds().setPosition(bigTrojan.getX() - bigTrojan.getxSpeed(), bigTrojan.getY() - bigTrojan.getySpeed());
            bigTrojan.setY(bigTrojan.getY() - bigTrojan.getySpeed());
            bigTrojan.setX(bigTrojan.getX() + bigTrojan.getxSpeed());
            bigTrojan.getSprite().setPosition(bigTrojan.getX(), bigTrojan.getY());
            trojanWidth = (int)bigTrojan.getX() - 150;
            trojanHeight = (int)bigTrojan.getY();
            if (bigTrojan.getBounds().overlaps(player.getBounds())){
                player.setHp(player.getHp() - (bigTrojan.getDamage()*10));
                bigTrojan = null;
                //Sets baby trojans to where big trojan died
                for (Trojan trojan : trojans){
                    trojan.setY(trojanHeight);

                    trojan.setX(trojanWidth);
                    trojan.getSprite().setPosition(trojan.getX(), trojan.getY());

                    //Creates Bounding box
                    trojan.setBounds(new Rectangle(trojan.getX(), trojan.getY(), trojan.getSprite().getWidth(), trojan.getSprite().getHeight()));
                    trojanWidth += 200;
                }
            }
        }
    }

    public void removeOutOfBoundsTrojan(){
        removeTrojan = null;
        if (bigTrojan != null){
            if (bigTrojan.getY() < - 300 || bigTrojan.getX() > WIDTH + 1000 || bigTrojan.getX() < -1000){
                bigTrojan = null;
                //Gdx.app.log("BigTrojan: ", "Removed big trojan!");
                //Sets baby trojans to where big trojan died
                for (Trojan trojan : trojans){
                    trojan.setY(trojanHeight);

                    trojan.setX(trojanWidth);
                    trojan.getSprite().setPosition(trojan.getX(), trojan.getY());

                    //Creates Bounding box
                    trojan.setBounds(new Rectangle(trojan.getX(), trojan.getY(), trojan.getSprite().getWidth(), trojan.getSprite().getHeight()));
                    trojanWidth += 200;
                }
            }
        }
        else {
            for (Trojan trojan : trojans){
                if (trojan.getY() < - 300 || trojan.getX() > WIDTH + 1000 || trojan.getX() < -1000){
                    removeTrojan = trojan;
                }
            }
            if (removeTrojan != null){
                trojans.remove(removeTrojan);
                //Gdx.app.log("Small Trojan: ", "Removed a small trojan!");
            }
            //resets stats for spawnWorm
            if (trojans.size() == 0){
                //Gdx.app.log("Trojan Array: ", "All trojans removed!");
                trojansSpawned = 0;
                trojanHeight = HEIGHT;
                trojanWidth = WIDTH/4;
            }
        }
    }

    //MEMORY LEAK
    public void spawnMemLeaks(int x, int y, int xSpeed){
        while (memLeaksSpawned != 3){
            //Gdx.app.log("GameClass: ", "Worm");
            MemoryLeak memLeak = new MemoryLeak();
            memLeak.setxSpeed(xSpeed);
            memLeak.setY(y);
            memLeak.setX(x);

            memLeak.getSprite().setPosition(x, y);

            //Creates Bounding box
            memLeak.setBounds(new Rectangle(x, y, memLeak.getSprite().getWidth(), memLeak.getSprite().getHeight()));
            //Adds the memory leak to the arrayList
            memLeaks.add(memLeak);

            x += 175;
            memLeaksSpawned++;
        }
    }

    public void moveMemLeak(){
        //Move memLeak
        for (MemoryLeak memLeak : memLeaks){
            memLeak.getBounds().setPosition(memLeak.getX() - memLeak.getxSpeed(), memLeak.getY() - memLeak.getySpeed());
            memLeak.setY(memLeak.getY() - memLeak.getySpeed());
            memLeak.setX(memLeak.getX() + memLeak.getxSpeed());
            memLeak.getSprite().setPosition(memLeak.getX(), memLeak.getY());
            //if memory leak collides with player, remove memoryleak
            if (memLeak.getBounds().overlaps(player.getBounds())){
                removeMemLeak = memLeak;
                player.setHp(player.getHp() - (memLeak.getDamage()*10));
            }
        }
        if (removeMemLeak != null){
            memLeaks.remove(removeMemLeak);
        }
    }

    public void removeOutOfBoundsMemLeak(){
        removeMemLeak = null;

        for (MemoryLeak memLeak : memLeaks){
            if (memLeak.getY() < - 300 || memLeak.getX() > WIDTH + 1000 || memLeak.getX() < -1000){
                removeMemLeak = memLeak;
            }
        }
        if (removeMemLeak != null){
            memLeaks.remove(removeMemLeak);
            //Gdx.app.log("MemLeak: ", "Removed memory leak!");
        }
        //resets stats for spawnWorm
        if (memLeaks.size() == 0){
            //Gdx.app.log("MemLeak: ", "All memory leaks removed!");
            memLeaksSpawned = 0;
            //memLeakHeight = HEIGHT;
            //memLeakWidth = WIDTH/4;
        }
    }


    //PAUSE SCREEN
    //Pause Screen Variables
    SpriteBatch pauseBatch;
    Stage pauseStage;
    TextButton resume;
    TextButton exit;

    private void pauseScreenCreate(){
        pauseStage = new Stage();


        //Add buttons
        resume = new TextButton("Resume", skin, "default");
        resume.getLabel().setFontScale(3);
        resume.setWidth(WIDTH / 2);
        resume.setHeight(WIDTH / 4);
        resume.setPosition(WIDTH / 2 - (resume.getWidth() / 2), (HEIGHT - (HEIGHT / 3)) - (resume.getHeight()/2));
        resume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(inputMultiplexer);
                gameState = GameState.PLAYING;
            }
        });
        resume.toFront();

        exit = new TextButton("Quit", skin, "default");
        exit.getLabel().setFontScale(3);
        exit.setWidth(WIDTH / 2);
        exit.setHeight(WIDTH / 4);
        exit.setPosition(WIDTH / 2 - (exit.getWidth() / 2), (HEIGHT / 3) - (exit.getHeight()/2));
        exit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(inputMultiplexer);
                gameState = GameState.PLAYING;
                game.setScreen(AntiVirus.levelSelectScreen);
            }
        });
        exit.toFront();

        pauseStage.addActor(resume);
        pauseStage.addActor(exit);
        Gdx.input.setInputProcessor(pauseStage);
    }

    private void pauseScreenRender(){
        overlay.draw(batch, 0.5f);
        resume.draw(batch, 1);
        exit.draw(batch, 1);
        pauseStage.draw();
    }

}