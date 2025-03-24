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
    public void setCounter(int counter) {
        super.setCounter(counter);
        this.lostMaxHealth = counter; // 同步丢失的生命值
    }
    public void onEquip() {
        lostMaxHealth = AbstractDungeon.player.maxHealth - 1;
        AbstractDungeon.player.decreaseMaxHealth(lostMaxHealth);
        AbstractDungeon.player.currentHealth = Math.min(AbstractDungeon.player.currentHealth, 1);
        AbstractDungeon.player.healthBarUpdatedEvent();
        this.counter = lostMaxHealth;
    }
    public void atBattleStart() {
        if (this.counter <= 0) {
            this.counter = lostMaxHealth;
        }
        flash();
    }
    public int onLoseHpLast(int damageAmount) {
        int currentHp = AbstractDungeon.player.currentHealth;
        int lethalDamage = damageAmount - (currentHp - 1);
        if (lethalDamage > 0 && counter > 0) {
            int actualBlock = Math.min(counter, lethalDamage);
            flash();
            AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                    AbstractDungeon.player.hb.cX,
                    AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f,
                    "护戒生效 (" + actualBlock + ")",
                    Color.GOLD.cpy()
            ));
            counter -= actualBlock;
            return damageAmount - actualBlock;
        }
        return damageAmount;
    }
    public void onVictory() {
        flash();
        this.counter = lostMaxHealth;
    }
    public AbstractRelic makeCopy() {
        GuowangdeHujie relic = new GuowangdeHujie();
        relic.lostMaxHealth = this.lostMaxHealth;
        return relic;
    }
}
