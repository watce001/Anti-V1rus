package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Caroline on 14/04/2016.
 */

public class Player extends Units{

    public Player(){
        hp = 200;
        damage = 20;
        id = 1;
        ySpeed = 8;
        xSpeed = 8;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("PlayerTest.png"));
        sprite = new Sprite(img);
        bounds = new Rectangle();
    }
}
