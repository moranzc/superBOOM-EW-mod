package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.RepairPower;
import wishdalmod.helpers.ModHelper;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Zhanhouxiuzheng extends CustomCard {
    public static final String ID = ModHelper.makePath("Zhanhouxiuzheng");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = "wishdaleResources/images/cards/Zhanhouxiuzheng.png";
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Zhanhouxiuzheng() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 7;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(CardTags.HEALING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new RepairPower(p, this.magicNumber), this.magicNumber));
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(3);
        }
    }
    public AbstractCard makeCopy() {
        return new Zhanhouxiuzheng();
    }

}