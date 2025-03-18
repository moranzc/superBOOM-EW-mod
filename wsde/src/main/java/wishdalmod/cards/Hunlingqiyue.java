package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.BorderLongFlashEffect;
import com.megacrit.cardcrawl.vfx.combat.FlameBarrierEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.PersistentFlameEffect;
import wishdalmod.powers.HunlingqiyuePower;

import java.util.Iterator;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;


public class Hunlingqiyue extends CustomCard {
    public static final String ID = ModHelper.makePath("Hunlingqiyue");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Hunlingqiyue");
    private static final int COST = 4;
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Hunlingqiyue() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isEthereal = true;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        //试一下超帅的特效
        // 暗影汇聚
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.DARK_GRAY, p.hb.cX + 50f, p.hb.cY), 0.1F));
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.DARK_GRAY, p.hb.cX - 50f, p.hb.cY), 0.1F));
        // 火焰粒子效果
        this.addToBot(new SFXAction("ATTACK_FLAME_BARRIER"));
        this.addToBot(new VFXAction(p, new FlameBarrierEffect(p.hb.cX, p.hb.cY), 0.2F));
        // 能量
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.SCARLET), 0.05F));
        this.addToBot(new SFXAction("GHOST_ORB_IGNITE_1"));
        // 灵魂漩涡
        this.addToBot(new VFXAction(p, new VerticalAuraEffect(Color.CYAN, p.hb.cX, p.hb.cY), 0.0F));

        // 震动
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.GOLDENROD), 0.0F));
        this.addToBot(new VFXAction(new BorderLongFlashEffect(Color.GOLDENROD), 0.0F));
        this.addToBot(new SFXAction("APPEAR"));
        // 闪光
        this.addToBot(new VFXAction(
                new BorderLongFlashEffect(Color.MAGENTA), 0.2F));
        this.addToBot(new VFXAction(
                new VerticalAuraEffect(Color.SKY, p.hb.cX, p.hb.cY),
                0.3F));
        boolean powerExists = false;
        Iterator var4 = p.powers.iterator();
        while (var4.hasNext()) {
            AbstractPower pow = (AbstractPower)var4.next();
            if (pow.ID.equals ("wishdalemod:Hunlingqiyue")) {
                powerExists = true;
                break;
            }
        }
        if (!powerExists) {
            addToBot(new ApplyPowerAction(p, p, new HunlingqiyuePower(p)));
        }
        if (!p.hasPower(HunlingqiyuePower.POWER_ID)) {
            this.addToBot(new ApplyPowerAction(p, p, new HunlingqiyuePower(p)));
        }
        int hpLoss = Math.max(0, (int)(p.currentHealth * 0.1f));
        this.addToBot(new LoseHPAction(p, p, hpLoss));
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(3);
            initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Hunlingqiyue();
    }
}