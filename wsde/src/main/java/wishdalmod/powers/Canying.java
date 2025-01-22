package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import wishdalmod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Canying
        extends AbstractPower {
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
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 522, 700);
        updateDescription();
    }
    public void renderIcons(SpriteBatch sb, float x, float y, Color c){
        super.renderIcons(sb,this.owner.hb.cX,this.owner.hb.cY,c);
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
}
