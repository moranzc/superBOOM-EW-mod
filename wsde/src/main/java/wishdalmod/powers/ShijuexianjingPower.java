package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class ShijuexianjingPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("ShijuexianjingPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ShijuexianjingPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        String path128 = "wishdaleResources/images/powers/ShijuexianjingPower84.png";
        String path48 = "wishdaleResources/images/powers/ShijuexianjingPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        this.flash();
        if (Settings.FAST_MODE) {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount, true));
        } else {
            this.addToBot(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.amount));
        }
    }
}