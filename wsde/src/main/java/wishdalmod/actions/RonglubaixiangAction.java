package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class RonglubaixiangAction extends AbstractGameAction {
    private final AbstractPlayer player;

    public RonglubaixiangAction(AbstractPlayer player) {
        this.player = player;
    }

    public void update() {
        if (!player.hand.isEmpty()) {
            AbstractDungeon.actionManager.addToTop(new ExhaustAction(1, false));
        }
        int roll = AbstractDungeon.cardRandomRng.random(99);
        AbstractCard.CardRarity rarity;

        if (roll < 30) {
            rarity = AbstractCard.CardRarity.COMMON;
        } else if (roll < 70) {
            rarity = AbstractCard.CardRarity.UNCOMMON;
        } else if (roll < 90) {
            rarity = AbstractCard.CardRarity.RARE;
        } else if (roll < 97) {
            AbstractCard rareCard = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, AbstractDungeon.cardRandomRng);
            rareCard.upgrade();
            AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(rareCard, 1));
            this.isDone = true;
            return;
        } else if (roll < 99) {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(66, 0, 0));
            this.isDone = true;
            return;
        } else {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(30, 0, 0));
            player.increaseMaxHp(15, true);
            this.isDone = true;
            return;
        }
        AbstractCard newCard = AbstractDungeon.getCard(rarity, AbstractDungeon.cardRandomRng);
        AbstractDungeon.actionManager.addToTop(new MakeTempCardInHandAction(newCard, 1));
        this.isDone = true;
    }
}
