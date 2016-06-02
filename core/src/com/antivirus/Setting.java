//package com.antivirus;
//
//import com.badlogic.gdx.InputMultiplexer;
//import com.badlogic.gdx.Screen;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.graphics.GL20;
//import com.badlogic.gdx.graphics.Pixmap;
//import com.badlogic.gdx.graphics.Texture;
//import com.badlogic.gdx.graphics.g2d.BitmapFont;
//import com.badlogic.gdx.graphics.g2d.GlyphLayout;
//import com.badlogic.gdx.graphics.g2d.SpriteBatch;
//import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.scenes.scene2d.InputEvent;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
//import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
//import FX.ParticleManager;
//import FX.SoundFXManager;
//
//
//
///**
// * Created by Tony on 13/05/2016.
// */
//public class Setting implements Screen {
//
//    public enum GameState {PLAYING, PAUSED}
//    GameState gameState = GameState.PLAYING;
//
//
//    TextButton musicOff;
//    TextButton musicOn;
//    private SpriteBatch batch;
//    private Skin skin;
//    public static int WIDTH;
//    public static int HEIGHT;
//    BitmapFont font;
//    String txt;
//    GlyphLayout layout;
//    AntiVirus game;
//    TextButton resume;
//    Stage settingStage;
//    InputMultiplexer inputMultiplexer;
//    private SoundFXManager sfx;
//    Screen level;
//    Pixmap pixmap;
//    TextureRegion pauseBackground;
//    Image overlay;
//    SpriteBatch pauseBatch;
//
//    public Setting(AntiVirus game){
//        this.game = game;
//    }
//    public void setLevel(Screen level){
//        this.level = level;
//    }
//
//
//    public void create() {
//        batch = new SpriteBatch();
//        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
//        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
//        txt = "setting page";
//        WIDTH = Gdx.graphics.getWidth();
//        HEIGHT = Gdx.graphics.getHeight();
//
//        //changing page
//        /*
//        pixmap = new Pixmap(0,0,Pixmap.Format.RGB888);
//        pauseBackground = new TextureRegion(new Texture(pixmap));
//        overlay = new Image(pauseBackground);
//        pixmap.setColor(1, 0, 0, 05f);
//        pixmap.fill();
//        overlay.setWidth(WIDTH);
//        overlay.setHeight(HEIGHT);
//        pauseBatch = new SpriteBatch();
//        */
//
//
//        inputMultiplexer = new InputMultiplexer();
//        layout = new GlyphLayout();
//        layout.setText(font, txt);
//
//
//
//
//
//
//
//        sfx = new SoundFXManager();
//
//        Gdx.input.setInputProcessor(settingStage);
//        settingButton();
//    }
//
//    public void clicked(InputEvent event, float x, float y) {
//        Gdx.app.log("Setting : ", "About to call setting page");
//
//        settingPageRender();
//    }
//
//
//
//
//
//
//    public void render (float f) {
//        Gdx.app.log("Setting: ", "setting page render");
//        Gdx.gl.glClearColor(0, 0, 0, 1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//
//
//        batch.begin();
//        font.draw(batch, txt, Gdx.graphics.getWidth()/2 - layout.width/2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4) + layout.height/2);
//        settingButton();
//        settingPageRender();
//
//        batch.end();
//    }
//    public void dispose(){
//
//    }
//
//    public void resize(int width, int height){}
//
//    public void pause(){}
//
//    public void resume(){
//        Gdx.app.log("Resume: ", "Resume working~~!!!!!");
//    }
//
//    public void show(){
//        Gdx.app.log("Setting: ", "Setting show called");
//        create();
//    }
//    public void hide(){
//        Gdx.app.log("Setting: ", "Setting hide called");
//    }
//
//
//    public void settingButton(){
//        settingStage = new Stage();
//
//        //adding buttons
//        resume = new TextButton("Resume", skin, "default");
//        resume.getLabel().setFontScale(3);
//        resume.setWidth(WIDTH / 2);
//        resume.setHeight(WIDTH / 6);
//        resume.setPosition(WIDTH / 2 -resume.getWidth() / 2, resume.getY() + resume.getHeight() - resume.getHeight() + resume.getY()+resume.getHeight());
//
//        resume.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                Gdx.input.setInputProcessor(inputMultiplexer);
//                Lvl1.musicBackground.pause();
//                sfx.playSound(SoundFXManager.Type.SELECT);
//                gameState = GameState.PLAYING;
//                Lvl1.musicBackground.pause();
//
//
//
//                //resume();
//
//            }
//        });
//        resume.toFront();
//
//        musicOff = new TextButton("Music off", skin, "default");
//        musicOff.getLabel().setFontScale(3);
//        musicOff.setWidth(WIDTH / 2);
//        musicOff.setHeight(WIDTH / 6);
//        musicOff.setPosition(WIDTH / 2 -musicOff.getWidth() / 2, musicOff.getY() + musicOff.getHeight() + musicOff.getHeight()+musicOff.getHeight());
//
//        musicOff.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                //Gdx.input.setInputProcessor(inputMultiplexer);
//                sfx.playSound(SoundFXManager.Type.SELECT);
//                Lvl1.musicBackground.pause();
//            }
//
//        });
//        musicOff.toFront();
//
//        musicOn = new TextButton("Music on", skin, "default");
//        musicOn.getLabel().setFontScale(3);
//        musicOn.setWidth(WIDTH / 2);
//        musicOn.setHeight(WIDTH / 6);
//        musicOn.setPosition(WIDTH / 2 -musicOn.getWidth() / 2, musicOn.getY() + musicOn.getHeight() + musicOn.getHeight()+musicOn.getHeight()
//                +musicOn.getHeight()+musicOn.getHeight());
//
//        musicOn.addListener(new ClickListener() {
//            public void clicked(InputEvent event, float x, float y) {
//                //Gdx.input.setInputProcessor(inputMultiplexer);
//                sfx.playSound(SoundFXManager.Type.SELECT);
//                Lvl1.musicBackground.play();
//            }
//        });
//        musicOn.toFront();
//
//
//
//        settingStage.addActor(musicOff);
//        settingStage.addActor(musicOn);
//        settingStage.addActor(resume);
//        Gdx.input.setInputProcessor(settingStage);
//    }
//
//    public void settingPageRender(){
//        resume.draw(batch, 1);
//        musicOn.draw(batch, 1);
//        musicOff.draw(batch, 1);
//        settingStage.draw();
//    }
//}