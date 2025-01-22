package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.Haoli;

public class ResetHaoli extends AbstractGameAction {
    public ResetHaoli(AbstractCreature target) { this.target = target; }
    public void update() {
        if (this.target.hasPower(ModHelper.makePath("Haoli"))) {
            Haoli apoptosisDamage = (Haoli)this.target.getPower(ModHelper.makePath("Haoli"));
            apoptosisDamage.reSetTurn();
            apoptosisDamage.updateDescription();
        }
        this.isDone = true;
    }
}
