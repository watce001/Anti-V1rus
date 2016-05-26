package FX;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import Units.Units;
import Units.Enemies;

/**
 * Created by Corey on 5/24/2016.
 * Adapted from JuiceGame Particle System by Michael Stopa on 5/4/2016
 */
public class ParticleManager {
    //Constants
    public static final int MAX_PARTICLES = 512;
    public static final float PLAYER_TRAIL_LIFETIME = 0.3f;
    public static final float IMPACT_LIFETIME = 0.2f;
    public static final float IMPACT_SCATTER = 150f;
    public static final float EXPLOSION_LIFETIME = 0.8f;
    public static final float EXPLOSION_SCATTER = 300f;
    public enum Type { NONE, MUZZLE_FLASH, PLAYER_TRAIL, IMPACT, EXPLOSION, DATA}

    //Textures
    Texture explosionSheet = new Texture("ExplosionSheet.png");
    Texture pixel = new Texture("pixel.png");
    Texture data0 = new Texture("0.png");
    Texture data1 = new Texture("1.png");
    Texture eng0 = new Texture("eng0.png");
    Texture eng1 = new Texture("eng1.png");
    Texture flash = new Texture("BulletSprite.png");

    //Type Data
    TextureRegion playerTrailTextures[] = new TextureRegion[2];
    TextureRegion impact;
    TextureRegion muzzleFlash;
    TextureRegion dataTextures[] = new TextureRegion[2];
    TextureRegion explosionFrames[] = new TextureRegion[7];

    //Entity Data
    public Type[] type = new Type[MAX_PARTICLES];
    public Units[] source = new Units[MAX_PARTICLES];
    /**Position in the world. */
    public float[] x = new float[MAX_PARTICLES];
    public float[] y = new float[MAX_PARTICLES];
    /**Velocity applied per-second. */
    public float[] vX = new float[MAX_PARTICLES];
    public float[] vY = new float[MAX_PARTICLES];
    public float[] lifetime = new float[MAX_PARTICLES];

