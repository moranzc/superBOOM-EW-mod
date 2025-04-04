package wishdalmod.helpers.texiao;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import java.util.ArrayList;

public class MicaiTexiao {
    private static final String BASE_PATH = "wishdaleResources/images/dongtu/micai/";
    private static final int TEXTURE_WIDTH = 522;
    private static final int TEXTURE_HEIGHT = 700;
    public static final ArrayList<TextureAtlas.AtlasRegion> micaiList = new ArrayList<>();
    private static int currentIndex = 0;
    private static float timeElapsed = 0f;
    private static float animationSpeed = 1f; // 控制动画速度，值越大速度越慢,但完全没有用，不如重复
    static {
        loadTextures();
    }
    private static void loadTextures() {
        String[] imageNames = {
                "1.PNG","1.PNG",
                "2.PNG","2.PNG",
                "3.PNG","3.PNG",
                "4.PNG","4.PNG",
                "5.PNG","5.PNG",
                "6.PNG","6.PNG",
                "7.PNG","7.PNG",
                "8.PNG","8.PNG",
                "9.PNG","9.PNG",
        };
        for (String imageName : imageNames) {
            micaiList.add(new TextureAtlas.AtlasRegion(new Texture(BASE_PATH + imageName), 0, 0, TEXTURE_WIDTH, TEXTURE_HEIGHT));
        }
    }
    public static TextureAtlas.AtlasRegion getByIndex(float deltaTime) {
        timeElapsed += deltaTime;
        if (timeElapsed >= animationSpeed) {
            timeElapsed = 0f;
            currentIndex++;
            if (currentIndex >= micaiList.size()) {
                currentIndex = 0;
            }
        }
        return micaiList.get(currentIndex);
    }
    public static void setAnimationSpeed(float speed) {
        animationSpeed = speed;
    }
    public static void initThis() {
    }
}