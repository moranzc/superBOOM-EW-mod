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
import wishdalmod.characters.EW.PlayerColorEnum;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

public class Strike extends CustomCard {
    public static final String ID = ModHelper.makePath("Strike");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = ModHelper.getCardImagePath("Strike");
    private static final AbstractCard.CardType TYPE = CardType.ATTACK;
    private static final AbstractCard.CardColor COLOR = PlayerColorEnum.WISHDALE_RED;
    private static final AbstractCard.CardRarity RARITY = CardRarity.BASIC;
    private static final AbstractCard.CardTarget TARGET = CardTarget.ENEMY;

    public Strike() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 0 : 1
                ,
                TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0],
                TYPE, COLOR, RARITY, TARGET);
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
        this.baseDamage = TypeSelectScreen.getType() == 0 ? 6 : 9;
        this.damage = this.baseDamage;
        this.timesUpgraded = 0;
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL))
        );
    }
    public void upgrade() {
        if (TypeSelectScreen.getType() == 0) {
            this.timesUpgraded++;
            this.upgradeDamage(2);
            this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            this.initializeTitle();
        } else {
            if (!this.upgraded) {
                this.upgradeName();
                this.upgradeDamage(1);
                this.upgraded = true;
            }
        }
        this.initializeDescription();
    }
    public boolean canUpgrade() {
        return TypeSelectScreen.getType() == 0 || !this.upgraded;
    }
    public AbstractCard makeCopy() {
        Strike copy = new Strike();
        copy.timesUpgraded = this.timesUpgraded;
        for (int i = 0; i < this.timesUpgraded; i++) {
            copy.upgradeDamage(2);
        }
        if (this.timesUpgraded > 0 && TypeSelectScreen.getType() == 0) {
            copy.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            copy.initializeTitle();
        }
        return copy;
    }
}
