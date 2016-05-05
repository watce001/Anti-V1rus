package Projectiles;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Caroline on 20/04/2016.
 */
public class Bullet {
    private SpriteBatch batch;
    private TextureRegion img;
    private Sprite sprite;
    private Rectangle bounds;

    //1=player,2=enemy1, etc.
    // REMOVE ID
    private int id;

    int damage;

    protected float x;
    protected float y;

    public Bullet(float x, float y, int id, int damage){
        this.x = x;
        this.y = y;
        this.id = id;
        this.damage = damage;

        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("BulletTest.png"));
        sprite = new Sprite(img);
        sprite.setPosition(x,y);
        bounds = new Rectangle();
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
        sprite.setPosition(x,y);
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        this.y = y;
        sprite.setPosition(x,y);
    }

    public Sprite getSprite(){
        return sprite;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public int getDamage() {
        return damage;
    }

    public int getId() {
        return id;
    }
}
