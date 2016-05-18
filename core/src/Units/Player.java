package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Caroline on 14/04/2016.
 *
 */

public class Player extends Units{

    boolean canShoot = true;

    public Player(){
        hp = 100;
        damage = 20;
        id = 1;
        ySpeed = 8;
        xSpeed = 8;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("PlayerSprite.png"));
        sprite = new Sprite(img);
        sprite.setSize(150,150);
        bounds = new Rectangle();

        hitImg = new Texture("PlayerHitSprite.png");
    }

    public void disableShoot() {
        this.canShoot = false;
    }

    public void enableShoot() {
        this.canShoot = true;
    }

    public boolean canShoot() {
        return canShoot;
    }
}
