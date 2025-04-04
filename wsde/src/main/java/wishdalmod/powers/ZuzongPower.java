package wishdalmod.powers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.LoseBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.actions.ZuzongTakeDamageAction;
import wishdalmod.characters.EW;
import wishdalmod.characters.Zuzong;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.MicaiTexiao;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ZuzongPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("ZuzongPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public ZuzongPower(AbstractCreature owner, int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;
        this.priority = 999;
        this.region128 = new TextureAtlas.AtlasRegion(
                ImageMaster.loadImage("wishdaleResources/images/powers/ZuzongPower84.png"), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(
                ImageMaster.loadImage("wishdaleResources/images/powers/ZuzongPower32.png"), 0, 0, 32, 32);
        updateDescription();
    }

    int currentIndex=0;
    public void update(int slot) {
        super.update(slot);
        currentIndex++;
        if (currentIndex >= MicaiTexiao.micaiList.size())
            currentIndex = 0;
    }
    public void renderIcons(SpriteBatch sb, float x, float y, Color c) {
        super.renderIcons(sb,x,y,c);
        TextureAtlas.AtlasRegion img = MicaiTexiao.getByIndex(currentIndex);
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
    public int onAttacked(DamageInfo info, int damageAmount) {
        if (!(owner instanceof EW)) return damageAmount;
        EW ew = (EW) owner;

        int blocked = Math.min(ew.currentBlock, damageAmount);
        if (blocked > 0) {
            addToTop(new LoseBlockAction(ew, ew, blocked));
            damageAmount -= blocked;
            if (damageAmount <= 0) return 0;
        }
        List<Zuzong> aliveZuzongs = ew.currentZuzongs.stream()
                .filter(z -> !z.isDeadOrEscaped())
                .collect(Collectors.toList());

        int remaining = damageAmount;
        for (int i = aliveZuzongs.size()-1; i >= 0; i--) {
            Zuzong z = aliveZuzongs.get(i);
            if (remaining <= 0) break;

            int actualDmg = Math.min(remaining, z.currentHealth);
            addToTop(new ZuzongTakeDamageAction(z, info, actualDmg));
            remaining -= actualDmg;
        }

        return remaining;
    }

    @Override
    public void stackPower(int stackAmount) {
        this.amount = Math.max(0, this.amount + stackAmount);
        if (this.amount <= 0) {
            AbstractDungeon.actionManager.addToTop(
                    new RemoveSpecificPowerAction(owner, owner, POWER_ID));
        }
        updateDescription();
    }
    private List<Zuzong> findAliveZuzongs() {
        List<Zuzong> alive = new ArrayList<>();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
            if (m instanceof Zuzong && !m.isDeadOrEscaped() && !m.isDying) {
                alive.add((Zuzong) m);
            }
        }
        return alive;
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
    public void onDeath() {
        if (owner instanceof Zuzong) {
            AbstractDungeon.player.powers.remove(this);
        }
    }
}