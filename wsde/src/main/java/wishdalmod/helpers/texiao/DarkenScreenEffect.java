package wishdalmod.helpers.texiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DarkenScreenEffect extends AbstractGameEffect {
    private float intensity;
    private boolean battleEnded = false;
    private float fadeOutTimer = 1.0f;

    public DarkenScreenEffect(int triggerCount) {
        this.intensity = Math.min(0.7f, triggerCount * 0.02f);
        this.color = new Color(0, 0, 0, intensity);
        this.duration = -1.0f;
    }

    public void updateIntensity(int triggerCount) {
        this.intensity = Math.min(0.7f, triggerCount * 0.05f);
        this.color.a = intensity;
        this.fadeOutTimer = 7.0f;
    }

    public void update() {
        float deltaTime = Gdx.graphics.getDeltaTime();

        if (battleEnded) {
            this.isDone = true;
            return;
        }

        if (fadeOutTimer > 0) {
            fadeOutTimer -= deltaTime;
        } else if (intensity > 0) {
            intensity -= deltaTime * 0.5f;
            color.a = intensity;
        }

        if (intensity <= 0) {
            this.isDone = true;
        }
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0, 0, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose() {}

    public void endEffect() {
        this.battleEnded = true;
    }
}
