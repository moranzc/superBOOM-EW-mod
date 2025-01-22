package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ExhaustTopCardAction
        extends AbstractGameAction
{
    public void update() {
        if (!AbstractDungeon.player.drawPile.isEmpty()) {
            AbstractCard card = AbstractDungeon.player.drawPile.getTopCard();
            AbstractDungeon.player.drawPile.group.remove(card);
            (AbstractDungeon.getCurrRoom()).souls.remove(card);
            card.exhaustOnUseOnce = true;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            addToTop(new UnlimboAction(card, true));
            AbstractDungeon.player.hand.moveToExhaustPile(card);
            if (!Settings.FAST_MODE) {
                addToTop(new WaitAction(Settings.ACTION_DUR_MED));
            } else {
                addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
            }
        }

        this.isDone = true;
    }
}
