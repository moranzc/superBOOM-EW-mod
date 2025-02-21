package wishdalmod.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.characters.EW;
import wishdalmod.powers.Canying;


public class CanyingAction extends AbstractGameAction {
    public CanyingAction(AbstractCreature target, AbstractCreature source, int amount) {
        this.source = source;
        this.target = target;
        this.amount = amount;
    }

    public void update() {
        if (this.source == AbstractDungeon.player && AbstractDungeon.player instanceof EW) {
            this.amount = ((EW)AbstractDungeon.player).CanyingXiaoguo.onGiveCanying(this.amount);
        }
        addToTop(new ApplyPowerAction(this.target, this.source, new Canying(this.target, this.amount)));
        this.isDone = true;
    }
}