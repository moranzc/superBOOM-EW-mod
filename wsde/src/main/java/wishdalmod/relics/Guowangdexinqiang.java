package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

public class Guowangdexinqiang extends CustomRelic {
    public static final String ID = ModHelper.makePath("Guowangdexinqiang");
    private static final String IMG = "wishdaleResources/images/relics/Guowangdexinqiang.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Guowangdexinqiang_o.png";
    private static final float HP_THRESHOLD = 0.1f;
    private static final int ABSOLUTE_THRESHOLD = 10;
    private static final int DEX_AMOUNT = 5;
    private boolean isActive = false;

    public Guowangdexinqiang() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.HEAVY);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 5 + this.DESCRIPTIONS[1];
    }
    public void atBattleStart() {
        this.isActive = false;
        checkAndApplyDexterity();
    }
    public void onBloodied() {
        checkAndApplyDexterity();
    }
    public void onPlayerEndTurn() {
        checkAndApplyDexterity();
    }
    private void checkAndApplyDexterity() {
        AbstractPlayer p = AbstractDungeon.player;
        boolean conditionMet = p.currentHealth <= p.maxHealth * HP_THRESHOLD || p.currentHealth <= ABSOLUTE_THRESHOLD;
        if (!isActive && conditionMet) {
            activateEffect(p);
        } else if (isActive && !conditionMet) {
            deactivateEffect(p);
        }
    }

    private void activateEffect(AbstractPlayer p) {
        this.flash();
        this.pulse = true;
        this.addToBot(new RelicAboveCreatureAction(p, this));
        this.addToBot(new ApplyPowerAction(p, p,
                new DexterityPower(p, DEX_AMOUNT), DEX_AMOUNT));
        isActive = true;
    }

    private void deactivateEffect(AbstractPlayer p) {
        this.addToBot(new ApplyPowerAction(p, p,
                new DexterityPower(p, -DEX_AMOUNT), -DEX_AMOUNT));
        this.stopPulse();
        isActive = false;
    }
    public void onVictory() {
        this.pulse = false;
        this.isActive = false;
    }
    public AbstractRelic makeCopy() {
        return new Guowangdexinqiang();
    }
}
