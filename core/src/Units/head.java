package Units;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Tony on 31/05/2016.
 */
public class head extends Worm {

    public head(){
        hp = 150;
        damage = 12;
        fileDropCount = 10;
        points = 400;
        spawnNum = 1;
        xSpeed = 0;
        ySpeed = 3;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("virus_train_head.png"));
        sprite = new Sprite(img);
        sprite.setSize(300,300);

        hitImg = new Texture("virus_train_Hithead.png");
    }

}
