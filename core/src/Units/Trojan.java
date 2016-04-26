package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Caroline on 24/04/2016.
 */
public class Trojan extends Enemies{

    public Trojan(){
        hp = 150;
        damage = 4;
        id = 3;
        speed = 2;

        fileDropCount = 5;
        spawnNum = 1;
        healthDropProb = 0.25;
        points = 200;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("EnemyTest.png"));
        sprite = new Sprite(img);

        sprite.setSize(200,200);
    }

    public void getSmallTrojan(){
        hp = 25;
        damage = 2;
        id = 4;
        speed = 2;
        fileDropCount = 1;
        healthDropProb = 0.10;
        spawnNum = 5;
        points = 100;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("EnemyTest.png"));
        sprite = new Sprite(img);
        sprite.setSize(125, 125);
    }
}