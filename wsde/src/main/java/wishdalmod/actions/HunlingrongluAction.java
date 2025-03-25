package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import wishdalmod.powers.HunlingrongluPower;

import static com.megacrit.cardcrawl.actions.defect.RecycleAction.TEXT;
public class HunlingrongluAction extends AbstractGameAction {
    private AbstractPlayer p;
    public HunlingrongluAction(AbstractPlayer player, int magicNumber) {
        this.p = player;
        this.amount = magicNumber;
        this.duration = Settings.ACTION_DUR_FAST;
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_FAST) {
            if (this.p.hand.isEmpty()) {
                this.isDone = true;
                return;
            }

            if (this.p.hand.size() == 1) {
                AbstractCard selectedCard = this.p.hand.getBottomCard();
                applyEffect(selectedCard);
                this.p.hand.moveToExhaustPile(selectedCard);
                this.tickDuration();
            } else {
                AbstractDungeon.handCardSelectScreen.open(TEXT[0], 1, false);
                this.tickDuration();
            }
        } else {
            if (!AbstractDungeon.handCardSelectScreen.wereCardsRetrieved) {
                for (AbstractCard c : AbstractDungeon.handCardSelectScreen.selectedCards.group) {
                    applyEffect(c);
                    this.p.hand.moveToExhaustPile(c);
                }
                AbstractDungeon.handCardSelectScreen.wereCardsRetrieved = true;
                AbstractDungeon.handCardSelectScreen.selectedCards.group.clear();
            }
            this.tickDuration();
        }
    }
    private void applyEffect(AbstractCard card) {
        int energyGained = 0;
        if (card.costForTurn == -1) {
            energyGained = EnergyPanel.getCurrentEnergy();
        } else if (card.costForTurn > 0) {
            energyGained = card.costForTurn;
        }

        if (energyGained > 0) {
            this.addToTop(new GainEnergyAction(energyGained));
            this.addToTop(new LoseHPAction(p, p, energyGained));
        }
        if (p.hasPower(HunlingrongluPower.POWER_ID)) {
            HunlingrongluPower power = (HunlingrongluPower) p.getPower(HunlingrongluPower.POWER_ID);
            power.stackPower(this.amount, card);
        } else {
            AbstractDungeon.actionManager.addToBottom(
                    new com.megacrit.cardcrawl.actions.common.ApplyPowerAction(p, p, new HunlingrongluPower(p, this.amount, card), this.amount)
            );
        }
    }
}
