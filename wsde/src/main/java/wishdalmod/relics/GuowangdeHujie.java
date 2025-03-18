package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import wishdalmod.helpers.ModHelper;

public class GuowangdeHujie extends CustomRelic {
    public static final String ID = ModHelper.makePath("GuowangdeHujie");
    private static final String IMG = "wishdaleResources/images/relics/GuowangdeHujie.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/GuowangdeHujie_o.png";
    private int lostMaxHealth = 0;
    public GuowangdeHujie() {
        super(ID, new Texture(IMG), new Texture(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void onEquip() {
        lostMaxHealth = AbstractDungeon.player.maxHealth - 1;
        AbstractDungeon.player.maxHealth = 1;
        AbstractDungeon.player.currentHealth = 1;
        AbstractDungeon.player.healthBarUpdatedEvent();
        this.counter = lostMaxHealth;
    }
    public void atBattleStart() {
        flash();
        this.counter = lostMaxHealth;
    }
    public int onLoseHpLast(int damageAmount) {
        if (AbstractDungeon.player.currentHealth - damageAmount <= 0) {
            if (counter >= damageAmount) {
                flash();
                AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                        AbstractDungeon.player.hb.cX,
                        AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f,
                        "护戒生效", Color.GOLD.cpy()));
                counter -= damageAmount;
                return 0;
            } else {
                return damageAmount;
            }
        }
        return damageAmount;
    }
    public void onVictory() {
        flash();
        this.counter = lostMaxHealth;
    }
    public AbstractRelic makeCopy() {
        return new GuowangdeHujie();
    }
}