    public ParticleManager() {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            type[i] = Type.NONE;
        }
    }

    public void init() {
        playerTrailTextures[0] = new TextureRegion(eng0);
        playerTrailTextures[1] = new TextureRegion(eng1);
        dataTextures[0] = new TextureRegion(data0);
        dataTextures[1] = new TextureRegion(data1);
        muzzleFlash = new TextureRegion(flash);
        muzzleFlash.flip(false, true);

        impact = new TextureRegion(pixel, 10, 10);

        explosionFrames[0] = new TextureRegion(explosionSheet, 0, 0, 200, 200);
        explosionFrames[1] = new TextureRegion(explosionSheet, 200, 0, 200, 200);
        explosionFrames[2] = new TextureRegion(explosionSheet, 400, 0, 200, 200);
        explosionFrames[3] = new TextureRegion(explosionSheet, 600, 0, 200, 200);
        explosionFrames[4] = new TextureRegion(explosionSheet, 0, 200, 200, 200);
        explosionFrames[5] = new TextureRegion(explosionSheet, 200, 200, 200, 200);
        explosionFrames[6] = new TextureRegion(explosionSheet, 400, 200, 200, 200);
    }

    public void dispose() {
        explosionSheet.dispose();
        pixel.dispose();
        data0.dispose();
        data1.dispose();
        eng0.dispose();
        eng1.dispose();
        flash.dispose();
    }

    public int spawn(Type t, Units spawner) {
        //An early-fail stupid check
        if (t == null) return -1;
        //Find a free index by looping through from the beginning
        int i = -1;
        for (int free = 0; free < MAX_PARTICLES; free++) {
            if (type[free] == Type.NONE) {
                i = free;
                break;
            }
        }
        //Return a fail indicator if no free index was found
        if (i < 0) return -1;

        //Register the index as in-use
        type[i] = t;
        x[i] = 0f;
        y[i] = 0f;
        source[i] = spawner;

        //Type-specific initialization
        switch(t) {
            case PLAYER_TRAIL: {
                vX[i] = MathUtils.random(-100f, 100f);
                vY[i] = -600f;
                lifetime[i] = PLAYER_TRAIL_LIFETIME;
                break;
            } case MUZZLE_FLASH: {
                if(spawner instanceof Enemies) {
                    vX[i] = 0f;
                    vY[i] = 5f;
                }
                else {
                    vX[i] = 0f;
                    vY[i] = -5f;
                }
                lifetime[i] = 0.03f;
                break;
            } case IMPACT: {
                vX[i] = MathUtils.random(-IMPACT_SCATTER, IMPACT_SCATTER);
                vY[i] = MathUtils.random(-IMPACT_SCATTER, IMPACT_SCATTER);
                lifetime[i] = IMPACT_LIFETIME;
                break;
            } case EXPLOSION: {
                vX[i] = 0;
                vY[i] = 0;
                lifetime[i] = EXPLOSION_LIFETIME/2;
                break;
            } case DATA: {
                vX[i] = MathUtils.random(-EXPLOSION_SCATTER, EXPLOSION_SCATTER);
                vY[i] = MathUtils.random(-EXPLOSION_SCATTER, EXPLOSION_SCATTER);
                lifetime[i] = EXPLOSION_LIFETIME;
                break;
            }


        }

        return i;
    }

    public void update(float deltaTime) {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            if (type[i] == Type.NONE) continue;
            //Recycle dead particles to free their memory for use by new particles
            if (lifetime[i] < 0f) {
                type[i] = Type.NONE;
                continue;
            }
            lifetime[i] -= deltaTime;
            x[i] += vX[i] * deltaTime;
            y[i] += vY[i] * deltaTime;
        }
    }

    public void render(SpriteBatch batch) {
        for (int i = 0; i < MAX_PARTICLES; i++) {
            switch(type[i]) {
                case NONE: break;
                case PLAYER_TRAIL: {
                    TextureRegion reg = playerTrailTextures[i % playerTrailTextures.length];
                    batch.setColor(1f, 1f, 1f, Math.max(lifetime[i] / PLAYER_TRAIL_LIFETIME, 0f));
                    batch.draw(reg,
                            x[i] - 12.5f,
                            y[i] - 12.5f, 25f, 25f);
                    break;
                } case MUZZLE_FLASH: {
                    TextureRegion reg = muzzleFlash;
                    batch.setColor(1, 1, 1, 1);
                    /* Muzzle flashes look a lot better when snapped to their source.
                     * Doing so is simply a matter of drawing it relative to whatever
                     * entity spawned it */
                    Units spawner = source[i];
                    int yOffset = 0;
                    //if spawned by an enemy, flip texture and offset y value
                    if(spawner instanceof Enemies) {
                        reg = new TextureRegion(muzzleFlash);
                        reg.flip(false, true);
                        yOffset = -40; //so sprite renders below the enemy;
                    }
                    batch.draw(reg,
                            spawner.getSprite().getX() + x[i] - 17.5f,
                            spawner.getSprite().getY() + y[i] + yOffset, 35, 45);
                    break;
                } case IMPACT: {
                    TextureRegion reg = new TextureRegion(impact);
                    batch.setColor(1, 1, 1, Math.max(lifetime[i] / IMPACT_LIFETIME, 0f));
                    batch.draw(reg,
                            x[i] - reg.getRegionWidth()/2,
                            y[i] - reg.getRegionHeight()/2);
                    break;
                } case DATA: {
                    //Pick a random one to display
                    TextureRegion reg = new TextureRegion(dataTextures[i % dataTextures.length]);
                    batch.setColor(1, 1, 1, Math.max(lifetime[i] / EXPLOSION_LIFETIME, 0f));
                    batch.draw(reg,
                            x[i] - reg.getRegionWidth()/2,
                            y[i] - reg.getRegionHeight()/2);
                    break;
                } case EXPLOSION: {
                    int frame = (int) (explosionFrames.length - (lifetime[i]/EXPLOSION_LIFETIME/2*explosionFrames.length));
                    if (frame >= 0 && frame < explosionFrames.length) {
                        TextureRegion currentFrame = new TextureRegion(explosionFrames[frame]);
                        batch.setColor(1, 1, 1, Math.max(lifetime[i] / EXPLOSION_LIFETIME/2, 0f));
                        batch.draw(currentFrame, x[i] - explosionFrames[frame].getRegionWidth()/2, y[i] - explosionFrames[frame].getRegionHeight()/2);
                    }
                    break;
            }
        }
    }
}


}
