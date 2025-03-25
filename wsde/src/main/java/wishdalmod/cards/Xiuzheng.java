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
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.magicNumber = this.baseMagicNumber = WishdaleMod.damagedLastTurn ? 2 : 5;;
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
        this.addToTop(new HealAction(p, p, this.magicNumber));
    }

    public void triggerOnGlowCheck() {
        this.glowColor = WishdaleMod.damagedLastTurn ?
                AbstractCard.BLUE_BORDER_GLOW_COLOR :
                AbstractCard.GOLD_BORDER_GLOW_COLOR;
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(1);
            this.upgradeBaseCost(1);
            this.initializeDescription();
        }
    }
}
