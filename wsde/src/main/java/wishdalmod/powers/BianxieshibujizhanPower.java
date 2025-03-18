package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect;
import wishdalmod.helpers.ModHelper;

public class BianxieshibujizhanPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("BianxieshibujizhanPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public BianxieshibujizhanPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = amount;
        String path128 = "wishdaleResources/images/powers/Bianxieshibujizhan84.png";
        String path48 = "wishdaleResources/images/powers/Bianxieshibujizhan32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() { this.description = DESCRIPTIONS[0]; }
    public void onShuffle() {
        addToBot(new GainEnergyAction(this.amount));
        flash();
        AbstractDungeon.effectList.add(new TextAboveCreatureEffect(
                AbstractDungeon.player.hb.cX - AbstractDungeon.player.animX,
                AbstractDungeon.player.hb.cY + AbstractDungeon.player.hb.height / 2f,
                "sp+1", new Color(0F, 255F, 255F, 1.0F).cpy()));
    }
}