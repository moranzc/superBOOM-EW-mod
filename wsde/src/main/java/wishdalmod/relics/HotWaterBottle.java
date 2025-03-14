package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

public class HotWaterBottle extends CustomRelic {
    public static final String ID = ModHelper.makePath("HotWaterBottle");
    private static final String IMG = "wishdaleResources/images/relics/HotWaterBottle.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/HotWaterBottle_o.png";

    public HotWaterBottle() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.COMMON, AbstractRelic.LandingSound.FLAT);
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }
    public void onEquip() {
        AbstractDungeon.actionManager.addToTop(
                new RelicAboveCreatureAction(AbstractDungeon.player, this));
        AbstractDungeon.player.gainGold(15);
        AbstractDungeon.player.increaseMaxHp(2, true);
        this.flash();
    }
    public AbstractRelic makeCopy() {
        return new HotWaterBottle();
    }
}