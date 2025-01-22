package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

public class Lei extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("Lei");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public Lei(AbstractCreature owner) {
        this.name = NAME;
        this.ID = "Lei";
        this.owner = owner;
        this.amount = 2;
        String path128 = "wishdaleResources/images/powers/Lei84.png";
        String path48 = "wishdaleResources/images/powers/Lei32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
        this.type = PowerType.DEBUFF;
    }
    public void updateDescription() {
        StringBuilder sb = new StringBuilder();
        sb.append(powerStrings.DESCRIPTIONS[0]);
        for(int i = 0; i < this.amount; ++i) {
            sb.append("[E] ");
        }
        this.description = DESCRIPTIONS[0];
    }
    public void atStartOfTurn() {
        this.addToBot(new LoseEnergyAction(this.amount));
        this.flash();
    }
}