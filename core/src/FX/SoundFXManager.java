package FX;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Corey on 5/24/2016.
 */
public class SoundFXManager {
    public enum Type {
        DEATH, SHOOT, HIT, SELECT, FILE, CORRUPTFILE
    }
    //SELECT
    Sound select;
    //DEATH
    Sound death;
    //SHOOT
    Sound shoot1;
    Sound shoot2;
    //HIT
    Sound hit1;
    Sound hit2;
    //PICKUPS
    Sound file;
    Sound corruptFile;

    //Switch for which sound to play
    boolean num;

    public SoundFXManager(){
        //SELECT
        select = Gdx.audio.newSound(Gdx.files.internal("Blip_Select.wav"));
        //DEATH
        death = Gdx.audio.newSound(Gdx.files.internal("Death.wav"));
        //SHOOT - Has 2 variations to prevent sound being repetitive
        shoot1 = Gdx.audio.newSound(Gdx.files.internal("Laser_Shoot.wav"));
        shoot2 = Gdx.audio.newSound(Gdx.files.internal("Laser_Shoot2.wav"));
        //HIT - Also has 2 variations to prevent sound being repetitive
        hit1 = Gdx.audio.newSound(Gdx.files.internal("Hit_Hurt.wav"));
        hit2 = Gdx.audio.newSound(Gdx.files.internal("Hit_Hurt2.wav"));
        //PICKUPS
        file = Gdx.audio.newSound(Gdx.files.internal("Pickup_File.wav"));
        corruptFile = Gdx.audio.newSound(Gdx.files.internal("Pickup_Corrupt.wav"));

        num = true;
    }

    public void playSound(Type type) {
        //randomise pitch to further prevent sounds from getting repetitive.
        float pitch = MathUtils.random(0.5f, 1.5f);

        if(type == Type.SHOOT) {
            if(num) {
                shoot1.play(0.3f, pitch, 0);
            }
            else {
                shoot2.play(0.3f, pitch, 0);
            }
        }
        else if(type == Type.HIT) {
            if(num) {
                hit1.play(0.7f, pitch, 0);
            }
            else {
                hit2.play(0.7f, pitch, 0);
            }
        }
        else if(type == Type.DEATH) {
            death.play(1f);
        }
        else if(type == Type.FILE) {
            file.play(0.5f);
        }
        else if(type == Type.CORRUPTFILE){
            corruptFile.play(0.7f);
        }
        else if(type == Type.SELECT) {
            select.play(1f);
        }

        if(num) {
            num = false;
        }
        else {
            num = true;
        }
    }

    public void dispose() {
        select.dispose();
        death.dispose();
        shoot1.dispose();
        shoot2.dispose();
        hit1.dispose();
        hit2.dispose();
        file.dispose();
        corruptFile.dispose();
    }

}
