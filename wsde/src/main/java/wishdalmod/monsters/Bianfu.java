package wishdalmod.monsters;

import basemod.abstracts.CustomMonster;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.MonsterStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FlightPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.BianfuPower;

public class Bianfu extends CustomMonster {
    public static final String ID = ModHelper.makePath("Bianfu");
    private static final MonsterStrings monsterStrings = CardCrawlGame.languagePack.getMonsterStrings(ID);
    public static final String NAME = monsterStrings.NAME;
    public static final String IMG = "wishdaleResources/images/monsters/Bianfu.png";
    private static final int HP_MIN = 12;
    private static final int HP_MAX = 15;
    private static final int A_HP_MIN = 13;
    private static final int A_HP_MAX = 17;

    private int turnCounter = 1; // 回合计数

    public Bianfu(float x, float y) {
        super(NAME, ID, HP_MAX, 0.0F, 0.0F, 120.0F, 180.0F, IMG, x, y);
        if (AbstractDungeon.ascensionLevel >= 7) {
            setHp(A_HP_MIN, A_HP_MAX);
        } else {
            setHp(HP_MIN, HP_MAX);
        }
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new FlightPower(this, 33), 33));
    }

    @Override
    public void getMove(int num) {
        if (turnCounter == 1) {
            setMove((byte)0, Intent.UNKNOWN);
        } else if (turnCounter == 2) {
            setMove((byte)1, Intent.DEBUFF);
        } else if (turnCounter == 3) {
            setMove((byte)2, Intent.BUFF);
        } else {
            setMove((byte)3, Intent.NONE);
        }
    }

    @Override
    public void takeTurn() {
        switch (this.nextMove) {
            case 0:
                break;
            case 1:
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(AbstractDungeon.player, this, new BianfuPower(AbstractDungeon.player, this))
                );
                break;
            case 2:
                int healAmount = HP_MIN - this.currentHealth;
                if (healAmount > 0) {
                    AbstractDungeon.actionManager.addToBottom(new HealAction(this, this, healAmount));
                }
                AbstractDungeon.actionManager.addToBottom(
                        new ApplyPowerAction(this, this, new IntangiblePlayerPower(this, 99), 99)
                );
                break;
            case 3:
                break;
        }
        turnCounter++;
        AbstractDungeon.actionManager.addToBottom(new RollMoveAction(this));
    }

    @Override
    public void die() {
        // 在死亡前记录位置到对应的BianfuPower
        for (AbstractPower p : AbstractDungeon.player.powers) {
            if (p instanceof BianfuPower) {
                BianfuPower power = (BianfuPower) p;
                // 匹配源便符
                if (power.source == this) {
                    power.setBianfuDeathPos(this.hb.cX, this.hb.cY); // 使用碰撞中心坐标
                }
            }
        }

        super.die();
        // 只移除一层便符power
        AbstractDungeon.actionManager.addToBottom(
                new ReducePowerAction(AbstractDungeon.player, this, BianfuPower.POWER_ID, 1)
        );
    }
}