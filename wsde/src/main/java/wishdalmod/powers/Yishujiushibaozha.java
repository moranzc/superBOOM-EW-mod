package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import wishdalmod.helpers.ModHelper;
import com.megacrit.cardcrawl.core.AbstractCreature;

public class Yishujiushibaozha extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("Yishujiushibaozha");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public Yishujiushibaozha(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = Amount;
        String path128 = "wishdaleResources/images/powers/Yishujiushibaozha84.png";
        String path48 = "wishdaleResources/images/powers/Yishujiushibaozha32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }
    public void atEndOfTurn(boolean isPlayer) {
        super.atEndOfTurn(isPlayer);
        this.amount--;
        if (this.amount == 0) {
            AbstractPlayer p = AbstractDungeon.player;
            addToBot(new RemoveSpecificPowerAction(p, p, POWER_ID));
            for (AbstractMonster m : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                m.currentHealth = 1;
                m.damage(new DamageInfo(m, 999, DamageInfo.DamageType.HP_LOSS));
                addToBot(new VFXAction(new ExplosionSmallEffect(m.hb.cX, m.hb.cY), 0.1F));
            }
        }
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }
}
