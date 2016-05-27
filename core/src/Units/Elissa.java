package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Corey on 5/18/2016.
 */
public class Elissa extends Enemies{

    boolean isMovingLeft = false;
    boolean isMovingRight = false;

    public Elissa(){
        hp = 1500;
        damage = 10;
        fileDropCount = 15;
        points = 2000;
        spawnNum = 1;
        xSpeed = 3;
        ySpeed = 2;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("ElissaSprite.png"));
        sprite = new Sprite(img);
        sprite.setSize(300,300);

        hitImg = new Texture("ElissaHitSprite.png");
    }

    public boolean getIsMovingLeft() {
        return isMovingLeft;
    }

    public void setIsMovingLeft(boolean isMovingLeft) {
        this.isMovingLeft = isMovingLeft;
    }

    public boolean getIsMovingRight() {
        return isMovingRight;
    }

    public void setIsMovingRight(boolean isMovingRight) {
        this.isMovingRight = isMovingRight;
    }
}

