package com.antivirus;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.audio.Sound;


/**
 * Created by Corey on 15/04/2016.
 * Co-Authored by Caroline
 */
public class LevelSelect implements Screen {
    AntiVirus game;
    private SpriteBatch batch;
    private Skin skin;
    private Stage stage;
    public static int WIDTH;
    public static int HEIGHT;
    InputMultiplexer inputMultiplexer;
    private boolean checkingScore;

    //Overlay
    Pixmap pixmap;
    TextureRegion scoreBackground;
    Image overlay;

    GlyphLayout scoreLayout;

    private int checkedScore;
    private int checkedFileRecovery;

    private Sound selectSound;

    //load bitmap font
    BitmapFont font;
    BitmapFont uiFont;

    String txt;

    // Making the object
    GlyphLayout layout;

    //Constructor to keep a reference to the main Game class
    public LevelSelect(AntiVirus game) {
        this.game = game;
    }

    public void create() {
        Gdx.app.log("LevelSelect: ", "levelSelect create");
        batch = new SpriteBatch();
        //initFonts(Gdx.graphics.getWidth()/10);
        skin = new Skin(Gdx.files.internal("uidata/uiskin.json"));
        stage = new Stage();

        //Gets width and height of screen, ans sets them to variables
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        //set default to not checking score
        checkingScore = false;

        //init select sound
        selectSound = Gdx.audio.newSound(Gdx.files.internal("Blip_Select.wav"));

        //Initializes title
        font = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        txt = "Select Scan";
        //Centers font text
        layout = new GlyphLayout();
        layout.setText(font, txt);

        //init UIfont
        uiFont = new BitmapFont(Gdx.files.internal("MainMenu/datacontrol.fnt"));
        uiFont.getData().setScale(0.5f, 0.5f);

        //Managing input from both stage, and sprite batch
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);

        //Overlay for other screens
        pixmap = new Pixmap(0, 0, Pixmap.Format.RGB888);
        scoreBackground = new TextureRegion(new Texture(pixmap));
        overlay = new Image(scoreBackground);
        pixmap.setColor(1, 0, 0, 05f);
        pixmap.fill();
        overlay.setWidth(WIDTH);
        overlay.setHeight(HEIGHT);

        //Creates button to start level 1
        final TextButton lvl1button = new TextButton("LVL1:\\WORM.EXE", skin, "default");
        lvl1button.setWidth(Gdx.graphics.getWidth() / 2);
        lvl1button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl1button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl1button.getWidth() / 2),
                ((Gdx.graphics.getHeight() / 4)) + (lvl1button.getHeight() * 1.5f));
        lvl1button.getLabel().setFontScale(3);
        lvl1button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level1 Score");
                scoreScreenCreate(1);
                checkingScore = true;
            }
        });

        //Creates button to start level 2
        final TextButton lvl2button = new TextButton("LVL2:\\TROJAN.EXE", skin, "default");
        lvl2button.setWidth(Gdx.graphics.getWidth() / 2);
        lvl2button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl2button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl2button.getWidth() / 2),
                ((Gdx.graphics.getHeight() / 4)));
        lvl2button.getLabel().setFontScale(3);
        lvl2button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level2 Score");
                scoreScreenCreate(2);
                checkingScore = true;

                //Presents dialog box with a coming soon message - Remove once level implemented
