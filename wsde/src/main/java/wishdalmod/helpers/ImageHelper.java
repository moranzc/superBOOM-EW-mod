package wishdalmod.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.ArrayList;

public class ImageHelper {
    public static TextureAtlas.AtlasRegion getByIndex(int index) { return (TextureAtlas.AtlasRegion)canyingpowerList.get(index); }
    public static final ArrayList<TextureAtlas.AtlasRegion> canyingpowerList = new ArrayList(); static  {
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/1.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/2.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/3.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/4.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/5.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/6.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/7.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/8.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/15.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/16.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/17.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/18.png"), 0, 0, 522, 700));
        canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture("wishdaleResources/images/dongtu/canying/19.png"), 0, 0, 522, 700));
    }
    public static void initThis(){}
}
