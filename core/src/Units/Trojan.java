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
        ySpeed = 2;
        xSpeed = 0;

        fileDropCount = 5;
        spawnNum = 1;
        healthDropProb = 0.25;
        points = 200;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("TrojanSprite.png"));
        sprite = new Sprite(img);

        sprite.setSize(200,200);
    }

    public void setSmallTrojan(){
        setHp(100);
        setDamage(2);
        setId(4);
        setySpeed(2);
        setFileDropCount(1);
        setHealthDropProb(0.10f);
        setSpawnNum(5);
        setPoints(100);

        sprite.setSize(125, 125);
    }
}
