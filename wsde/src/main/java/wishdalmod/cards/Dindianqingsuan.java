package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.CanyingAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.Canying;
import wishdalmod.screen.TypeSelectScreen;


import java.util.Random;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Dindianqingsuan extends CustomCard {
    public static final String ID = ModHelper.makePath("Dindianqingsuan");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Dindianqingsuan");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final Random random = new Random();
    public Dindianqingsuan() {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.baseDamage = 7;
            this.magicNumber = this.baseMagicNumber = 3;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.baseDamage = 6;
            this.magicNumber = this.baseMagicNumber = 4;
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (random.nextFloat() < 0.5f) {
            CardCrawlGame.sound.play("作战中4.wav");
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToBottom(new CanyingAction(m, p, this.magicNumber));
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeMagicNumber(2);
            } else {
                this.upgradeMagicNumber(3);
            }
            this.initializeDescription();
        }
    }
}
