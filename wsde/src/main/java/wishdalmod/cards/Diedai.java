package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import java.util.Iterator;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Diedai extends CustomCard {
    public static final String ID = ModHelper.makePath("Diedai");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Diedai");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    public Diedai() {
        super(ID, NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 3 : 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.damage = this.baseDamage = 5;
            this.magicNumber = this.baseMagicNumber = 4;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.damage = this.baseDamage = 8;
            this.magicNumber = this.baseMagicNumber = 5;
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        this.addToBot(new ReduceCostAction(this.uuid, this.magicNumber - 4));
        addToBot(new AbstractGameAction() {
            private AbstractCard card = Diedai.this;
            public void update() {
                AbstractCard var10000 = this.card;
                var10000.baseDamage += Diedai.this.baseMagicNumber;
                this.card.applyPowers();
                Iterator<AbstractCard> var1 = AbstractDungeon.player.discardPile.group.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (c instanceof Diedai) {
                        c.baseDamage += Diedai.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }
                var1 = AbstractDungeon.player.drawPile.group.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (c instanceof Diedai) {
                        c.baseDamage += Diedai.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }
                var1 = AbstractDungeon.player.hand.group.iterator();
                while (var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    if (c instanceof Diedai) {
                        c.baseDamage += Diedai.this.baseMagicNumber;
                        c.applyPowers();
                    }
                }
                this.isDone = true;
            }
        });
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeDamage(3);
            } else {
                this.upgradeDamage(4);
            }
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Diedai();
    }
}