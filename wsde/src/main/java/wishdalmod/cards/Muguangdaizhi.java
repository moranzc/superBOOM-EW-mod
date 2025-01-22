package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.RandomizeDrawCardCostAction;
import wishdalmod.helpers.ModHelper;

import java.util.ArrayList;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Muguangdaizhi extends CustomCard {
    public static final String ID = ModHelper.makePath("Muguangdaizhi");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Muguangdaizhi");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Muguangdaizhi() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION,TYPE,COLOR,RARITY, TARGET);
        this.magicNumber = this.baseMagicNumber = 3;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DrawCardAction(this.magicNumber, new RandomizeDrawCardCostAction(), true));
    }


    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            upgradeName();
            upgradeMagicNumber(1);
        }

    }
    public AbstractCard makeCopy() {
        return new Muguangdaizhi();
    }
}
