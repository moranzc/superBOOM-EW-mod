package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.SummonZuzongAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Sihunlingdeyuxi extends CustomCard {
    public static final String ID = ModHelper.makePath("Sihunlingdeyuxi");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = ModHelper.getCardImagePath("Sihunlingdeyuxi");
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Sihunlingdeyuxi() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH,
                TypeSelectScreen.getType() == 0 ? -2 : 2,
                TypeSelectScreen.getType() == 0 ? CARD_STRINGS.EXTENDED_DESCRIPTION[0] : CARD_STRINGS.DESCRIPTION,
                TYPE, COLOR, RARITY, TARGET);

        if (TypeSelectScreen.getType() == 0) {
            this.isInnate = true;  // 固有
            this.selfRetain = true; // 保留
            this.exhaust = true;
        }

        this.baseMagicNumber = this.magicNumber = 3;

        updateCardAttributes();
    }

    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(35, 0, 10));
        } else {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(17, 0, this.magicNumber));
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0 && this.timesUpgraded < 2) {
            this.cantUseMessage = "还未解锁";
            return false;
        }
        return super.canUse(p, m);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded || this.timesUpgraded < 2) {
            this.timesUpgraded++;
            this.upgraded = true;
            this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();

            if (TypeSelectScreen.getType() == 0) {
                if (this.timesUpgraded == 2) {
                    this.upgradeBaseCost(0); // 第二次升级 0费
                }
            } else {
                if (this.timesUpgraded == 1) {
                    this.upgradeMagicNumber(3);
                } else if (this.timesUpgraded == 2) {
                    this.upgradeMagicNumber(1);
                }
            }

            this.initializeDescription();
        }
    }

    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 2;
    }

    @Override
    public AbstractCard makeCopy() {
        return new Sihunlingdeyuxi();
    }
}
