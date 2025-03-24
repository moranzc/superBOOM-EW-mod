package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class QinshiAction extends AbstractGameAction {
    private int magicNumber;

    public QinshiAction(int magicNumber) {
        this.magicNumber = magicNumber;
    }
    public void update() {
        for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
            AbstractDungeon.actionManager.addToBottom(new CanyingAction(m, AbstractDungeon.player, this.magicNumber));
        }
        this.isDone = true;
    }
}
