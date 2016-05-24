package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * BOSS CLASS
 * Created by Caroline on 9/05/2016.
 * Co-Authored by Corey
 */
public class YourDoom extends Worm{

    public YourDoom(){
        hp = 300;
        damage = 4;
        fileDropCount = 10;
        points = 400;
        spawnNum = 7;
        xSpeed = 3;
        ySpeed = 1;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("YourDoomSprite.png"));
        sprite = new Sprite(img);
        sprite.setSize(150,150);

        hitImg = new Texture("WormHitSprite.png");
    }
}
