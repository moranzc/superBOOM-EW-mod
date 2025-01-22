package wishdalmod.cards;
//3场战斗后从牌组移除 75块钱 不足扣遗物
//3费打1，斩杀升级所有牌
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.ShengjiAction;
import wishdalmod.helpers.ModHelper;


import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Zixuechengcai extends CustomCard {
    public static final String ID = ModHelper.makePath("Zixuechengcai");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Zixuechengcai");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private int useTimes = 0;

    public Zixuechengcai() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.isMultiDamage = true;
        this.baseDamage = 12;
        this.tags.add(CardTags.HEALING);
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
        this.addToBot(new ShengjiAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
            this.upgradeDamage(3);
        }
    }
    public AbstractCard makeCopy() {
        return new Zixuechengcai();
    }
}