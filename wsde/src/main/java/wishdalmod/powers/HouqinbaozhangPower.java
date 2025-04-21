package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class HouqinbaozhangPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("HouqinbaozhangPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String TEXTURE_128 = "wishdaleResources/images/powers/HouqinbaozhangPower84.png";
    private static final String TEXTURE_32 = "wishdaleResources/images/powers/HouqinbaozhangPower32.png";
    public HouqinbaozhangPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(TEXTURE_128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(TEXTURE_32), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]+amount+DESCRIPTIONS[1]; }
    public void onExhaust(AbstractCard card) {
        this.flash();
        this.addToBot(new DrawCardAction(this.owner, this.amount));
    }
    public void onManualDiscard() {
        this.flash();
        this.addToBot(new DrawCardAction(this.owner, this.amount));
    }
    public void onPlayCard(AbstractCard card, AbstractMonster m) {
        if (card.type == AbstractCard.CardType.POWER) {
            this.flash();
            this.addToBot(new DrawCardAction(this.owner, this.amount));
        }
    }
}