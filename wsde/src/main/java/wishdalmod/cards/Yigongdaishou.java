package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import java.util.Iterator;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Yigongdaishou extends CustomCard {
    public static final String ID = ModHelper.makePath("Yigongdaishou");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Yigongdaishou");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Yigongdaishou() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION,TYPE,COLOR,RARITY, TARGET);
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.baseDamage = 17;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
        } else {
            this.baseDamage = 25;
            this.block = this.baseBlock = 7;
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        if (TypeSelectScreen.getType() == 0) {
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        } else {
            this.addToBot(new GainBlockAction(p, p, this.block));
            this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        }
    }
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) {
            return false;
        } else {
            Iterator<AbstractCard> var4 = p.hand.group.iterator();
            while(var4.hasNext()) {
                AbstractCard c = var4.next();
                if (c.type != CardType.ATTACK) {
                    canUse = false;
                    this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
                }
            }
            return canUse;
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeDamage(5);
            } else {
                this.upgradeDamage(10);
            }
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Yigongdaishou();
    }
}
