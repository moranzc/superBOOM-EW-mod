package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import wishdalmod.helpers.ModHelper;

import java.util.Iterator;

public class AncientTreeFruit extends CustomRelic {
    public static final String ID = ModHelper.makePath("AncientTreeFruit");
    private static final String IMG = "wishdaleResources/images/relics/AncientTreeFruit.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/AncientTreeFruit_o.png";
    public AncientTreeFruit() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void onEquip() {
        AbstractDungeon.combatRewardScreen.open();
        AbstractDungeon.combatRewardScreen.rewards.clear();
        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON)));
        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON)));
        AbstractDungeon.combatRewardScreen.rewards.add(new RewardItem(AbstractDungeon.returnRandomScreenlessRelic(RelicTier.COMMON)));
        AbstractDungeon.combatRewardScreen.positionRewards();
        AbstractDungeon.getCurrRoom().rewardPopOutTimer = 0.25F;
        this.flash();
    }
    public CustomRelic makeCopy() {
        return new AncientTreeFruit();
    }
}