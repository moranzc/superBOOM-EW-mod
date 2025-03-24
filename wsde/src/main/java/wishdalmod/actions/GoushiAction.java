package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GoushiAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final boolean upgraded;

    public GoushiAction(AbstractPlayer player, boolean upgraded) {
        this.player = player;
        this.upgraded = upgraded;
    }
    public void update() {
        int newPlayerHp = AbstractDungeon.aiRng.random(1, player.maxHealth);
        player.currentHealth = newPlayerHp;
        player.healthBarUpdatedEvent();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(player.hb.cX, player.hb.cY, AttackEffect.FIRE));
        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                int maxRange = upgraded ? Math.max(1, monster.maxHealth / 2) : monster.maxHealth;
                int newMonsterHp = AbstractDungeon.aiRng.random(1, maxRange);
                monster.currentHealth = newMonsterHp;
                monster.healthBarUpdatedEvent();
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.FIRE));
            }
        }
        this.isDone = true;
    }
}
