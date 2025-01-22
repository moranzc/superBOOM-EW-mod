package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Chongzu extends CustomCard {
    public static final String ID = ModHelper.makePath("Chongzu");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Chongzufang");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private boolean isUsed = false;

    public Chongzu() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.block = this.baseBlock = 5;
        this.damage = this.baseDamage = 9;
        this.magicNumber = this.baseMagicNumber = 1;
    }

    public void triggerOnGlowCheck() {
        if (this.isUsed) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
            upgradeDamage(1);
            if (this.type.equals(AbstractCard.CardType.ATTACK)) {
                upgradeMagicNumber(1);
            }
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.isUsed = true;
        if (this.type.equals(AbstractCard.CardType.SKILL)) {
            addToBot(new GainBlockAction(p, this.block));
            addToBot(new DrawCardAction(this.magicNumber));
        } else if (this.type.equals(AbstractCard.CardType.ATTACK)) {
            addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
            addToBot(new DrawCardAction(this.magicNumber));
        }
    }

    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        if (this.isUsed) {
            if (this.type.equals(AbstractCard.CardType.SKILL)) {
                this.type = AbstractCard.CardType.ATTACK;
                this.target = AbstractCard.CardTarget.ENEMY;
                this.rawDescription = CARD_STRINGS.UPGRADE_DESCRIPTION;
                initializeDescription();
                loadCardImage(ModHelper.getCardImagePath("Chongzugong"));
                if (this.upgraded) {
                    upgradeMagicNumber(1);
                }
            } else if (this.type.equals(AbstractCard.CardType.ATTACK)) {
                this.type = AbstractCard.CardType.SKILL;
                this.target = AbstractCard.CardTarget.SELF;
                this.rawDescription = CARD_STRINGS.DESCRIPTION;
                initializeDescription();
                loadCardImage(ModHelper.getCardImagePath("Chongzufang"));
                if (this.upgraded) {
                    upgradeMagicNumber(-1);
                }
            }
        }
        this.isUsed = false;
    }
    public AbstractCard makeCopy() {
        return new Chongzu();
    }
}