package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class GoushiPinghengAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final boolean upgraded;

    public GoushiPinghengAction(AbstractPlayer player, boolean upgraded) {
        this.player = player;
        this.upgraded = upgraded;
    }

    @Override
    public void update() {
        int maxBlock = upgraded ? 499 : 999;

        int playerBlock = AbstractDungeon.aiRng.random(1, maxBlock);
        this.addToBot(new GainBlockAction(player, player, playerBlock));
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(player.hb.cX, player.hb.cY, AttackEffect.FIRE));

        for (AbstractMonster monster : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!monster.isDeadOrEscaped()) {
                int monsterBlock = AbstractDungeon.aiRng.random(1, maxBlock);
                this.addToBot(new GainBlockAction(monster, monster, monsterBlock));
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(monster.hb.cX, monster.hb.cY, AttackEffect.FIRE));
            }
        }

        this.isDone = true;
    }
}
