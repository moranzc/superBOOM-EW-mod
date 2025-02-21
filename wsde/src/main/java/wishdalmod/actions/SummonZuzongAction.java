package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.characters.EW;

public class SummonZuzongAction extends AbstractGameAction {
    private int maxHealth;
    private int strength;
    private int block;

    public SummonZuzongAction(int maxHealth, int strength, int block) {
        this.maxHealth = maxHealth;
        this.strength = strength;
        this.block = block;
        this.duration = 0.0F;
    }
    @Override
    public void update() {
        if (AbstractDungeon.player instanceof EW) {
            EW ewPlayer = (EW) AbstractDungeon.player;
            ewPlayer.summonZuzong(maxHealth, strength, block);
        }
        isDone = true;
    }
}
