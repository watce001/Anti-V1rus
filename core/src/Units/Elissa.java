package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Corey on 5/18/2016.
 */
public class Elissa extends Enemies{

    boolean isUp;

    public Elissa(){
        hp = 800;
        damage = 10;
        fileDropCount = 10;
        points = 400;
        spawnNum = 1;
        xSpeed = 3;
        ySpeed = 1;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("ElissaSprite.png"));
        sprite = new Sprite(img);
        sprite.setSize(300,300);
    }

    public boolean getIsUp() {
        return isUp;
    }

    public void setIsUp(boolean isUp) {
        this.isUp = isUp;
    }
}

