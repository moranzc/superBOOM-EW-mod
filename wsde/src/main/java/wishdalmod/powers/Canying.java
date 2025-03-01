package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.Settings;
import wishdalmod.helpers.ImageHelper;
import wishdalmod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Canying extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("Canying");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public Canying(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.amount = amount;
        String path128 = "wishdaleResources/images/powers/Canying84.png";
        String path48 = "wishdaleResources/images/powers/Canying34.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }
    int currentIndex=0;
    public void update(int slot) {
        super.update(slot);
        currentIndex++;
        if (currentIndex >= ImageHelper.canyingpowerList.size())
            currentIndex = 0;
    }
    public void renderIcons(SpriteBatch sb, float x,float y, Color c) {
        super.renderIcons(sb,x,y,c);
        TextureAtlas.AtlasRegion img = ImageHelper.getByIndex(currentIndex);
        sb.setColor(Color.WHITE.cpy());
        sb.draw(img,
                this.owner.hb.cX - (float) img.packedWidth / 2.0F,
                this.owner.hb.cY - (float) img.packedHeight / 2.0F,
                (float) img.packedWidth / 2.0F,
                (float) img.packedHeight / 2.0F,
                (float) img.packedWidth,
                (float) img.packedHeight,
        Settings.scale,
        Settings.scale,
        0.0F);
    }
    public void stackPower(int stackAmount){
        super.stackPower(stackAmount);
        while(this.amount>=7) {
            this.amount -= 7;
            addToTop(new ApplyPowerAction(this.owner, this.owner, new Haoli(this.owner, 1), 1));
        }
        if(this.amount<=0)
            addToTop(new RemoveSpecificPowerAction(this.owner,this.owner,this));
        updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
}
