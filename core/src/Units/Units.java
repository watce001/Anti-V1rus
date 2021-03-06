package Units;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;


/**
 * Created by Caroline on 14/04/2016.
 * Co-Authored by Corey
 */
public class Units {
    protected int hp;
    protected int damage;
    protected float x;
    protected float y;
    protected float xSpeed;
    protected float ySpeed;
    protected int id;
    protected boolean hit = false;
    private int hitFrameCount = 0;

    protected Rectangle bounds;

    protected SpriteBatch batch;
    protected TextureRegion img;
    protected Texture hitImg;
    protected Sprite sprite;

    public Units(){ }

    //Make getters & setters
    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
        hit = true;
    }

    public int getDamage(){
        return damage;
    }

    public void setDamage(int damage){
        this.damage = damage;
    }

    public float getX(){
        return x;
    }

    public void setX(float x){
        this.x = x;
    }

    public float getY(){
        return y;
    }

    public void setY(float y){
        this.y = y;
    }

    public float getxSpeed() { return xSpeed; }

    public void setxSpeed(float xSpeed) { this.xSpeed = xSpeed; }

    public float getySpeed() { return ySpeed; }

    public void setySpeed(float ySpeed) { this.ySpeed = ySpeed; }

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public Sprite getSprite(){
        if (hit) {
            sprite.setTexture(hitImg);
            hitFrameCount = 1;
            hit = false;
        }
        if(hitFrameCount >= 1) {
            if (hitFrameCount <= 10) {
                hitFrameCount++;
            }
            else {
                sprite.setTexture(img.getTexture());
            }
        }
        return sprite;

    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }
}
