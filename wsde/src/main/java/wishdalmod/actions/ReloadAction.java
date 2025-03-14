package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.core.Settings;

public class ReloadAction extends AbstractGameAction {
    private int cardCount;

    public ReloadAction(AbstractCreature source) {
        this.source = source;
        this.actionType = ActionType.WAIT;
        this.startDuration = Settings.ACTION_DUR_FAST;
        this.duration = this.startDuration;
    }
    public void update() {
        cardCount = AbstractDungeon.player.hand.size();
        if (cardCount > 0) {
            this.addToTop(new ExhaustAction(cardCount, true, true));
        }
        if (cardCount > 0) {
            this.addToTop(new DrawCardAction(AbstractDungeon.player, cardCount));
        }
        this.isDone = true;
    }
}
