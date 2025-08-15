package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.watcher.SkipEnemiesTurnAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class MositimaoneAction extends AbstractGameAction {
    private final AbstractPlayer p;
    private final int energyLeft;

    public MositimaoneAction(AbstractPlayer p) {
        this.p = p;
        this.energyLeft = p.energy.energy;
    }
    public void update() {
        p.hand.group.forEach(card -> card.retain = true);
        AbstractDungeon.actionManager.addToTop(new AbstractGameAction() {
            public void update() {
                p.energy.energy = energyLeft;
                AbstractDungeon.player.powers.forEach(power -> power.atStartOfTurn());
                AbstractDungeon.player.relics.forEach(relic -> relic.atTurnStart());
                this.isDone = true;
            }
        });
        AbstractDungeon.actionManager.addToTop(new SkipEnemiesTurnAction());
        this.isDone = true;
    }
}
