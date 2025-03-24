package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.List;

public class RandomizeDrawCardStatsAction extends AbstractGameAction {
    private final List<AbstractCard> cardsToModify;

    public RandomizeDrawCardStatsAction(List<AbstractCard> cards) {
        this.cardsToModify = cards;
    }
    public void update() {
        for (AbstractCard c : cardsToModify) {
            if (c.cost >= 0) {
                int newCost = AbstractDungeon.cardRandomRng.random(4);
                if (c.cost != newCost) {
                    c.cost = newCost;
                    c.costForTurn = newCost;
                    c.isCostModified = true;
                }
            }

            if (c.baseDamage > 0) {
                c.baseDamage = AbstractDungeon.cardRandomRng.random(1, 32);
                c.isDamageModified = true;
            }

            if (c.baseBlock > 0) {
                c.baseBlock = AbstractDungeon.cardRandomRng.random(1, 30);
                c.isBlockModified = true;
            }

            if (c.baseMagicNumber > 0) {
                c.baseMagicNumber = AbstractDungeon.cardRandomRng.random(1, 6);
                c.magicNumber = c.baseMagicNumber;
                c.isMagicNumberModified = true;
            }
            c.applyPowers();
        }
        this.isDone = true;
    }
}
