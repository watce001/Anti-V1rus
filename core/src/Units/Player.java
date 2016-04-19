package Units;

import com.antivirus.Lvl1;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Caroline on 14/04/2016.
 */

public class Player extends Units{

    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;

    private SpriteBatch batch;
    private TextureRegion img;
    private Sprite sprite;

    public Player(){
        //super(hp, damage, x = Lvl1.WIDTH/2;speed);
        hp = 200;
        damage = 5;
        speed = 100;

        y = Lvl1.HEIGHT/2;

        speed = 100;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("PlayerTest.png"));
        sprite = new Sprite(img);
    }

    public void setLeft(boolean b) {left =b;}
    public void setRight(boolean b) { right = b;}
    public void setUp(boolean b) { up = b;}
    public void setDown(boolean b) {down = b;}

    public Sprite getSprite(){
        return sprite;
    }

    public void update(float dt){

    }
}
