package wishdalmod.helpers.texiao;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;

public class PersistentFlameEffect extends AbstractGameEffect {
    private float timer = 0.0f;
    private static final float INTERVAL = 1.5f;

    public PersistentFlameEffect() {
        this.color = Color.SCARLET.cpy();
        this.duration = Float.MAX_VALUE;
    }
    @Override
    public void update() {
        timer += Gdx.graphics.getDeltaTime();
        if (timer >= INTERVAL) {
            timer = 0;
            AbstractDungeon.effectsQueue.add(new BorderLongFlashEffect(Color.SCARLET));
        }
    }
    @Override
    public void render(SpriteBatch sb) {
    }
    @Override
    public void dispose() {
    }
}
