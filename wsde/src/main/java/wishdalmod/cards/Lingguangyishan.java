package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.LinguangyishanAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Lingguangyishan extends CustomCard {
    public static final String ID = ModHelper.makePath("Lingguangyishan");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Lingguangyishan");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Lingguangyishan() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION,TYPE,COLOR,RARITY, TARGET);
        updateCardAttributes();
        this.exhaust = true;
        this.isEthereal = true;
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LinguangyishanAction(p));
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
                this.isEthereal = false;
            } else {
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
                this.isEthereal = false;
            }
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Lingguangyishan();
    }
}
