package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.DamageInfo.DamageType;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.characters.EW.PlayerColorEnum;
import wishdalmod.helpers.ModHelper;

public class Strike extends CustomCard {
    public static final String ID = ModHelper.makePath("Strike");
    private static final CardStrings CARD_STRINGS;
    private static final String NAME;
    private static final String IMG_PATH = "wishdaleResources/images/cards/Strike.png";
    private static final int COST = 1;
    private static final String DESCRIPTION;
    private static final AbstractCard.CardType TYPE;
    private static final AbstractCard.CardColor COLOR;
    private static final AbstractCard.CardRarity RARITY;
    private static final AbstractCard.CardTarget TARGET;
    static {
        CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = CARD_STRINGS.NAME;
        DESCRIPTION = CARD_STRINGS.DESCRIPTION;
        TYPE = CardType.ATTACK;
        COLOR = PlayerColorEnum.WISHDALE_RED;
        RARITY = CardRarity.BASIC;
        TARGET = CardTarget.ENEMY;
    }
    public Strike() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 9;
        this.tags.add(CardTags.STARTER_STRIKE);
        this.tags.add(CardTags.STRIKE);
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(1);
            this.initializeDescription();
        }

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageType.NORMAL)));
    }


}
