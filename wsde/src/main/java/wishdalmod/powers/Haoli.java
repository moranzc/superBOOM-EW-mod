package wishdalmod.powers;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class Haoli
        extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("Haoli");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private int turnCount;
    public Haoli(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.DEBUFF;
        this.amount = amount;
        this.turnCount = 1;
        String path128 = "wishdaleResources/images/powers/Haoli84.png";
        String path48 = "wishdaleResources/images/powers/Haoli32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }

    public void reSetTurn() { this.turnCount = 1; }

    public void atEndOfRound() {
        this.turnCount--;
        if (this.turnCount == 0) {
            addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        }
        updateDescription();
    }

    public void atStartOfTurn() {
        flash();
        for (AbstractCreature enemy : AbstractDungeon.getMonsters().monsters) {
            if (!enemy.isDeadOrEscaped()) {
                addToBot(new LoseHPAction(enemy, this.owner, 9*amount));
            }
        }
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 0.3F;
        }
        return damage;
    }

    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
}
