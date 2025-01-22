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
import wishdalmod.helpers.ModHelper;

import java.util.Iterator;

public class GoldBoneDice extends CustomRelic {
    public static final String ID = ModHelper.makePath("GoldBoneDice");
    private static final String IMG = "wishdaleResources/images/relics/GoldBoneDice.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/GoldBoneDice_o.png";
    private float MODIFIER_AMT = 0.15F;
    public GoldBoneDice() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0] + 15 + this.DESCRIPTIONS[1];
    }
    public boolean canSpawn() {
        return Settings.isEndless || AbstractDungeon.floorNum <= 52;
    }
    public void atBattleStart() {
        this.flash();
        Iterator var1 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
        while(var1.hasNext()) {
            AbstractMonster m = (AbstractMonster)var1.next();
            if (m.currentHealth > (int)((float)m.maxHealth * (1.0F - this.MODIFIER_AMT))) {
                m.currentHealth = (int)((float)m.maxHealth * (1.0F - this.MODIFIER_AMT));
                m.healthBarUpdatedEvent();
            }
        }
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    public CustomRelic makeCopy() {
        return new GoldBoneDice();
    }
}