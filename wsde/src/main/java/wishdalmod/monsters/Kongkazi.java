package wishdalmod.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import wishdalmod.helpers.ModHelper;

public class Kongkazi extends CustomMonster {
    public static final String ID = ModHelper.makePath("Kongkazi");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String IMG = "wishdaleResources/images/monsters/Kongkazi.png";
    private static final int HP_MIN = 57;
    private static final int HP_MAX = 63;
    private static final int A_HP_MIN = 61;
    private static final int A_HP_MAX = 74;

    private DamageInfo attack1;
    private DamageInfo attack2;

    public Kongkazi(float x, float y) {
        super(NAME, ID, HP_MAX, 0.0F, 0.0F, 180.0F, 250.0F, IMG, x, y);

        // 确保位置有效
        if (x < 0 || y < 0) {
            x = Settings.WIDTH / 2f;
            y = Settings.HEIGHT * 0.3f;
        }

        // 确保位置在屏幕范围内
        float minX = Settings.WIDTH * 0.1f;
        float maxX = Settings.WIDTH * 0.9f;
        float minY = Settings.HEIGHT * 0.2f;
        float maxY = Settings.HEIGHT * 0.8f;

        this.drawX = MathUtils.clamp(x, minX, maxX);
        this.drawY = MathUtils.clamp(y, minY, maxY);

        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
        }
        attack1 = new DamageInfo(this, 15);
        attack2 = new DamageInfo(this, 10);
        this.damage.add(attack1);
        this.damage.add(attack2);
    }

    @Override
    public void getMove(int num) {
        if (num < 33) {
            setMove((byte)0, Intent.ATTACK, attack1.base);
        } else if (num < 66) {
            setMove((byte)1, Intent.ATTACK, attack2.base);
        } else {
            setMove((byte)2, Intent.DEBUFF);
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, attack1, AbstractGameAction.AttackEffect.SLASH_HEAVY));
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, attack2, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
                break;
            case 2:
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDrawPileAction(new VoidCard(), 1, true, true));
                break;
        }
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }
}