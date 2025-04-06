package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.FireBurstParticleEffect;
import wishdalmod.helpers.ModHelper;

public class HunlingqiyuePinghengPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("HunlingqiyuePinghengPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private static final String TEXTURE_128 = "wishdaleResources/images/powers/Hunlingqiyue84.png";
    private static final String TEXTURE_32 = "wishdaleResources/images/powers/Hunlingqiyue32.png";

    private float fireTimer = 0.0f;
    private final Color flameColor = new Color(0.6f, 0.0f, 0.8f, 0.7f); // 紫色火焰

    public HunlingqiyuePinghengPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = -1;
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(TEXTURE_128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(TEXTURE_32), 0, 0, 32, 32);
        updateDescription();
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    public void onInitialApplication() {
        atStartOfTurn();
    }
    public void atStartOfTurn() {
        AbstractDungeon.player.hand.group.stream()
                .filter(c -> c.type == AbstractCard.CardType.ATTACK)
                .forEach(c -> {
                    c.setCostForTurn(0);
                    flash();
                });
    }
    public void onCardDraw(AbstractCard card) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            card.setCostForTurn(0);
            flash();
        }
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK) {
            action.exhaustCard = true;
            flash();
        }
    }
    public void render(SpriteBatch sb) {
        if (!AbstractDungeon.isScreenUp) {
            BorderFlashEffect borderEffect = new BorderFlashEffect(flameColor, true);
            borderEffect.render(sb);
        }
    }
    public void update(int slot) {
        super.update(slot);
        this.fireTimer += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
        if (this.fireTimer >= 0.1f) {
            this.fireTimer = 0.0f;
            for (int i = 0; i < 4; i++) {
                AbstractDungeon.effectsQueue.add(new FireBurstParticleEffect(
                        Settings.WIDTH * 0.1f + (float) Math.random() * Settings.WIDTH * 0.8f,
                        50.0f * Settings.scale
                ));
            }
        }
    }
    public void onRemove() {
        AbstractDungeon.effectsQueue.add(new BorderFlashEffect(Color.WHITE, false));
    }
}
