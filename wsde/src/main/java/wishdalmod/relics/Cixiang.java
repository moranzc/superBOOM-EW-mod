package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

import java.util.Iterator;

public class Cixiang extends CustomRelic {

    public static final String ID = ModHelper.makePath("Cixiang");
    private static final String IMG = "wishdaleResources/images/relics/Cixiang.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Cixiang_o.png";

    private static final int THORNS_AMOUNT = 2;

    public Cixiang() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + THORNS_AMOUNT + this.DESCRIPTIONS[1];
    }
    public void updateDescription(AbstractPlayer.PlayerClass c) {
        this.description = this.getUpdatedDescription();
        this.tips.clear();
        this.tips.add(new PowerTip(this.name, this.description));
        this.initializeTips();
    }
    public void atBattleStart() {
        Iterator<AbstractMonster> it = AbstractDungeon.getMonsters().monsters.iterator();
        while (it.hasNext()) {
            AbstractMonster m = it.next();
            this.addToTop(new RelicAboveCreatureAction(m, this));
            m.addPower(new ThornsPower(m, THORNS_AMOUNT));
        }
        AbstractDungeon.onModifyPower();
    }
    public void onSpawnMonster(AbstractMonster monster) {
        monster.addPower(new ThornsPower(monster, THORNS_AMOUNT));
        AbstractDungeon.onModifyPower();
    }
    public void onEquip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }
    public void onUnequip() {
        --AbstractDungeon.player.energy.energyMaster;
    }
    public AbstractRelic makeCopy() {
        return new Cixiang();
    }
}
