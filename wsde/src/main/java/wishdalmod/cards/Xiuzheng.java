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

    private boolean lastDamagedState;
    private int conditionCost;
    public Xiuzheng() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
        this.baseMagicNumber = 5;
        this.magicNumber = this.baseMagicNumber;
        this.lastDamagedState = WishdaleMod.damagedLastTurn;
        this.conditionCost = WishdaleMod.damagedLastTurn ? 2 : 0;

        updateCardState();
    }
    public int getCost() {
        return conditionCost;
    }
    private void updateCardState() {
        this.conditionCost = WishdaleMod.damagedLastTurn ? 2 : 0;
        this.baseMagicNumber = WishdaleMod.damagedLastTurn ? (upgraded ? 4 : 2) : (upgraded ? 7 : 5);
        this.magicNumber = this.baseMagicNumber;
        this.upgradeBaseCost(WishdaleMod.damagedLastTurn ? 2 : 0);
        if (TypeSelectScreen.getType() == 0) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.glowColor = WishdaleMod.damagedLastTurn ?
                AbstractCard.BLUE_BORDER_GLOW_COLOR :
                AbstractCard.GOLD_BORDER_GLOW_COLOR;

        initializeDescription();
    }

    public void update() {
        super.update();
        if (this.lastDamagedState != WishdaleMod.damagedLastTurn) {
            this.lastDamagedState = WishdaleMod.damagedLastTurn;
            updateCardState();
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.increaseMaxHp(this.magicNumber, true);
        addToTop(new HealAction(p, p, this.magicNumber));
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
    public void displayUpgrades() {
        super.displayUpgrades();
        updateCardState();
    }
    public void applyPowers() {
        super.applyPowers();
        updateCardState();
    }
}