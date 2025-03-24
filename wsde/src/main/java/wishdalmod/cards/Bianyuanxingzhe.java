package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.BufferPower;
import wishdalmod.helpers.texiao.PersistentFlameEffect;
import wishdalmod.powers.BianyuanxingzhePower;
import wishdalmod.helpers.ModHelper;



import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Bianyuanxingzhe extends CustomCard {
    public static final String ID = ModHelper.makePath("Bianyuanxingzhe");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Bianyuanxingzhe");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;
    public void tookDamage() {
        this.updateCost(-1);
    }

    public Bianyuanxingzhe() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 5;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
        if (p.currentHealth > 1) {
            p.currentHealth = 1;
            p.healthBarUpdatedEvent();
        }
        this.addToBot(new ApplyPowerAction(p, p, new BianyuanxingzhePower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
        this.addToBot(new ApplyPowerAction(p, p, new BufferPower(p, 1), 1));
        this.addToBot(new GainBlockAction(p, p, 10));
        AbstractDungeon.effectsQueue.add(new PersistentFlameEffect());
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {return false;}
        else {
            if (p.currentHealth > 10 && p.currentHealth > p.maxHealth * 0.1f){
                canUse = false;
                this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
            }
        }
        return canUse;
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (this.cost < 4) {
                this.upgradeBaseCost(this.cost - 1);
                if (this.cost < 0) {
                    this.cost = 0;
                }
            } else {
                this.upgradeBaseCost(2);
            }

            this.upgradeMagicNumber(2);
        }

    }
    public AbstractCard makeCopy() {
        AbstractCard tmp = new Bianyuanxingzhe();
        if (AbstractDungeon.player != null) {
            ((AbstractCard)tmp).updateCost(-AbstractDungeon.player.damagedThisCombat);
        }
        return tmp;
    }
}