package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.characters.Zuzong;

public class GiveZuzongBlockAction extends AbstractGameAction {
    private final int blockAmount;

    public GiveZuzongBlockAction(int blockAmount) {
        this.blockAmount = blockAmount;
    }

    @Override
    public void update() {
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            if (monster instanceof Zuzong && !monster.isDeadOrEscaped()) {
                this.addToTop(new GainBlockAction(monster, monster, blockAmount));
            }
        }
        this.isDone = true;
    }
}
