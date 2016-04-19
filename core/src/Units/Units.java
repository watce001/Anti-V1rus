package Units;

/**
 * Created by Caroline on 14/04/2016.
 */
public class Units {
    protected int hp;
    protected int damage;
    protected float x;
    protected float y;
    protected float speed;


    public Units(){ }

    //Make getters & setters
    public int getHp(){
        return hp;
    }

    public void setHp(int hp){
        this.hp = hp;
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

    public float getSpeed(){
        return speed;
    }

    public void setSpeed(float speed){
        this.speed = speed;
    }
}
