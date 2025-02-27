package wishdalmod.helpers.texiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class FlashbangEffect
        extends AbstractGameEffect {
    private final float toHalfBlack;
    private final float toBlack;
    private final float toWhite;
    private final boolean hideCards;

    public FlashbangEffect() {
        this.duration = 0.8F;
        this.toHalfBlack = 0.15F;
        this.toBlack = 0.25F;
        this.toWhite = 0.1F;
        this.startingDuration = this.duration;
        this.color = new Color(255.0F, 255.0F, 255.0F, 0.0F);
        this.hideCards = Settings.hideCards;
        Settings.hideCards = true;
    }


    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration > this.startingDuration - this.toHalfBlack) {
            this.color.a = (this.startingDuration - this.duration) / this.toHalfBlack * 0.5F;
        } else if (this.duration > this.startingDuration - this.toHalfBlack - this.toBlack) {
            this.color.a = (this.startingDuration - this.toHalfBlack - this.duration) / this.toBlack * 0.5F + 0.5F;
        } else if (this.duration < this.toWhite) {
            this.color.a = this.duration / this.toWhite;
        } else {
            this.color.a = 1.0F;
        }
        if (this.duration < 0.0F) {
            this.isDone = true;
            Settings.hideCards = this.hideCards;
        }
    }


    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixelTexture = new Texture(pixmap);
        pixmap.dispose();
        sb.draw(pixelTexture, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
    }

    public void dispose() {}
}
