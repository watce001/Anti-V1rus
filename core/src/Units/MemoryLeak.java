package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Caroline on 24/04/2016.
 */
public class MemoryLeak extends Enemies{
   //dot = damage over time
    int dot;

    public MemoryLeak(){
        hp = 100;
        damage = 6;
        dot = 3;
        id = 5;
        speed = 4;

        fileDropCount = 4;
        spawnNum = 3;
        healthDropProb = 0.20;
        points = 150;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("EnemyTest.png"));
        sprite = new Sprite(img);
    }

    public int getDot() {
        return dot;
    }

    public void setDot(int dot) {
        this.dot = dot;
    }
}
