package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;
import wishdalmod.modcore.WishdaleMod;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Xiuzheng extends CustomCard {
    public static final String ID = ModHelper.makePath("Xiuzheng");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Xiuzheng");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Xiuzheng() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.magicNumber = this.baseMagicNumber = 2;
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

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            this.initializeDescription();
            this.magicNumber = this.baseMagicNumber = WishdaleMod.damagedLastTurn ? 2 : 5;;
            this.upgradeBaseCost(WishdaleMod.damagedLastTurn ? 2 : 0);
            AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
            this.addToTop(new HealAction(p, p, this.magicNumber));
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.initializeDescription();
            this.magicNumber = this.baseMagicNumber = WishdaleMod.damagedLastTurn ? 2 : 5;;
            this.upgradeBaseCost(WishdaleMod.damagedLastTurn ? 2 : 0);
            AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
            this.addToTop(new HealAction(p, p, this.magicNumber));
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }

    }

    public void triggerOnGlowCheck() {
        this.glowColor = WishdaleMod.damagedLastTurn ?
                AbstractCard.BLUE_BORDER_GLOW_COLOR :
                AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(2);
            this.initializeDescription();
        }
    }
}
