package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

import static com.megacrit.cardcrawl.daily.mods.Lethality.STR_AMT;

public class RoaringHand extends CustomRelic {
    public static final String ID = ModHelper.makePath("RoaringHand");
    private static final String IMG = "wishdaleResources/images/relics/RoaringHand.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/RoaringHand_o.png";
    private int count = 0;
    private boolean restart;
    public RoaringHand() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, AbstractRelic.LandingSound.FLAT);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atTurnStart() {
        if (this.restart) {
            this.count = 0;
        } else {
            this.restart = true;
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            if (this.count < 15) {
                this.count++;
                this.flash();
                this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
            this.restart = false;
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, this.count), this.count));
            this.addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseStrengthPower(AbstractDungeon.player, this.count), this.count));
            this.flash();
        }
    }
    public void onVictory() {
        this.count = 0;
    }
    public CustomRelic makeCopy() {
        return new RoaringHand();
    }
}