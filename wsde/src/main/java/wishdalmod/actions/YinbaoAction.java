package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;

public class YinbaoAction
        extends AbstractGameAction
{
    public int[] multiDamage;
    private final boolean freeToPlayOnce;
    private final DamageInfo.DamageType damageType;
    private final AbstractPlayer p;
    private final int energyOnUse;
    private final int magicNumber;

    public YinbaoAction(AbstractPlayer p, int[] multiDamage, DamageInfo.DamageType damageType, boolean freeToPlayOnce, int energyOnUse, int magicNumber) {
        this.multiDamage = multiDamage;
        this.damageType = damageType;
        this.p = p;
        this.freeToPlayOnce = freeToPlayOnce;
        this.duration = Settings.ACTION_DUR_XFAST;
        this.actionType = AbstractGameAction.ActionType.SPECIAL;
        this.energyOnUse = energyOnUse;
        this.magicNumber = magicNumber;
    }

    public void update() {
        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {

            for (int i = 0; i < effect; i++) {
                addToBot(new SFXAction("ATTACK_PIERCING_WAIL"));
                addToBot(new VFXAction(this.p, new ShockWaveEffect(this.p.hb.cX, this.p.hb.cY, Settings.GREEN_TEXT_COLOR, ShockWaveEffect.ShockWaveType.CHAOTIC), 0.3F));
                addToBot(new DamageAllEnemiesAction(this.p, this.multiDamage, this.damageType, AbstractGameAction.AttackEffect.NONE, true));

                for (AbstractMonster mo : (AbstractDungeon.getMonsters()).monsters) {
                    addToBot(new ApplyPowerAction(mo, this.p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    if (!mo.hasPower("Artifact")) {
                        addToBot(new ApplyPowerAction(mo, this.p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE)); continue;
                    }
                    if (i >= (mo.getPower("Artifact")).amount) {
                        addToBot(new ApplyPowerAction(mo, this.p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                    }
                }
            }




            if (!this.freeToPlayOnce) {
                this.p.energy.use(EnergyPanel.totalCount);
            }
        }

        this.isDone = true;
    }
}
