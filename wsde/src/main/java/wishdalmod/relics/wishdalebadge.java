package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

public class wishdalebadge extends CustomRelic {
    public static final String ID = ModHelper.makePath("wishdalebadge");
    private static final String IMG = "wishdaleResources/images/relics/wishdalebadge.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/wishdalebadge_o.png";
    private boolean activated = true;

    public wishdalebadge() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL),AbstractRelic.RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    public CustomRelic makeCopy() {
        return new wishdalebadge();
    }

    public void atBattleStart() {
        super.atBattleStart();
        this.activated = true;
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && this.activated){
            this.activated = false;
            this.flash();
            AbstractCreature target = action.target;
            AbstractCard tmp = card.makeSameInstanceOf();
            tmp.current_x = card.current_x;
            tmp.current_y = card.current_y;
            tmp.target_x = (float)Settings.WIDTH / 2.0F - 300.0F * Settings.scale;
            tmp.target_y = (float)Settings.HEIGHT / 2.0F;
            tmp.applyPowers();
            tmp.purgeOnUse = true;
            tmp.baseDamage = tmp.baseDamage / 2;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, (AbstractMonster) target, card.energyOnUse, true, true), true);

            this.pulse = false;
        }
    }

    public void atTurnStart() {
        this.activated = true;
    }

    public boolean checkTrigger() {
        return this.activated;
    }
}