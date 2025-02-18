package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class LinguangyishanAction extends AbstractGameAction {
    private AbstractPlayer p;

    public LinguangyishanAction(AbstractPlayer p) { this.p = p; } private AbstractCard returnCard;
    public void update() {
        this.returnCard = AbstractDungeon.getCard(AbstractCard.CardRarity.RARE, AbstractDungeon.cardRandomRng);
        this.returnCard.setCostForTurn(-99);
        addToBot(new MakeTempCardInHandAction(this.returnCard, 1));
        this.isDone = true;
    }
}
