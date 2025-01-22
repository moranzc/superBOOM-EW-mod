package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class YinbiPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("YinbiPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public YinbiPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.amount = -1;
        String path128 = "wishdaleResources/images/powers/YinbiPower84.png";
        String path48 = "wishdaleResources/images/powers/YinbiPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
        this.type = PowerType.DEBUFF;
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        super.onUseCard(card, action);
        if (card.type == AbstractCard.CardType.ATTACK) {
            flash();
            addToBot(new AbstractGameAction() {
                public void update() {
                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, "IntangiblePlayer"));
                    addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, YinbiPower.POWER_ID));
                    this.isDone = true;
                }
            });
        }
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
}
