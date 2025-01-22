package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GuoziaAction extends AbstractGameAction {
    private int damage;
    private AbstractMonster m;

    public GuoziaAction(int damage, AbstractMonster m) {
        this.duration = Settings.ACTION_DUR_FAST;
        this.damage = damage;
        this.m = m;
    }


    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            if (c.type == AbstractCard.CardType.ATTACK) {
                addToBot(new DrawCardAction(1));
                addToBot(new DamageAction(this.m, new DamageInfo(AbstractDungeon.player, this.damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
                addToBot(new GuoziaAction(this.damage, this.m));
            }
        }
        this.isDone = true;
    }
}
