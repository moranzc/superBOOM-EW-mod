package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MeideAction extends AbstractGameAction {
    private int count;
    private final int blockPerCard;
    private AbstractPlayer p;

    public MeideAction(AbstractPlayer p, int  blockPerCard) {
        this.count = 0;
        this.p = p;
        this.blockPerCard = blockPerCard;
    }
    public void update() {
        int i;
        for (i = 0; i < this.p.drawPile.size(); ) {
            AbstractCard c = this.p.drawPile.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                this.count++;
                this.p.drawPile.removeCard(c);
                this.p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0.0F, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, this.p.limbo));
                addToBot(new WaitAction(0.1F));
                continue;
            }
            i++;
        }
        for (i = 0; i < this.p.discardPile.size(); ) {
            AbstractCard c = this.p.discardPile.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                this.count++;
                this.p.discardPile.removeCard(c);
                this.p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0.0F, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, this.p.limbo));
                addToBot(new WaitAction(0.1F));
                continue;
            }
            i++;
        }
        for (i = 0; i < this.p.hand.size(); ) {
            AbstractCard c = this.p.hand.group.get(i);
            if (c.type == AbstractCard.CardType.CURSE || c.type == AbstractCard.CardType.STATUS) {
                this.count++;
                this.p.hand.removeCard(c);
                this.p.limbo.addToTop(c);
                c.targetDrawScale = 0.5F;
                c.setAngle(0.0F, true);
                c.target_x = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_WIDTH, Settings.WIDTH - AbstractCard.IMG_WIDTH);
                c.target_y = AbstractDungeon.cardRandomRng.random(AbstractCard.IMG_HEIGHT, Settings.HEIGHT - AbstractCard.IMG_HEIGHT);
                addToBot(new ExhaustSpecificCardAction(c, this.p.limbo));
                addToBot(new WaitAction(0.1F));
                continue;
            }
            i++;
        }
        addToTop(new GainBlockAction(this.p, this.p, this.count * this.blockPerCard));
        this.isDone = true;
    }
}
