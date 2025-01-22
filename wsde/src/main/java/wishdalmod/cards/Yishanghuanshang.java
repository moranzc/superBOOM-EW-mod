package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;


import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Yishanghuanshang extends CustomCard {
    public static final String ID = ModHelper.makePath("Yishanghuanshang");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Yishanghuanshang");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int useTimes = 0;

    public Yishanghuanshang() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 0;
    }
    public void triggerOnGlowCheck() {
        if (this.useTimes == 2) {
            this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.useTimes++;
        if (this.useTimes == 3) {
            this.exhaust = true;
        }
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
    }


    public void applyPowers() {
        AbstractPlayer p = AbstractDungeon.player;
        this.baseDamage = p.maxHealth - p.currentHealth;
        super.applyPowers();
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        this.rawDescription += CARD_STRINGS.UPGRADE_DESCRIPTION;
        initializeDescription();
    }

    public void onMoveToDiscard() {
        this.rawDescription = CARD_STRINGS.DESCRIPTION;
        initializeDescription();
    }


    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
    public AbstractCard makeCopy() {
        return new Yishanghuanshang();
    }
}
