package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BufferPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Hunlingpingzhang extends CustomCard {
    public static final String ID = ModHelper.makePath("Hunlingpingzhang");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "wishdaleResources/images/cards/Hunlingpingzhang.png";
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Hunlingpingzhang() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber;
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.baseMagicNumber = 1;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.baseMagicNumber = 2;
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new BufferPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Hunlingpingzhang();
    }

}