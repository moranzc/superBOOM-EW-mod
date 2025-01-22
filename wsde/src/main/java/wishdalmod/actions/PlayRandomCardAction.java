package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import wishdalmod.helpers.ModHelper;

import java.util.ArrayList;

public class PlayRandomCardAction
        extends AbstractGameAction {
    private boolean exhaustCards;
    private boolean isAutoPlay;

    public PlayRandomCardAction(AbstractCreature target, boolean exhausts, boolean isAutoPlay) {


        this.duration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.WAIT;
        this.source = AbstractDungeon.player;
        this.target = target;
        this.exhaustCards = exhausts;
        this.isAutoPlay = isAutoPlay;
    }

    public void update() {
        if (AbstractDungeon.player.hand.isEmpty()) {
            this.isDone = true;

            return;
        }
        if (!AbstractDungeon.player.hand.isEmpty()) {

            ArrayList<AbstractCard> cardGroup = new ArrayList<AbstractCard>(AbstractDungeon.player.hand.group);
            if (!this.isAutoPlay) {
                cardGroup.removeIf(c -> ((c.cost > EnergyPanel.getCurrentEnergy() && !c.freeToPlay()) || canUseCard(c)));
            } else {
                cardGroup.removeIf(c -> canUseCard(c));
            }

            if (cardGroup.isEmpty()) {
                this.isDone = true;

                return;
            }
            final AbstractCard card = (AbstractCard)cardGroup.get(AbstractDungeon.cardRandomRng.random(cardGroup.size() - 1));

            AbstractDungeon.player.hand.group.remove(card);
            (AbstractDungeon.getCurrRoom()).souls.remove(card);
            card.exhaustOnUseOnce = this.exhaustCards;
            AbstractDungeon.player.limbo.group.add(card);
            card.current_y = -200.0F * Settings.scale;
            card.target_x = Settings.WIDTH / 2.0F + 200.0F * Settings.xScale;
            card.target_y = Settings.HEIGHT / 2.0F;
            card.targetAngle = 0.0F;
            card.lighten(false);
            card.drawScale = 0.12F;
            card.targetDrawScale = 0.75F;
            card.applyPowers();
            addToTop(new NewQueueCardAction(card, this.target, false, this.isAutoPlay));
            addToTop(new UnlimboAction(card));
            addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
        }
        this.isDone = true;
    }

    private boolean canUseCard(AbstractCard c) {
        if (c.type == AbstractCard.CardType.STATUS && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Medical Kit"))
            return true;
        return (c.type == AbstractCard.CardType.CURSE && c.costForTurn < -1 && !AbstractDungeon.player.hasRelic("Blue Candle"));
    }
}