//                showComingSoon();


                //Gdx.app.log("LevelSelectScreen: ", "level1 started");
            }
        });

        //Creates button to start level 3
        final TextButton lvl3button = new TextButton("LVL3:\\MACRO.EXE", skin, "default");
        lvl3button.setWidth(Gdx.graphics.getWidth() / 2);
        lvl3button.setHeight(Gdx.graphics.getHeight() / 8);
        lvl3button.setPosition((Gdx.graphics.getWidth() / 2) - (lvl3button.getWidth() / 2),
                ((Gdx.graphics.getHeight() / 4)) - (lvl3button.getHeight() * 1.5f));
        lvl3button.getLabel().setFontScale(3);
        lvl3button.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectSound.play();
                Gdx.app.log("LevelSelectScreen: ", "About to call level3 Score");
                scoreScreenCreate(3);
                checkingScore = true;
            }
        });

        stage.addActor(lvl1button);
        stage.addActor(lvl2button);
        stage.addActor(lvl3button);
        Gdx.input.setInputProcessor(stage);
    }

    public void render(float f) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        batch.begin();
        font.draw(batch, txt, Gdx.graphics.getWidth() / 2 - layout.width / 2, (Gdx.graphics.getHeight() - Gdx.graphics.getHeight() / 4) + layout.height / 2);

        if (checkingScore) {
            scoreScreenRender();
        }

        batch.end();
    }

    private void showComingSoon() {
        Dialog dialog = new Dialog("Coming Soon!", skin, "dialog") {
            public void result(Object obj) {
                System.out.println("result " + obj);
            }
        };
        dialog.setScale(3);
        dialog.setWidth(Gdx.graphics.getWidth());
        dialog.text("This level is currently unavailable.");
        dialog.button("Okay");
        dialog.show(stage);
    }

    @Override
    public void dispose() {
        selectSound.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void show() {
        Gdx.app.log("LevelSelect: ", "levelSelect show called");
        create();
    }

    @Override
    public void hide() {
        Gdx.app.log("LevelSelect: ", "levelSelect hide called");
    }

    //HIGH SCORE SCREEN
    //High Score Screen Variables
    Stage scoreStage;
    TextButton play;
    TextButton back;
    String name;

    private void scoreScreenCreate(int lvl) {
        scoreStage = new Stage();
        scoreLayout = new GlyphLayout();

        name = "";

        //Add buttons
        play = new TextButton("Play", skin, "default");
        play.getLabel().setFontScale(3);
        play.setWidth(WIDTH / 2);
        play.setHeight(WIDTH / 4);
        play.setPosition(WIDTH / 2 - (play.getWidth() / 2), (play.getHeight()) * 1.2f);
        if (lvl == 1) {
            name = "LVL1:\\WORM.EXE";
            play.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectSound.play();
                    Gdx.app.log("LevelSelectScreen: ", "About to call level1");
                    game.setScreen(AntiVirus.level1);
                    Gdx.app.log("LevelSelectScreen: ", "level1 started");
                }
            });
        } else if (lvl == 2) {
            name = "LVL2:\\TROJAN.EXE";
            play.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectSound.play();
                    Gdx.app.log("LevelSelectScreen: ", "About to call level2");
                    game.setScreen(AntiVirus.level2);
                    Gdx.app.log("LevelSelectScreen: ", "level2 started");
                }
            });
        } else {
            name = "LVL3:\\MACRO.EXE";
            play.addListener(new ClickListener() {
                public void clicked(InputEvent event, float x, float y) {
                    selectSound.play();
                    Gdx.app.log("LevelSelectScreen: ", "About to call level3");
                    game.setScreen(AntiVirus.level3);
                    Gdx.app.log("LevelSelectScreen: ", "level3 started");
                }
            });
        }
        play.toFront();

        checkedScore = ScoreHandler.getHighScore(lvl);
        checkedFileRecovery = ScoreHandler.getHighFileRecovery(lvl);

        back = new TextButton("Back", skin, "default");
        back.getLabel().setFontScale(3);
        back.setWidth(WIDTH / 2);
        back.setHeight(WIDTH / 4);
        back.setPosition(WIDTH / 2 - (back.getWidth() / 2), back.getHeight() * 0.1f);
        back.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                selectSound.play();
                checkingScore = false;
                Gdx.input.setInputProcessor(stage);
            }
        });
        back.toFront();

        scoreStage.addActor(back);
        scoreStage.addActor(play);
        Gdx.input.setInputProcessor(scoreStage);
    }

    private void scoreScreenRender() {
        overlay.draw(batch, 1);
        String score = ("High Score: " + checkedScore);
        String fileScore = ("Data Recovery: " + checkedFileRecovery + "%");
        scoreLayout.setText(font, name);
        font.draw(batch, name, WIDTH / 2 - scoreLayout.width / 2, HEIGHT - scoreLayout.height - 10);
        scoreLayout.setText(uiFont, score);
        uiFont.draw(batch, score, WIDTH / 2 - scoreLayout.width / 2, HEIGHT / 2 + HEIGHT / 4);
        scoreLayout.setText(uiFont, fileScore);
        uiFont.draw(batch, fileScore, WIDTH / 2 - scoreLayout.width / 2, HEIGHT / 2);
        play.draw(batch, 1);
        back.draw(batch, 1);
        scoreStage.draw();
    }
}
