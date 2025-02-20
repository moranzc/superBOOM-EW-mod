package wishdalmod.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.icons.AbstractCustomIcon;

public class BoomIcon extends AbstractCustomIcon {
    public static final String ID = "wishdalmod:BOOM";
    private static BoomIcon singleton;
    private static final Texture boomTexture = new Texture("cvetmod/images/ManaTheresa.png"); // TODO: 你画好图以后把这个改了

    public BoomIcon() {
        super(ID, boomTexture);
    }

    public static BoomIcon get()
    {
        if (singleton == null) {
            singleton = new BoomIcon();
        }
        return singleton;
    }
}
