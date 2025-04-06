package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.GoushiAction;
import wishdalmod.actions.GoushiPinghengAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Goushi extends CustomCard {
    public static final String ID = ModHelper.makePath("Goushi");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Goushi");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL;

    public Goushi() {
        super(ID, NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 4 : 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            AbstractDungeon.actionManager.addToBottom(new GoushiPinghengAction(p, upgraded));
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            AbstractDungeon.actionManager.addToBottom(new GoushiAction(p, upgraded));
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeBaseCost(3);
                this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
            } else {
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

}
