package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class BianyuanxingzhePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("BianyuanxingzhePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int bufferStacks;
    public BianyuanxingzhePower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.bufferStacks = 1;
        this.type = PowerType.BUFF;
        String path128 = "wishdaleResources/images/powers/Bianyuanxingzhe84.png";
        String path48 = "wishdaleResources/images/powers/Bianyuanxingzhe32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
    public void atStartOfTurn() {
        flash();
        addToBot(new GainBlockAction(owner, owner, 1));
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        flash();
        addToBot(new GainBlockAction(owner, owner, 1));
    }
    public float modifyBlock(float block) {
        return block + this.amount;
    }
    public void atEndOfTurn(boolean isPlayer) {
        flash();
        addToBot(new GainBlockAction(owner, owner, 1));
    }
    public void stackPower(int stackAmount) {
        this.bufferStacks += stackAmount;
    }
}