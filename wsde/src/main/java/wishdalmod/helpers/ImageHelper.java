package wishdalmod.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.ArrayList;
public class ImageHelper {
    private static final String BASE_PATH = "wishdaleResources/images/dongtu/canying/";
    private static final int TEXTURE_WIDTH = 522;
    private static final int TEXTURE_HEIGHT = 700;
    public static final ArrayList<TextureAtlas.AtlasRegion> canyingpowerList = new ArrayList<>();
    private static int currentIndex = 0;
    private static float timeElapsed = 0f;
    private static float animationSpeed = 1f; // 控制动画速度，值越大速度越慢,但完全没有用
    static {
        loadTextures();
    }
    private static void loadTextures() {
        String[] imageNames = {
                "1.png","1.png","1.png",
                "2.png","2.png","2.png",
                "3.png","3.png","3.png",
                "4.png","4.png","4.png",
                "5.png","5.png","5.png",
                "6.png","6.png","6.png",
                "7.png","7.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "8.png","8.png","8.png",
                "15.png","15.png","15.png",
                "16.png","16.png","16.png",
                "17.png","17.png","17.png",
                "18.png","18.png","18.png",
                "19.png","19.png","19.png"
        };
        for (String imageName : imageNames) {
            canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture(BASE_PATH + imageName), 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT));
        }
    }
    public static TextureAtlas.AtlasRegion getByIndex(float deltaTime) {
        timeElapsed += deltaTime;
        if (timeElapsed >= animationSpeed) {
            timeElapsed = 0f;
            currentIndex++;
            if (currentIndex >= canyingpowerList.size()) {
                currentIndex = 0;
            }
        }
        return canyingpowerList.get(currentIndex);
    }
    public static void setAnimationSpeed(float speed) {
        animationSpeed = speed;
    }
    public static void initThis() {
    }
}




/*
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

 */
