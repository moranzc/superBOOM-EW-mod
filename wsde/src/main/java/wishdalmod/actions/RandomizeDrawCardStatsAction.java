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
            int minBlock = 0, maxBlock = 0;
            int minDamage = 0, maxDamage = 0;
            int minMagic = 0, maxMagic = 0;
            int minCost = 0, maxCost = 0;

            switch (c.rarity) {
                case BASIC:
                    minBlock = 4; maxBlock = 10;
                    minDamage = 5; maxDamage = 11;
                    minMagic = 1; maxMagic = 3;
                    minCost = 0; maxCost = 2;
                    break;
                case COMMON:
                    minBlock = 5; maxBlock = 13;
                    minDamage = 6; maxDamage = 15;
                    minMagic = 1; maxMagic = 4;
                    minCost = 0; maxCost = 3;
                    break;
                case UNCOMMON:
                    minBlock = 6; maxBlock = 20;
                    minDamage = 7; maxDamage = 28;
                    minMagic = 2; maxMagic = 5;
                    minCost = 0; maxCost = 4;
                    break;
                case RARE:
                    minBlock = 8; maxBlock = 40;
                    minDamage = 9; maxDamage = 42;
                    minMagic = 2; maxMagic = 6;
                    minCost = 1; maxCost = 4;
                    break;
            }

            if (c.cost >= 0) {
                int newCost = AbstractDungeon.cardRandomRng.random(minCost, maxCost);
                if (c.cost != newCost) {
                    c.cost = newCost;
                    c.costForTurn = newCost;
                    c.isCostModified = true;
                }
            }

            if (c.baseDamage > 0) {
                c.baseDamage = AbstractDungeon.cardRandomRng.random(minDamage, maxDamage);
                c.isDamageModified = true;
            }

            if (c.baseBlock > 0) {
                c.baseBlock = AbstractDungeon.cardRandomRng.random(minBlock, maxBlock);
                c.isBlockModified = true;
            }

            if (c.baseMagicNumber > 0) {
                c.baseMagicNumber = AbstractDungeon.cardRandomRng.random(minMagic, maxMagic);
                c.magicNumber = c.baseMagicNumber;
                c.isMagicNumberModified = true;
            }
            c.applyPowers();
        }
        this.isDone = true;
    }
}
