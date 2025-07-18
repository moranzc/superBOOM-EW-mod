package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.SummonZuzongAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.BaolielimingPower;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Baolieliming extends CustomCard {
    public static final String ID = ModHelper.makePath("Baolieliming");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Baolieliming");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Baolieliming() {
        super(ID, NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 5 : 4, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Sihunling();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(15, 0, 0));
        AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(15, 0, 0));
        this.addToBot(new ApplyPowerAction(p, p, new BaolielimingPower(p, this.magicNumber), this.magicNumber));
        CardCrawlGame.sound.play("BOOM");
    }
    public void changeType(boolean hard) {
        if (!hard) {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        } else {

            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        }
        initializeDescription();
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeBaseCost(3);
            } else {
                this.upgradeBaseCost(4);
            }
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Baolieliming();
    }
}
