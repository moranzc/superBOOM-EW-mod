package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.YanchenhuanraoPower;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Yanchenhuanrao extends CustomCard {
    public static final String ID = ModHelper.makePath("Yanchenhuanrao");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "wishdaleResources/images/cards/Yanchenhuanrao.png";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    public Yanchenhuanrao() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = this.magicNumber = 7;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p,
                new YanchenhuanraoPower(p, this.baseMagicNumber, this.upgraded), this.baseMagicNumber));
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Yanchenhuanrao();
    }
}