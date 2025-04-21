package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import wishdalmod.helpers.ModHelper;

public class Guowangdeyanshen extends CustomRelic {
    public static final String ID = ModHelper.makePath("Guowangdeyanshen");
    private static final String IMG = "wishdaleResources/images/relics/Guowangdeyanshen.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Guowangdeyanshen_o.png";
    private static final float HP_THRESHOLD = 0.1f;
    private static final int ABSOLUTE_THRESHOLD = 10;
    private static final int TURN_INTERVAL = 2;
    private static final int ENERGY_AMT = 1;

    public Guowangdeyanshen() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL),
                AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
        this.counter = 0;
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + TURN_INTERVAL + DESCRIPTIONS[1];
    }
    public void atPreBattle() {
        this.counter = 0;
    }
    public void atTurnStart() {
        if (AbstractDungeon.getCurrRoom() == null ||
                AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) {
            return;
        }
        if (isBelowThreshold()) {
            if (this.counter == -1) {
                this.counter += 2;
            } else {
                this.counter++;
            }
            if (this.counter >= TURN_INTERVAL) {
                triggerEffect();
                this.counter = 0;
            }
        } else {
            this.counter = 0;
        }
    }
    private boolean isBelowThreshold() {
        AbstractPlayer p = AbstractDungeon.player;
        return p.currentHealth <= p.maxHealth * HP_THRESHOLD ||
                p.currentHealth <= ABSOLUTE_THRESHOLD;
    }
    private void triggerEffect() {
        flash();
        addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToTop(new GainEnergyAction(ENERGY_AMT));
        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX,
                AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f,
                "sp+1", new Color(0F, 255F, 255F, 1.0F).cpy()));
    }
    public AbstractRelic makeCopy() {
        return new Guowangdeyanshen();
    }
}