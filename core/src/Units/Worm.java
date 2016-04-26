package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Caroline on 14/04/2016.
 */
public class Worm extends Enemies {

    boolean isUp;

    public Worm(){
        hp = 25;
        damage = 1;
        id = 2;
        speed = 3;

        fileDropCount = 2;
        spawnNum = 10;
        healthDropProb = 0.05;
        points = 50;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("EnemyTest.png"));
        sprite = new Sprite(img);

        sprite.setSize(100,100);

    }

    public boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }
}
