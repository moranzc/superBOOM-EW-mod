package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class SpiritArmorPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("SpiritArmorPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public SpiritArmorPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = "SpiritArmorPower";
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = amount;
        String path128 = "wishdaleResources/images/powers/SpiritArmor84.png";
        String path48 = "wishdaleResources/images/powers/SpiritArmor32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
    public int onLoseHp(int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            int reduction = Math.min(this.amount, damageAmount);
            return damageAmount - reduction;
        }
        return damageAmount;
    }

}
