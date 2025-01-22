package wishdalmod.cards;


import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.PlayRandomCardAction;
import wishdalmod.helpers.ModHelper;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Dailizhihui extends CustomCard {
    public static final String ID = ModHelper.makePath("Dailizhihui");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Dailizhihui");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Dailizhihui() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < AbstractDungeon.player.hand.group.size(); i++) {
            addToBot(new PlayRandomCardAction((AbstractDungeon.getCurrRoom()).monsters.getRandomMonster(null, true, AbstractDungeon.cardRandomRng), false, true));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeBaseCost(2);
        }
    }
    public AbstractCard makeCopy() {
        return new Dailizhihui();
    }
}