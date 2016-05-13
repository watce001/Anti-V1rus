package Items;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Caroline on 14/04/2016.
 */
public class Files extends Items {

    public Files(){
        batch = new SpriteBatch();
        img = new TextureRegion(new Texture("FolderSprite.png"));
        sprite = new Sprite(img);
        sprite.setSize(50,50);
    }
}
