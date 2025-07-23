package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.SpiritArmorPower;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Fulingkaijia extends CustomCard {
    public static final String ID = ModHelper.makePath("Fulingkaijia");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Fulingkaijia");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Fulingkaijia() {
        super(ID, NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 3 : 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 1;
        this.magicNumber = this.baseMagicNumber;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new SpiritArmorPower(p, this.magicNumber), this.magicNumber));
    }
    public void upgrade() {
        if (!this.upgraded) {
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeName();
                this.upgradeBaseCost(2);
                this.isInnate = true;
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            } else {
                this.upgradeName();
                this.upgradeBaseCost(1);
                this.isInnate = true;
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
                this.initializeDescription();
            }
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Fulingkaijia();
    }
}
