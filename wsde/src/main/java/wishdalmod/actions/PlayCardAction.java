package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class PlayCardAction extends AbstractGameAction {
    private boolean exhaustCards;
    private AbstractPlayer p;
    private boolean isRandom;
    public PlayCardAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, boolean exhaustCards) {
        this.exhaustCards = false;
        this.isAutoPlay = true;
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.exhaustCards = exhaustCards;
    } private boolean anyNumber; private boolean canPickZero; public static int numPlayed; private boolean isAutoPlay; public PlayCardAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero) {
        this.exhaustCards = false;
        this.isAutoPlay = true;
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
    } public PlayCardAction(int amount, boolean isRandom, boolean anyNumber, boolean canPickZero, boolean exhaustCards, boolean isAutoPlay) {
        this.exhaustCards = false;
        this.isAutoPlay = true;
        this.anyNumber = anyNumber;
        this.p = AbstractDungeon.player;
        this.canPickZero = canPickZero;
        this.isRandom = isRandom;
        this.amount = amount;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.actionType = AbstractGameAction.ActionType.EXHAUST;
        this.exhaustCards = exhaustCards;
        this.isAutoPlay = isAutoPlay;
    }
    public void update() {
        if (this.duration == this.startDuration) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;

                return;
            }

            if (!this.anyNumber && this.p.hand.size() <= this.amount) {
                this.amount = this.p.hand.size();
                numPlayed = this.amount;
                int i = this.p.hand.size();

                for (int j = 0; j < i; j++) {
                    AbstractCard card = this.p.hand.getTopCard();
                    playCards(card);
                }
                CardCrawlGame.dungeon.checkForPactAchievement();
                this.isDone = true;

                return;
            }
            if (!this.isRandom) {
                numPlayed = this.amount;
                AbstractDungeon.handCardSelectScreen.open("������", this.amount, this.anyNumber, this.canPickZero);
                tickDuration();

                return;
            }
            for (int i = 0; i < this.amount; i++) {
                AbstractCard card = AbstractDungeon.player.hand.getRandomCard(true);
                playCards(card);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
        }

        if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {

            for (AbstractCard card : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                playCards(card);
            }

            CardCrawlGame.dungeon.checkForPactAchievement();
            AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
        }

        tickDuration();
    }

    private void playCards(AbstractCard card) {
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
        addToTop(new NewQueueCardAction(card, true, false, this.isAutoPlay));
        addToTop(new UnlimboAction(card));
        addToTop(new WaitAction(Settings.ACTION_DUR_FASTER));
    }
}
