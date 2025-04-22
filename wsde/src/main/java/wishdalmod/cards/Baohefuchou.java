package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Baohefuchou extends CustomCard {
    public static final String ID = ModHelper.makePath("Baohefuchou");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = ModHelper.getCardImagePath("Baohefuchou");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Baohefuchou() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, -2, CARD_STRINGS.DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseDamage = 8;
        this.damage = this.baseDamage;
        this.isMultiDamage = true;
        this.isInnate = true;  // 固有
        this.selfRetain = true; // 保留
    }


    @Override
    public void triggerWhenDrawn() {
        if (this.timesUpgraded >= 1) {
            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 1));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (this.cost == -2) {
            this.cantUseMessage = "还未解锁，升级解锁";
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
        AbstractCard overload = new BaohefuchouGuozai();
        if (this.timesUpgraded >= 8) {
            overload.upgrade();
        }
        addToBot(new MakeTempCardInHandAction(overload, 1));
        this.setCostForTurn(0);
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded < 10) {
            this.timesUpgraded++;
            this.upgraded = true;
            this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            if (timesUpgraded <= 6) {
                this.upgradeDamage(1);
            } else if (timesUpgraded == 7) {
                this.upgradeDamage(3);
            } else if (timesUpgraded == 8) {
                this.upgradeDamage(3);
            } else if (timesUpgraded == 9) {
                this.upgradeDamage(3);
            }
            // 设定升级对应的费用
            switch (this.timesUpgraded) {
                case 1:
                    setCustomCost(35);
                    break;
                case 2:
                    setCustomCost(33);
                    break;
                case 3:
                    setCustomCost(31);
                    break;
                case 4:
                    setCustomCost(29);
                    break;
                case 5:
                    setCustomCost(28);
                    break;
                case 6:
                    setCustomCost(27);
                    break;
                case 7:
                    setCustomCost(26);
                    break;
                case 8:
                    setCustomCost(25);
                    break;
                case 9:
                    setCustomCost(24);
                    break;
                case 10:
                    setCustomCost(20);
                    break;
            }

            this.initializeDescription();
        }
    }

    private void setCustomCost(int newCost) {
        this.cost = newCost;
        this.costForTurn = newCost;
        this.isCostModified = true;
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 11;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Baohefuchou();
    }
}
