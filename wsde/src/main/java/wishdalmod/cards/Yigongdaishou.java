package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ClashEffect;
import wishdalmod.helpers.ModHelper;

import java.util.Iterator;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Yigongdaishou extends CustomCard {
    public static final String ID = ModHelper.makePath("Yigongdaishou");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Yigongdaishou");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Yigongdaishou() {
        super(ID, NAME, IMG_PATH, 0, DESCRIPTION,TYPE,COLOR,RARITY, TARGET);
        this.baseDamage = 20;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new ClashEffect(m.hb.cX, m.hb.cY), 0.1F));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
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
            this.upgradeDamage(10);
        }

    }
    public AbstractCard makeCopy() {
        return new Yigongdaishou();
    }
}
