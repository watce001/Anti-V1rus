package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tony on 31/05/2016.
 */
public class body extends Worm {

    public body(){

        hp = 400;
        damage = 10;
        fileDropCount = 10;
        points = 400;
        spawnNum = 4;
        xSpeed = 0;
        ySpeed = 3;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("virus_train_body.png"));
        sprite = new Sprite(img);
        sprite.setSize(200,200);

        hitImg = new Texture("virus_train_Hitbody.png");
    }
}
