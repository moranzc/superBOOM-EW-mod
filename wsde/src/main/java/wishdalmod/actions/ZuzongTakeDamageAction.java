package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ZuzongTakeDamageAction extends AbstractGameAction {
    private final DamageInfo info;
    private final int damageAmount;

    public ZuzongTakeDamageAction(AbstractCreature target, DamageInfo originalInfo, int amount) {
        this.target = target;
        this.info = new DamageInfo(originalInfo.owner, amount, originalInfo.type);
        this.damageAmount = amount;
    }

    @Override
    public void update() {
        if (target != null && !target.isDeadOrEscaped()) {
            target.damage(info);
            if (target.isPlayer && AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.isDone = true;
    }
}