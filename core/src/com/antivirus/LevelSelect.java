package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.audio.Sound;


/**
 * Created by Caroline on 15/04/2016.
 * Co-Authored by Corey
 */
public class LevelSelect implements Screen {
    AntiVirus game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    private Sound selectSound;

    //load bitmap font
    BitmapFont font;

    String txt;

    // Making the object
    GlyphLayout layout;

    //Constructor to keep a reference to the main Game class
    public LevelSelect(AntiVirus game){this.game = game;}

    public void create(){
        Gdx.app.log("LevelSelect: ", "levelSelect create");
        batch = new SpriteBatch();
        //initFonts(Gdx.graphics.getWidth()/10);
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();

        //init select sound
        selectSound = Gdx.audio.newSound(Gdx.files.internal("Blip_Select.wav"));

        //Initializes title
        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        txt = "Select Scan";
        //Centers font text
        layout = new GlyphLayout();
        layout.setText(font, txt);

        //Creates button to start level 1
        final TextButton lvl1button = new TextButton("LVL1:\\WORM.EXE", skin, "default");
        lvl1button.setWidth(Gdx.graphics.getWidth()/2);
        lvl1button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl1button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl1button.getWidth()/2),
                ((Gdx.graphics.getHeight() / 4)) + (lvl1button.getHeight()*1.5f));
        lvl1button.getLabel().setFontScale(3);
        lvl1button.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level1");
                game.setScreen(AntiVirus.level1);
                Gdx.app.log("LevelSelectScreen: ", "level1 started");
            }
        });

        //Creates button to start level 2
        final TextButton lvl2button = new TextButton("LVL2:\\TROJAN.EXE", skin, "default");
        lvl2button.setWidth(Gdx.graphics.getWidth()/2);
        lvl2button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl2button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl2button.getWidth()/2),
                ((Gdx.graphics.getHeight() / 4)));
        lvl2button.getLabel().setFontScale(3);
        lvl2button.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level2");
                game.setScreen(AntiVirus.level2);

                Gdx.app.log("LevelSelectScreen: ", "level2 started");




            }
        });

        //Creates button to start level 3
        final TextButton lvl3button = new TextButton("LVL3:\\MACRO.EXE", skin, "default");
        lvl3button.setWidth(Gdx.graphics.getWidth()/2);
        lvl3button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl3button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl3button.getWidth()/2),
                ((Gdx.graphics.getHeight() / 4)) - (lvl3button.getHeight()*1.5f));
        lvl3button.getLabel().setFontScale(3);
        lvl3button.addListener(new ClickListener(){
            public void clicked (InputEvent event, float x, float y){
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level3");
                game.setScreen(AntiVirus.level3);
                Gdx.app.log("LevelSelectScreen: ", "level3 started");
            }
        });

        stage.addActor(lvl1button);
        stage.addActor(lvl2button);
        stage.addActor(lvl3button);
        Gdx.input.setInputProcessor(stage);
    }

    public void render (float f){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        font.draw(batch, txt, Gdx.graphics.getWidth()/2 - layout.width/2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4) + layout.height/2);

        batch.end();
    }



    @Override
    public void dispose(){
        selectSound.dispose();
    }
    @Override
    public void resize(int width, int height){}
    @Override
    public void pause(){}
    @Override
    public void resume(){}

    @Override
    public void show(){
        Gdx.app.log("LevelSelect: ", "levelSelect show called");
        create();
    }

    @Override
    public void hide(){
        Gdx.app.log("LevelSelect: ", "levelSelect hide called");
    }
}
