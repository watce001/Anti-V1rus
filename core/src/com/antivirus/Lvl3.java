package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Random;

import FX.ParticleManager;
import FX.SoundFXManager;
import Projectiles.ElissaFiles;
import Items.Files;
import Projectiles.Bullet;
import Units.Elissa;
import Units.MemoryLeak;
import Units.Player;
import Units.Trojan;
import Units.Worm;


/**
 * Created by Corey on 5/18/2016.
 */
public class Lvl3 implements Screen{

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
    boolean dotHappening;

    //Boss - Elissa
    ArrayList<Elissa> elissaArray;
    float elissaSpawnTime;
    int elissaSpawned;
    int elissaWidth;
    int elissaHeight;
    Elissa removeElissa;

    //Elissa Attack
    ArrayList<ElissaFiles> elissaFiles;
    ElissaFiles removeElissaFile;
    float playerDisableTime;

    //Touch mechanics
    //Vector2 origin = new Vector2();

    //Movement cooldowns
    float movementCd;
    float playerBulletCd;
    float wormBulletCd;
    float trojanBulletCd;
    float memLeakBulletCd;
    float elissaBulletCd;
    float elissaAttackCd;
    float spawnCd;
    float elapsedTime;
    float lastTime;
    float bossDefeatedCd;

    //Bullet array
    ArrayList<Bullet> bullets;
    Bullet removeBullet;

    //Item Array
    ArrayList<Files> files;
    Files removeFile;
    int fileScore;
    int totalFiles;

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

    //INGAME UI
    //In-Game Pause Button
    private Skin skin;
    private Stage stage;
    TextButton pauseButton;

    //In-Game Score
    BitmapFont uiFont;
    String scoreTxt;
    GlyphLayout scoreLayout;
    int score;

    //In_game Health
    String healthTxt;
    GlyphLayout healthLayout;

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

    //Movement (TEST)
    float touchX;
    float touchY;
    Vector2 lastTouch;
    Vector2 newTouch;
    boolean isTouched;
    boolean bugFix;
    int touchTime;

    //Game Background Music
    private Music musicBackground;

    //SoundFX
    private SoundFXManager sfx;

    //Particles
    private ParticleManager particles;

    public Lvl3(AntiVirus game){this.game = game;}

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

        //Music
        musicBackground = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        musicBackground.setLooping(true);
        musicBackground.play();

        //SoundFX
        sfx = new SoundFXManager();

        //Particle System
        particles = new ParticleManager();
        particles.init();

        //Player
        player = new Player();
        playerSprite = player.getSprite();
        //sets player in middle of screen, 1/4th of the way up from the bottom
        player.setX(WIDTH / 2 - (playerSprite.getWidth() / 2));
        player.setY(HEIGHT / 4 - (playerSprite.getHeight() / 2));
        playerSprite.setPosition(player.getX(),player.getY());
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
        dotHappening = false;

        //Boss - Elissa
        elissaArray = new ArrayList<Elissa>();
        elissaSpawnTime = 0.0f;
        elissaSpawned = 0;
        elissaWidth = 20;
        elissaHeight = HEIGHT;
        elissaFiles = new ArrayList<ElissaFiles>();


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
        elissaBulletCd = 0.0f;
        elissaAttackCd = 0.0f;
        spawnCd = 0.0f;
        bossDefeatedCd = 0.0f;

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

        //INGAME UI
        //Score
        score = 0;
        uiFont = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        uiFont.getData().setScale(0.5f,0.5f);
        scoreTxt = "Score: " + String.format("%06d", score);
        scoreLayout = new GlyphLayout();
        scoreLayout.setText(uiFont, scoreTxt);
        //Health
        healthTxt = "Score: " + player.getHp();
        healthLayout = new GlyphLayout();
        healthLayout.setText(uiFont, healthTxt);

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
//        inputMultiplexer.addProcessor(this);
        //TOUCH MOVEMENT
        touchX = 0;
        touchY = 0;
        lastTouch = new Vector2();
        newTouch = new Vector2();
        bugFix = false;
        touchTime = 0;


        //FILES
        fileScore = 0;
        files = new ArrayList<Files>();


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

        if (elissaArray.size() > 0){
            for (Elissa elissa : elissaArray){
                elissa.getSprite().draw(batch);
            }
        }

        //Draws ElissaFiles if elissaFiles array is holding files objects
        if (elissaFiles.size() > 0){
            for (ElissaFiles file : elissaFiles){
                file.getSprite().draw(batch);
            }
        }

        //Draws files if files array is holding files objects
        if (files.size() > 0){
            for (Files file : files){
                file.getSprite().draw(batch);
            }
        }

        //particle system
        particles.render(batch);

