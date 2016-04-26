package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;



/**
 * Created by Caroline on 22/03/2016.
 */
public class MenuScreen implements Screen {

    AntiVirus game;

    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;

    //load bitmap font
    BitmapFont font;

    String txt;

    // Making the object
    GlyphLayout layout;

    //Constructor to keep a reference to the main Game class
    public MenuScreen(AntiVirus game){this.game = game;}

//    public BitmapFont titleFont;
//    public BitmapFont startFont;


    public void create(){
        Gdx.app.log("MenuScren: ", "menuScreen create");
        batch = new SpriteBatch();
        //initFonts(Gdx.graphics.getWidth()/10);
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();

        //Initializes title
        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        txt = "anti virus";
        //Centers font text
        layout = new GlyphLayout();
        layout.setText(font, txt);


        //Creates start button
        final TextButton button = new TextButton("Start", skin, "default");
        button.setWidth(Gdx.graphics.getWidth()/2);
        button.setHeight(Gdx.graphics.getHeight() / 8);
        button.setPosition((Gdx.graphics.getWidth() / 2) - (button.getWidth()/2),
                (Gdx.graphics.getHeight() / 4) - (button.getHeight()/2));
        button.getLabel().setFontScale(3);
        button.addListener(new ClickListener(){
           public void clicked (InputEvent event, float x, float y){
               button.setText("CLICKED");
               Gdx.app.log("MenuScreen: ", "About to call level1");
               game.setScreen(AntiVirus.level1);
               Gdx.app.log("MenuScreen: ", "level1 started");
           }
        });


        stage.addActor(button);
        Gdx.input.setInputProcessor(stage);

    }

    public void render (float f){
        Gdx.app.log("MenuScreen: ", "menuScreen render");
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        font.draw(batch, txt, Gdx.graphics.getWidth()/2 - layout.width/2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight()/4) + layout.height/2);


//        titleFont.draw(batch, "ANTI-V1RUS",
//                (Gdx.graphics.getWidth()/4),
//                Gdx.graphics.getHeight() - (Gdx.graphics.getHeight()/5));
//
//        startFont.draw(batch, "START",((Gdx.graphics.getWidth()/2) - 100),
//                Gdx.graphics.getHeight()/4);

        batch.end();

    }

    @Override
    public void dispose(){
//        titleFont.dispose();
//        startFont.dispose();
    }
    @Override
    public void resize(int width, int height){}
    @Override
    public void pause(){}
    @Override
    public void resume(){}

    @Override
    public void show(){
        Gdx.app.log("MenuScreen: ", "menuScreen show called");
        create();
    }

    @Override
    public void hide(){
        Gdx.app.log("MenuScreen: ", "menuScreen hide called");
    }

    //Initializes font layout and type face
//    private void initFonts(int fontSize){
//        //Tells what type of font it will be working with
//        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("ui/data-latin.ttf"));
//        FreeTypeFontGenerator.FreeTypeFontParameter params = new FreeTypeFontGenerator.FreeTypeFontParameter();
//        FreeTypeFontGenerator.FreeTypeFontParameter startParams = new FreeTypeFontGenerator.FreeTypeFontParameter();
//
//        params.size = fontSize;
//        startParams.size = fontSize - (fontSize/4);
//        //params.color = Color.BLACK;
//        params.color = com.badlogic.gdx.graphics.Color.BLACK;
//        startParams.color = Color.BLACK;
//        titleFont = generator.generateFont(params);
//        startFont = generator.generateFont(startParams);
//    }
}

