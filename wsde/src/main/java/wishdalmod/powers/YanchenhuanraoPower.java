package wishdalmod.powers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.DarkenScreenEffect;
import wishdalmod.helpers.texiao.GraySmokeEffect;

public class YanchenhuanraoPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("YanchenhuanraoPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private final boolean isUpgraded;
    private static final Color SMOKE_COLOR = new Color(0.4f, 0.4f, 0.5f, 0.7f);
    private float smokeTimer;
    private float waveTimer;
    private int triggerCount = 0;
    private DarkenScreenEffect darkenEffect = null;
    public YanchenhuanraoPower(AbstractCreature owner, int blockAmount, boolean isUpgraded) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = AbstractPower.PowerType.BUFF;
        this.amount = blockAmount;
        this.isUpgraded = isUpgraded;
        this.smokeTimer = 0.0F;
        this.waveTimer = 0.0F;
        String path128 = "wishdaleResources/images/powers/YanchenhuanraoPower84.png";
        String path48 = "wishdaleResources/images/powers/YanchenhuanraoPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() {
        this.description = isUpgraded ?
                powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[1] :
                powerStrings.DESCRIPTIONS[0] + this.amount + powerStrings.DESCRIPTIONS[2];
    }
    public void onExhaust(AbstractCard card) {
        flash();
        triggerCount++;
        addToBot(new GainBlockAction(owner, owner, amount));
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (!m.isDeadOrEscaped()) {
                int block = calculateBlockForTarget(m);
                addToBot(new GainBlockAction(m, owner, block));
            }
        }
        AbstractDungeon.effectList.add(new GraySmokeEffect(owner.hb.cX, owner.hb.cY));
        if (darkenEffect == null) {
            darkenEffect = new DarkenScreenEffect(triggerCount);
            AbstractDungeon.topLevelEffects.add(darkenEffect);
        } else {
            darkenEffect.updateIntensity(triggerCount);
        }
        AbstractDungeon.topLevelEffects.add(new DarkenScreenEffect(triggerCount));
        for (int i = 0; i < 5; ++i) {
            AbstractDungeon.effectsQueue.add(new GraySmokeEffect(
                    owner.hb.cX + MathUtils.random(-100.0F, 100.0F) * Settings.scale,
                    owner.hb.cY + MathUtils.random(-50.0F, 50.0F) * Settings.scale
            ));
        }
        AbstractDungeon.effectList.add(new GraySmokeEffect(owner.hb.cX, owner.hb.cY));

    }
    public void update(int slot) {
        super.update(slot);
        smokeTimer += Gdx.graphics.getDeltaTime();
        if (smokeTimer > 0.1F) {
            smokeTimer = 0.0F;
            for (int i = 0; i < 3; ++i) {
                AbstractDungeon.effectsQueue.add(new GraySmokeEffect(
                        owner.hb.cX + MathUtils.random(-50.0F, 50.0F) * Settings.scale,
                        owner.hb.cY + MathUtils.random(-20.0F, 20.0F) * Settings.scale
                ));
            }
        }
    }
    private int calculateBlockForTarget(AbstractMonster target) {
        if (isUpgraded && !target.isPlayer) {
            return (int) Math.ceil(amount * 0.5f);
        }
        return amount;
    }
    public void onRemove() {
        if (darkenEffect != null) {
            darkenEffect.endEffect();
            darkenEffect = null;
        }
    }
}