        switch (gameState){
            case PLAYING:
                //Score
                scoreTxt = "Score: " + String.format("%06d", score);
                uiFont.draw(batch, scoreTxt, 0, HEIGHT - (scoreLayout.height));
                //Health
                healthTxt = "Health: " + player.getHp();
                uiFont.draw(batch,healthTxt,WIDTH/2 , HEIGHT - (healthLayout.height));
                //MOVEMENT
                isTouched = Gdx.input.isTouched();
                //Gdx.app.log("Playing: ", "Is touched: " + isTouched);
                playerMovement();
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
                    musicBackground.dispose();
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
                    musicBackground.dispose();
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
        removeFile = null;
        removeElissa = null;

        switch (gameState){
            case PLAYING:
                //If movement cooldown is zero.
                if(movementCd <= 0.0f){
                    //Spawing time, for when we want a specific enemy to spawn
                    spawnCd ++;

                    checkPlayerHealth();

                    //FOR FIGURING OUT WHEN TO SPAWN THINGS
                    //Gdx.app.log("GameClass: ", "spawnCd: " + spawnCd);

                    //Shooting player bullets
                    playerBulletSpawn();

                    //Bullets
                    bulletUpdate();

                    //background
                    animateBackground();

                    if (dotHappening == true){
                        Gdx.app.log("Is True: ", "true");
                        damageOverTimeCollision();
                    }

                    //update bullets;
                    // wormBulletUpdate();

                    if (worms.size() > 0){
                        //Shooting worm bullets
                        wormBulletSpawn();
                        animateWorm();
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

                    if (elissaArray.size() > 0){
                        elissaBulletSpawn();
                        spawnCorruptData();
                        moveElissa();
                    }

                    if (files.size() > 0){
                        updateFiles();
                    }

                    //update particles
                    float deltaTime = Gdx.graphics.getDeltaTime();
                    particles.update(deltaTime);

                    //SPAWNING

                    //Spawns trojan
                    if (spawnCd == 100) {
                        spawnTrojans(WIDTH, HEIGHT, -2, 1);
                    }
                    //Spawns worm
                    if (spawnCd == 400){
                        spawnWorm(5, HEIGHT, 0);
                    }
                    //Spawns memLeak
                    if (spawnCd == 700){
                        spawnMemLeaks(150, HEIGHT, 1);
                    }
                    if (spawnCd == 1000){
                        spawnWorm(WIDTH, HEIGHT, -3);
                    }
                    if (spawnCd == 1200) {
                        if(worms.size() > 0) {
                            for(Worm worm : worms) {
                                worm.setxSpeed(-5);
                            }
                        }
                    }
                    if (spawnCd == 1300){
                        spawnMemLeaks(-500, HEIGHT, 2);
                    }
                    if (spawnCd == 1600){
                        spawnTrojans(0, HEIGHT, 3, -3);
                    }
                    if (spawnCd == 1900){
                        spawnWorm(WIDTH, HEIGHT, -5);
                    }
                    if (spawnCd == 2200){
                        spawnMemLeaks(WIDTH, HEIGHT, -2);
                    }
                    if (spawnCd == 2500){
                        spawnTrojans(WIDTH, HEIGHT, -3, 3);
                    }
                    if (spawnCd == 2800){
                        spawnWorm(-500, HEIGHT, 3);
                    }
                    if (spawnCd == 3100){
                        spawnElissa();
                    }
                    if (spawnCd > 3100 && elissaArray.size() == 0){
                        bossDefeatedCd ++;
                        if (bossDefeatedCd == 200){
                            Gdx.input.setInputProcessor(stage);
                            startTime = System.currentTimeMillis();
                            gameState = GameState.COMPLETE;
                        }
                    }
                    
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
        sfx.dispose();
        particles.dispose();
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

    //PLAYER
    public void checkPlayerHealth(){
        //Gdx.app.log("Player Health: ", "" + player.getHp());
        if (player.getHp() <= 0){
            sfx.playSound(SoundFXManager.Type.DEATH);
            Gdx.input.setInputProcessor(stage);
            startTime = System.currentTimeMillis();
            gameState = GameState.GAMEOVER;
            musicBackground.pause();
        }
    }

    public void playerMovement(){
        if (isTouched == true){
            newTouch.set(Gdx.input.getX(), HEIGHT - Gdx.input.getY());
            if (touchTime >= 1){
                //Gdx.app.log("Playing: " , "NTx: " + newTouch.x + " NTy: " + newTouch.y);
                //Gdx.app.log("Playing: " , "LTx: " + lastTouch.x + " LTy: " + lastTouch.y);
                touchX = newTouch.x - lastTouch.x;
                touchY = newTouch.y - lastTouch.y;
                //Gdx.app.log("Playing: ", "X: " + newTouch.x + " Y: " + newTouch.y);
                //Gdx.app.log("PLAYING", "differenceX:  " + touchX + " differenceY: " + touchX);
                if (touchX < 0){
                    if (player.getSprite().getX() > 0){
                        //Gdx.app.log("Left","");
                        player.setX(player.getX() + touchX);
                    }
                }
                else if (touchX > 0){
                    if (player.getSprite().getX() < (WIDTH - player.getSprite().getWidth())){
                        player.setX(player.getX() + touchX);
                    }
                    //Gdx.app.log("Right","");
                }

                if (touchY < 0){
                    if (player.getSprite().getY() > 0){
                        //Gdx.app.log("Down","");
                        player.setY(player.getY() + touchY);
                    }
                }
                else if (touchY > 0){
                    if (player.getSprite().getY() < (HEIGHT - player.getSprite().getHeight())){
                        //Gdx.app.log("Up","");
                        player.setY(player.getY() + touchY);
                    }
                }
                player.getBounds().setPosition(player.getX(), player.getY());
                playerSprite.setY(player.getY());
                playerSprite.setX(player.getX());

            }
            lastTouch.x = newTouch.x;
            lastTouch.y = newTouch.y;
            touchTime ++;
        }
        else{
            touchTime = 0;
        }

//        if (player.getSprite().getX() > WIDTH){
//            player.getSprite().setX(WIDTH);
//        }
//        else if (player.getSprite().getX() < 0){
//            player.getSprite().setX(0);
//        }
//        if (player.getSprite().getY() > HEIGHT){
//            player.getSprite().setY(HEIGHT);
//        }
//        else if (player.getSprite().getY() < 0){
//            player.getSprite().setY(0);
//        }
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

        for (ElissaFiles file : elissaFiles) {
            updateCorruptData(file);
        }
        if (removeElissaFile != null){
            elissaFiles.remove(removeElissaFile);
        }


        //remove shot worm
        if (removeWorm != null){
            spawnFiles(removeWorm.getFileDropCount(), removeWorm.getX() + (removeWorm.getSprite().getWidth()/2), removeWorm.getY() + removeWorm.getSprite().getHeight()/2);
            worms.remove(removeWorm);
            removeWorm = null;
        }
        //remove shot memLeak
        if (removeMemLeak != null){
            spawnFiles(removeMemLeak.getFileDropCount(), removeMemLeak.getX() + (removeMemLeak.getSprite().getWidth()/2), removeMemLeak.getY() + removeMemLeak.getSprite().getHeight()/2);
            memLeaks.remove(removeMemLeak);
            removeMemLeak = null;
        }
        //remove shot trojan
        if (removeTrojan != null){
            spawnFiles(removeTrojan.getFileDropCount(), removeTrojan.getX() + (removeTrojan.getSprite().getWidth()/2), removeTrojan.getY() + removeTrojan.getSprite().getHeight()/2);
            trojans.remove(removeTrojan);
            removeTrojan = null;
        }
        //Remove shot Elissa
        if (removeElissa != null){
            spawnFiles(removeElissa.getFileDropCount(), removeElissa.getX() + (removeElissa.getSprite().getWidth()/2), removeElissa.getY() + removeElissa.getSprite().getHeight());
            elissaArray.remove(removeElissa);
            removeElissa = null;
        }
        //remove bullet
        if(removeBullet != null){
            bullets.remove(removeBullet);
            removeBullet = null;
        }

    }
    //PLAYER BULLETS

    public void playerBulletSpawn(){
        //Shooting
        //Gdx.app.log("GameClass: ", "Adding bullets!" + bullets.size());
        if(!player.canShoot()) {
            if(spawnCd >= playerDisableTime){
                player.enableShoot();
            }
        }
        else if (playerBulletCd >= 10){

            if (bullets.size() <= 30){
                Bullet bullet = new Bullet(playerSprite.getX() + 75, playerSprite.getY() + 90, player.getId(), player.getDamage(), false);
                bullet.setBounds(new Rectangle(playerSprite.getX() + 75, playerSprite.getY() + 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                sfx.playSound(SoundFXManager.Type.SHOOT);
                int i = particles.spawn(ParticleManager.Type.MUZZLE_FLASH, player);
                particles.x[i] = player.getSprite().getWidth()/2;
                particles.y[i] = player.getSprite().getHeight();
                System.out.println("Player: x: " + player.getX() + " y: " + player.getY());
                bullets.add(bullet);
            }
            playerBulletCd = 0;
        }
        //Bullet shooting incrementer (See shooting in touch dragged)
        playerBulletCd++;
    }

    public void playerBulletUpdate(Bullet bullet){
        //Bullet update
        bullet.getBounds().setPosition(bullet.getX(),bullet.getY() + 20);
        bullet.setY(bullet.getY() + 20);
        if (bullet.getY() > 1670) {
            removeBullet = bullet;
        }
        //WORM
        //If bullet hits worm, worm loses health
        if (worms.size() >0){
            for (Worm worm : worms) {
                if (worm.getBounds().overlaps(bullet.getBounds())){
                    worm.setHp(worm.getHp() - bullet.getDamage());
                    for(int i = 0; i < 5; i++) {
                        int p = particles.spawn(ParticleManager.Type.IMPACT, worm);
                        particles.x[p] = (bullet.getX() + bullet.getSprite().getWidth()/2);
                        particles.y[p] = bullet.getY() + bullet.getSprite().getHeight();
                    }
                    sfx.playSound(SoundFXManager.Type.HIT);
                    removeBullet = bullet;
                    //If health is zero, remove it
                    if (worm.getHp() <= 0) {
                        score += worm.getPoints();
                        for(int i = 0; i < 10; i++) {
                            int p = particles.spawn(ParticleManager.Type.DATA, worm);
                            particles.x[p] = (worm.getX() + worm.getSprite().getWidth()/2);
                            particles.y[p] = worm.getY() + worm.getSprite().getHeight()/2;
                        }
                        int p = particles.spawn(ParticleManager.Type.EXPLOSION, worm);
                        particles.x[p] = (worm.getX() + worm.getSprite().getWidth()/2);
                        particles.y[p] = worm.getY() + worm.getSprite().getHeight()/2;

                        sfx.playSound(SoundFXManager.Type.DEATH);
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
                    for(int i = 0; i < 5; i++) {
                        int p = particles.spawn(ParticleManager.Type.IMPACT, memleak);
                        particles.x[p] = (bullet.getX() + bullet.getSprite().getWidth()/2);
                        particles.y[p] = bullet.getY() + bullet.getSprite().getHeight();
                    }
                    sfx.playSound(SoundFXManager.Type.HIT);
                    removeBullet = bullet;
                    //if health is zero, remove it
                    if (memleak.getHp() <= 0){
                        score += memleak.getPoints();
                        for(int i = 0; i < 10; i++) {
                            int p = particles.spawn(ParticleManager.Type.DATA, memleak);
                            particles.x[p] = (memleak.getX() + memleak.getSprite().getWidth()/2);
                            particles.y[p] = memleak.getY() + memleak.getSprite().getHeight()/2;
                        }
                        int p = particles.spawn(ParticleManager.Type.EXPLOSION, memleak);
                        particles.x[p] = (memleak.getX() + memleak.getSprite().getWidth()/2);
                        particles.y[p] = memleak.getY() + memleak.getSprite().getHeight()/2;
                        sfx.playSound(SoundFXManager.Type.DEATH);
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
                        for(int i = 0; i < 5; i++) {
                            int p = particles.spawn(ParticleManager.Type.IMPACT, trojan);
                            particles.x[p] = (bullet.getX() + bullet.getSprite().getWidth()/2);
                            particles.y[p] = bullet.getY() + bullet.getSprite().getHeight();
                        }
                        sfx.playSound(SoundFXManager.Type.HIT);
                        removeBullet = bullet;
                        if (trojan.getHp() <= 0){
                            score += trojan.getPoints();
                            for(int i = 0; i < 10; i++) {
                                int p = particles.spawn(ParticleManager.Type.DATA, trojan);
                                particles.x[p] = (trojan.getX() + trojan.getSprite().getWidth()/2);
                                particles.y[p] = trojan.getY() + trojan.getSprite().getHeight()/2;
                            }
                            int p = particles.spawn(ParticleManager.Type.EXPLOSION, trojan);
                            particles.x[p] = (trojan.getX() + trojan.getSprite().getWidth()/2);
                            particles.y[p] = trojan.getY() + trojan.getSprite().getHeight()/2;
                            sfx.playSound(SoundFXManager.Type.DEATH);
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
                for(int i = 0; i < 5; i++) {
                    int p = particles.spawn(ParticleManager.Type.IMPACT, bigTrojan);
                    particles.x[p] = (bullet.getX() + bullet.getSprite().getWidth()/2);
                    particles.y[p] = bullet.getY() + bullet.getSprite().getHeight();
                }
                sfx.playSound(SoundFXManager.Type.HIT);
                removeBullet = bullet;
                if (bigTrojan.getHp() <= 0){
                    score += bigTrojan.getPoints();
                    spawnFiles(bigTrojan.getFileDropCount(), bigTrojan.getX() + (bigTrojan.getSprite().getWidth()/2), bigTrojan.getY() + bigTrojan.getSprite().getHeight()/2);
                    for(int i = 0; i < 10; i++) {
                        int p = particles.spawn(ParticleManager.Type.DATA, bigTrojan);
                        particles.x[p] = (bigTrojan.getX() + bigTrojan.getSprite().getWidth()/2);
                        particles.y[p] = bigTrojan.getY() + bigTrojan.getSprite().getHeight()/2;
                    }
                    int p = particles.spawn(ParticleManager.Type.EXPLOSION, bigTrojan);
                    particles.x[p] = (bigTrojan.getX() + bigTrojan.getSprite().getWidth()/2);
                    particles.y[p] = bigTrojan.getY() + bigTrojan.getSprite().getHeight()/2;
                    sfx.playSound(SoundFXManager.Type.DEATH);
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
        //ELISSA
        if (elissaArray.size() > 0) {

            for (Elissa elissa : elissaArray){
                if (elissa.getBounds().overlaps(bullet.getBounds())){
                    elissa.setHp(elissa.getHp() - bullet.getDamage());
                    for(int i = 0; i < 5; i++) {
                        int p = particles.spawn(ParticleManager.Type.IMPACT, elissa);
                        particles.x[p] = (bullet.getX() + bullet.getSprite().getWidth()/2);
                        particles.y[p] = bullet.getY() + bullet.getSprite().getHeight();
                    }
                    sfx.playSound(SoundFXManager.Type.HIT);
                    removeBullet = bullet;
                    //If health is zero, remove it
                    if (elissa.getHp() <=0){
                        score += elissa.getPoints();
                        for(int i = 0; i < 10; i++) {
                            int p = particles.spawn(ParticleManager.Type.DATA, elissa);
                            particles.x[p] = (elissa.getX() + elissa.getSprite().getWidth()/2);
                            particles.y[p] = elissa.getY() + elissa.getSprite().getHeight()/2;
                        }
                        int p = particles.spawn(ParticleManager.Type.EXPLOSION, elissa);
                        particles.x[p] = (elissa.getX() + elissa.getSprite().getWidth()/2);
                        particles.y[p] = elissa.getY() + elissa.getSprite().getHeight()/2;
                        sfx.playSound(SoundFXManager.Type.DEATH);
                        removeElissa = elissa;
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
            if (bullets.size() <= 30){
                Bullet bullet = new Bullet(worm.getSprite().getX() + worm.getSprite().getWidth()/2, worm.getSprite().getY() - 90, worm.getId(), worm.getDamage(), true);
                bullet.setBounds(new Rectangle(worm.getSprite().getX() - 75, worm.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                sfx.playSound(SoundFXManager.Type.SHOOT);
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
                if (bullets.size() <= 30) {
                    Bullet bullet = new Bullet(bigTrojan.getSprite().getX() + bigTrojan.getSprite().getWidth()/2, bigTrojan.getSprite().getY() - 90, bigTrojan.getId(), bigTrojan.getDamage(), true);
                    bullet.setBounds(new Rectangle(bigTrojan.getSprite().getX() + bigTrojan.getSprite().getWidth()/2, bigTrojan.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                    sfx.playSound(SoundFXManager.Type.SHOOT);
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
                if (bullets.size() <= 30){
                    Bullet bullet = new Bullet(trojan.getSprite().getX() + trojan.getSprite().getWidth()/2, trojan.getSprite().getY() - 90, trojan.getId(), trojan.getDamage(), true);
                    bullet.setBounds(new Rectangle(trojan.getSprite().getX() + trojan.getSprite().getWidth() / 2, trojan.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                    sfx.playSound(SoundFXManager.Type.SHOOT);
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
            if (bullets.size() <= 30){
                Bullet bullet = new Bullet(memLeak.getSprite().getX() + memLeak.getSprite().getWidth()/2, memLeak.getSprite().getY() - 90, memLeak.getId(), memLeak.getDamage(), true);
                bullet.setBounds(new Rectangle(memLeak.getSprite().getX() - 75, memLeak.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                sfx.playSound(SoundFXManager.Type.SHOOT);
                bullets.add(bullet);
            }
            memLeakBulletCd = 0;
        }
        memLeakBulletCd ++;
    }

    //ELISSA BULLETS
    public void elissaBulletSpawn(){
        //Shooting
        if (elissaBulletCd >= 100){
            Random rand = new Random();
            int num;
            if (elissaArray.size() > 1){
                num = rand.nextInt(elissaArray.size()-1);
            }
            else{
                num = 0;
            }
            //Gdx.app.log("Worm bullets: ", "Rand num: " + num);
            Elissa elissa = elissaArray.get(num);
            //Gdx.app.log("GameClass: ", "Adding bullets!");
            if (bullets.size() <= 30){
                Bullet bullet = new Bullet(elissa.getSprite().getX() + elissa.getSprite().getWidth()/2, elissa.getSprite().getY() - 90, elissa.getId(), elissa.getDamage(), true);
                bullet.setBounds(new Rectangle(elissa.getSprite().getX() - 75, elissa.getSprite().getY() - 90, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                sfx.playSound(SoundFXManager.Type.SHOOT);
                bullets.add(bullet);
            }
            elissaBulletCd = 0;
        }
        elissaBulletCd ++;
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
            sfx.playSound(SoundFXManager.Type.HIT);
            removeBullet = bullet;
        }
    }

    public void spawnCorruptData(){

        if (elissaAttackCd >= 50){
            Random rand = new Random();
            int num;
            if (elissaArray.size() > 1){
                num = rand.nextInt(elissaArray.size()-1);
            }
            else{
                num = 0;
            }
            //Gdx.app.log("Worm bullets: ", "Rand num: " + num);
            Elissa elissa = elissaArray.get(num);
            //Gdx.app.log("GameClass: ", "Adding bullets!");
            if (elissaFiles.size() <= 30){
                int x = rand.nextInt(WIDTH - 50);
                ElissaFiles bullet = new ElissaFiles(x, HEIGHT + 50, elissa.getId(), elissa.getDamage(), false);
                bullet.setBounds(new Rectangle(elissa.getSprite().getX() - 75, HEIGHT + 50, bullet.getSprite().getWidth(), bullet.getSprite().getHeight()));
                elissaFiles.add(bullet);
            }
            elissaAttackCd = 0;
        }
        elissaAttackCd ++;
    }

    public void updateCorruptData(ElissaFiles elissaFile){
        //Bullet update
        elissaFile.getBounds().setPosition(elissaFile.getX(), elissaFile.getY() - 15);
        elissaFile.setY(elissaFile.getY() - 15);
        if (elissaFile.getY() < 0) {
            removeElissaFile = elissaFile;
        }
        //PLAYER
        if (player.getBounds().overlaps(elissaFile.getBounds())){
            player.setHp(player.getHp() - elissaFile.getDamage());
            Gdx.app.log("Enemy Bullet Update: ", "Player Hit! Dmg: " + player.getHp());
            removeElissaFile = elissaFile;
            playerDisableTime = spawnCd + elissaFile.getDisableTime();
            player.disableShoot();
            sfx.playSound(SoundFXManager.Type.CORRUPTFILE);
        }
    }

    //WORMS
    public void spawnWorm(int x, int y, int xSpeed){
        Gdx.app.log("spawnWorm: ", "Spawning Worms");
        int tempY = y;
        wormsSpawned = 0;
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
                score += worm.getPoints();
                sfx.playSound(SoundFXManager.Type.DEATH);
                removeWorm = worm;
                player.setHp(player.getHp() - (worm.getDamage()*10));
            }
        }
        if (removeWorm != null){
            spawnFiles(removeWorm.getFileDropCount(), removeWorm.getX() + (removeWorm.getSprite().getWidth()/2), removeWorm.getY() + removeWorm.getSprite().getHeight()/2);
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
        Gdx.app.log("spawnTrojans: ", "Spawning Trojans");
        trojansSpawned = 0;
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
                    score += trojan.getPoints();
                    sfx.playSound(SoundFXManager.Type.DEATH);
                    removeTrojan = trojan;
                    player.setHp(player.getHp() - (trojan.getDamage()*5));
                }
            }
            if (removeTrojan != null) {
                spawnFiles(removeTrojan.getFileDropCount(), removeTrojan.getX() + (removeTrojan.getSprite().getWidth() / 2), removeTrojan.getY() + removeTrojan.getSprite().getHeight()/2);
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
                score += bigTrojan.getPoints();
                player.setHp(player.getHp() - (bigTrojan.getDamage()*10));
                sfx.playSound(SoundFXManager.Type.DEATH);
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
        Gdx.app.log("spawnMemLeaks: ", "Spawning memLeaks");
        memLeaksSpawned = 0;
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
                score += memLeak.getPoints();
                dotHappening = true;
                startTime = System.currentTimeMillis();
                sfx.playSound(SoundFXManager.Type.DEATH);
                removeMemLeak = memLeak;
                //player.setHp(player.getHp() - (memLeak.getDamage()*10));
            }
        }
        if (removeMemLeak != null){
            spawnFiles(removeMemLeak.getFileDropCount(), removeMemLeak.getX() + (removeMemLeak.getSprite().getWidth()/2), removeMemLeak.getY() + removeMemLeak.getSprite().getHeight()/2);
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

    long lastNum;
    public void damageOverTimeCollision(){

        countdown = ((System.currentTimeMillis() - startTime) / 1000);

        Gdx.app.log( "LastNuM : " , "" + lastNum);
        Gdx.app.log("Countdown: " , "" + countdown);
        if (countdown > lastNum){
            Gdx.app.log("DAMAGE: " , "");
        }

        if (countdown == 6){
            dotHappening = false;
        }
        lastNum = countdown;

    }

    //ELISSA
    public void spawnElissa(){
        elissaSpawned = 0;
        while (elissaSpawned < 1){
            Elissa elissa = new Elissa();

            if (elissaHeight <= HEIGHT){
                elissa.setY(HEIGHT);
                elissaHeight = HEIGHT + 150;
            }
            else{
                elissa.setY(HEIGHT+150);
                elissaHeight = HEIGHT;
            }

            elissa.setX(WIDTH/2 - elissa.getSprite().getWidth()/2);

            elissa.getSprite().setPosition(elissa.getX(), elissa.getY());

            //Creates Bounding box
            elissa.setBounds(new Rectangle(elissa.getX(), elissa.getY(), elissa.getSprite().getWidth(), elissa.getSprite().getHeight()));
            //Adds the boss to the arrayList
            elissaArray.add(elissa);

            elissaWidth += 150;
            elissaSpawned++;
        }
    }

    public void moveElissa(){
        if (elissaArray.size() >= 1){
            if (elissaArray.get(0).getY() <= (HEIGHT-(elissaArray.get(0).getSprite().getHeight()*1.5))) {
                for (Elissa elissa: elissaArray){
                    if(!elissa.getIsMovingLeft() && !elissa.getIsMovingRight()) {
                        elissa.setIsMovingLeft(true);
                    }
                    if(elissa.getIsMovingLeft()) {
                        if (elissa.getX() < WIDTH/8) {
                            elissa.setIsMovingLeft(false);
                            elissa.setIsMovingRight(true);
                        }
                        else {
                            elissa.setX(elissa.getX() - elissa.getxSpeed());
                        }
                    }

                    if(elissa.getIsMovingRight()) {
                        if (elissa.getX()+elissa.getSprite().getWidth() > WIDTH - WIDTH/8) {
                            elissa.setIsMovingLeft(true);
                            elissa.setIsMovingRight(false);
                        }
                        else {
                            elissa.setX(elissa.getX() + elissa.getxSpeed());
                        }
                    }

                    elissa.getBounds().setPosition(elissa.getX(), elissa.getY());
                    elissa.getSprite().setPosition(elissa.getX(), elissa.getY());
                    //if worm collides with player remove player and worm section
                    if (elissa.getBounds().overlaps(player.getBounds())){
                        score += elissa.getPoints();
                        sfx.playSound(SoundFXManager.Type.DEATH);
                        removeElissa = elissa;
                        player.setHp(player.getHp() - (elissa.getDamage()*10));
                    }
                }
            }
            else{
                for (Elissa elissa: elissaArray){

                    elissa.getBounds().setPosition(elissa.getX(), elissa.getY() - elissa.getySpeed());
                    elissa.setY(elissa.getY() - elissa.getySpeed());
                    elissa.getSprite().setPosition(elissa.getX(), elissa.getY());
                    //if worm collides with player remove player and worm section
                    if (elissa.getBounds().overlaps(player.getBounds())){
                        score += elissa.getPoints();
                        sfx.playSound(SoundFXManager.Type.DEATH);
                        removeElissa = elissa;
                        player.setHp(player.getHp() - (elissa.getDamage()*10));
                    }
                }
            }
        }
        else{
            for (Elissa elissa: elissaArray){
                elissa.getBounds().setPosition(elissa.getX(), elissa.getY());
                elissa.getSprite().setPosition(elissa.getX(), elissa.getY());
                //if worm collides with player remove player and worm section
                if (elissa.getBounds().overlaps(player.getBounds())){
                    score += elissa.getPoints();
                    sfx.playSound(SoundFXManager.Type.DEATH);
                    removeElissa = elissa;
                    player.setHp(player.getHp() - (elissa.getDamage()*10));
                }
            }
        }



        if (removeElissa != null){
            spawnFiles(removeElissa.getFileDropCount(), removeElissa.getX() + (removeElissa.getSprite().getWidth()/2), removeElissa.getY() + removeElissa.getSprite().getHeight());
            elissaArray.remove(removeElissa);
        }
    }

    //File Drops
    public void spawnFiles(int amount, float x, float y){
        int count = 0;
        //Random rand = new Random();

        int locationX = (int)x - 100;
        int locationY = (int)y;

        if (amount >= 3){
            while (count != amount){

                if ((count%3) == 0){
                    locationX = (int)x -100;
                    locationY -= 60;
                }

                if (locationX < 0){
                    locationX = 0;
                }
                else if (locationX > WIDTH-75){
                    locationX = WIDTH-75;
                }

                if (locationY < 0){
                    locationY = 0;
                }
                else if (locationY > HEIGHT-75){
                    locationY = HEIGHT - 75;
                }
//            numX = rand.nextInt((int)x + 50)-50;
//            numY = rand.nextInt((int)y + 50)-50;
                Files file = new Files();
                file.setX(locationX);
                file.setY(locationY);
                file.getSprite().setPosition(file.getX(), file.getY());

                //Creates Bounding box
                file.setBounds(new Rectangle(file.getX(), file.getY(), file.getSprite().getWidth(), file.getSprite().getHeight()));
                //Adds the worm to the arrayList
                files.add(file);

                locationX += 100;
                count ++;
            }
        }
        else{
            locationX = (int)x;
            while (count != amount){
                if (locationX < 0){
                    locationX = 0;
                }
                else if (locationX > WIDTH-75){
                    locationX = WIDTH-75;
                }

                if (locationY < 0){
                    locationY = 0;
                }
                else if (locationY > HEIGHT-75){
                    locationY = HEIGHT - 75;
                }

                Files file = new Files();
                file.setX(locationX);
                file.setY(locationY);
                file.getSprite().setPosition(file.getX(), file.getY());

                //Creates Bounding box
                file.setBounds(new Rectangle(file.getX(), file.getY(), file.getSprite().getWidth(), file.getSprite().getHeight()));
                //Adds the worm to the arrayList
                files.add(file);

                locationX += 100;
                count ++;
            }
        }


    }

    public void updateFiles(){
        float y;
        for (Files file : files){
            y = file.getY() - 2;
            file.setY(y);
            file.getBounds().setPosition(file.getX(), file.getY());
            file.getSprite().setY(y);

            if (file.getBounds().overlaps(player.getBounds())){
                sfx.playSound(SoundFXManager.Type.FILE);
                removeFile = file;
                fileScore ++;
            }
        }
        if (removeFile != null){
            files.remove(removeFile);
        }
    }

    //PAUSE SCREEN
    //Pause Screen Variables
    SpriteBatch pauseBatch;
    Stage pauseStage;
    TextButton resume;
    TextButton exit;
    TextButton setting;

    private void pauseScreenCreate(){
        pauseStage = new Stage();
        sfx.playSound(SoundFXManager.Type.SELECT);

        //Add buttons
        resume = new TextButton("Resume", skin, "default");
        resume.getLabel().setFontScale(3);
        resume.setWidth(WIDTH / 2);
        resume.setHeight(WIDTH / 4);
        resume.setPosition(WIDTH / 2 - (resume.getWidth() / 2), (HEIGHT - (HEIGHT / 4)) - (resume.getHeight()));
        resume.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(inputMultiplexer);
                sfx.playSound(SoundFXManager.Type.SELECT);
                gameState = GameState.PLAYING;
            }
        });
        resume.toFront();

        setting = new TextButton("Setting", skin, "default");
        setting.getLabel().setFontScale(3);
        setting.setWidth(WIDTH / 2);
        setting.setHeight(WIDTH / 4);
        setting.setPosition(WIDTH / 2 - (setting.getWidth() / 2), resume.getY() - resume.getHeight() - (resume.getHeight() / 2 ));
        setting.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(inputMultiplexer);
                sfx.playSound(SoundFXManager.Type.SELECT);
                gameState = GameState.PLAYING;
                //game.setScreen(AntiVirus.settingPage);
            }
        });
        setting.toFront();

        exit = new TextButton("Quit", skin, "default");
        exit.getLabel().setFontScale(3);
        exit.setWidth(WIDTH / 2);
        exit.setHeight(WIDTH / 4);
        exit.setPosition(WIDTH / 2 - (exit.getWidth() / 2), setting.getY() - setting.getHeight() - (setting.getHeight()/2)   );
        exit.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                Gdx.input.setInputProcessor(inputMultiplexer);
                sfx.playSound(SoundFXManager.Type.SELECT);
                gameState = GameState.PLAYING;
                game.setScreen(AntiVirus.levelSelectScreen);
                musicBackground.pause();
            }
        });
        exit.toFront();


        pauseStage.addActor(resume);
        pauseStage.addActor(setting);
        pauseStage.addActor(exit);
        Gdx.input.setInputProcessor(pauseStage);
    }

    private void pauseScreenRender(){
        overlay.draw(batch, 0.5f);
        uiFont.draw(batch, scoreTxt, 0, HEIGHT - (scoreLayout.height));
        uiFont.draw(batch,healthTxt,WIDTH/2 , HEIGHT - (healthLayout.height));
        resume.draw(batch, 1);
        exit.draw(batch, 1);
        pauseStage.draw();
    }

}