package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.Haoli;

public class HaoliAction extends AbstractGameAction {
    public HaoliAction(AbstractCreature target) {
        this.target = target;
    }
    public void update() {
        if (this.target.hasPower(ModHelper.makePath("Canying"))) {
            AbstractPower apoptosisDamage = this.target.getPower(ModHelper.makePath("Canying"));
            int canyingAmount = apoptosisDamage.amount;
            if (canyingAmount >= 7) {
                int haoliAmount = canyingAmount / 7;
                for (int i = 0; i < haoliAmount; i++) {
                    addToTop(new ApplyPowerAction(this.target, this.target, new Haoli(this.target, 1)));
                }
                int remainingCanying = canyingAmount % 7;
                apoptosisDamage.amount = remainingCanying;
            }
        }
        this.isDone = true;
    }
}
