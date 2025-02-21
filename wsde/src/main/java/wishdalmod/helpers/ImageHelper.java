package wishdalmod.helpers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.ArrayList;
public class ImageHelper {
    private static final String BASE_PATH = "wishdaleResources/images/dongtu/canying/";
    private static final int TEXTURE_WIDTH = 522;
    private static final int TEXTURE_HEIGHT = 700;
    public static final ArrayList<TextureAtlas.AtlasRegion> canyingpowerList = new ArrayList<>();

    // 当前帧索引和动画播放速度
    private static int currentIndex = 0;
    private static float timeElapsed = 0f;
    private static float animationSpeed = 1f; // 控制动画速度，值越大速度越慢

    static {
        loadTextures();
    }

    // 加载所有的纹理资源
    private static void loadTextures() {
        String[] imageNames = {
                "1.png", "2.png", "3.png", "4.png", "5.png",
                "6.png", "7.png", "8.png", "8.png", "8.png",
                "8.png", "8.png", "8.png", "8.png", "15.png",
                "16.png", "17.png", "18.png", "19.png"
        };

        for (String imageName : imageNames) {
            canyingpowerList.add(new TextureAtlas.AtlasRegion(new Texture(BASE_PATH + imageName), 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT));
        }
    }

    // 获取当前帧的图片
    public static TextureAtlas.AtlasRegion getByIndex(float deltaTime) {
        timeElapsed += deltaTime;

        // 根据动画播放速度决定是否切换到下一帧
        if (timeElapsed >= animationSpeed) {
            timeElapsed = 0f;
            currentIndex++;
            if (currentIndex >= canyingpowerList.size()) {
                currentIndex = 0; // 如果到达最后一帧，重新播放
            }
        }

        return canyingpowerList.get(currentIndex);
    }

    // 设置动画速度，单位为秒（控制每帧的显示时间）
    public static void setAnimationSpeed(float speed) {
        animationSpeed = speed;
    }

    // 初始化方法（目前没有实际功能）
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
