package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Tudou extends CustomCard {
    public static final String ID = ModHelper.makePath("Tudou");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Tudou");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Tudou() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION,TYPE,COLOR,RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 1;
        this.exhaust = true;
        this.cardsToPreview = new Yineisidetou();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.upgraded || p.hasPower("MasterRealityPower")) {
            Yineisidetou Yineisidetou = new Yineisidetou();
            Yineisidetou.upgrade();
            addToBot(new MakeTempCardInDrawPileAction(Yineisidetou, this.magicNumber, true, false, false));
        } else {
            addToBot(new MakeTempCardInDrawPileAction(new Yineisidetou(), this.magicNumber, true, false, false));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Tudou();
    }
}
