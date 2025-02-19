package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;


public class BaozhaAction extends AbstractGameAction {
    private boolean isFast;
    public BaozhaAction() {
        this.isFast = false;
    }
    public void update() {
        addToTop(new DamageAllEnemiesAction(
                (AbstractCreature) null,
                DamageInfo.createDamageMatrix(1, true),
                DamageInfo.DamageType.THORNS,
                AbstractGameAction.AttackEffect.NONE,
                true
        ));
        this.isFast = true;
        this.isDone = true;
    }
    public boolean isFast() {
        return this.isFast;
    }
}