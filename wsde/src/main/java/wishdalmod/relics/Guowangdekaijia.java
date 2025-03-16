package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import wishdalmod.helpers.ModHelper;

public class Guowangdekaijia extends CustomRelic {
    public static final String ID = ModHelper.makePath("Guowangdekaijia");
    private static final String IMG = "wishdaleResources/images/relics/Guowangdekaijia.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Guowangdekaijia_o.png";
    private int blurStacks = 1;

    public Guowangdekaijia() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.HEAVY);
        this.counter = blurStacks;
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart() {
        flash();
        int missingHealth = AbstractDungeon.player.maxHealth - AbstractDungeon.player.currentHealth;
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, missingHealth));
        if (blurStacks > 0) {
            AbstractDungeon.player.addPower(new BlurPower(AbstractDungeon.player, 2));
            AbstractDungeon.player.addPower(new ArtifactPower(AbstractDungeon.player, blurStacks));
        }
        this.counter = blurStacks;
    }
    public void onVictory() {
        if (AbstractDungeon.player.currentHealth > 1) {
            AbstractDungeon.player.currentHealth = Math.max(1, AbstractDungeon.player.currentHealth - 3);
            AbstractDungeon.player.healthBarUpdatedEvent();
        }
        flash();
        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX,
                AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f,
                "-3", Color.SCARLET.cpy()));
        blurStacks++;
        this.counter = blurStacks;
    }
    public CustomRelic makeCopy() {
        return new Guowangdekaijia();
    }
}
