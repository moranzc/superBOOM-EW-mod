package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.ShockWaveEffect;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.FlashbangEffect;
import wishdalmod.screen.TypeSelectScreen;

import java.util.Iterator;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Shanguangdan extends CustomCard {
    public static final String ID = ModHelper.makePath("Shanguangdan");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Shanguangdan");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    public Shanguangdan() {
        super(ID, NAME, IMG_PATH, 1, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.magicNumber = this.baseMagicNumber = 9;
            this.exhaust = true;
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.magicNumber = this.baseMagicNumber = 4;
            this.exhaust = true;
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            AbstractDungeon.effectList.add(new FlashbangEffect());
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster mo;
            while(var3.hasNext()) {
                mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
            var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            while(var3.hasNext()) {
                mo = (AbstractMonster)var3.next();
                if (!mo.hasPower("Artifact")) {
                    this.addToBot(new ApplyPowerAction(mo, p, new GainStrengthPower(mo, this.magicNumber), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
                }
            }
        } else {
            AbstractDungeon.effectList.add(new FlashbangEffect());
            Iterator var3 = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster mo;
            while(var3.hasNext()) {
                mo = (AbstractMonster)var3.next();
                this.addToBot(new ApplyPowerAction(mo, p, new StrengthPower(mo, -this.magicNumber), -this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));
            }
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeMagicNumber(3);
            } else {
                this.upgradeMagicNumber(2);
            }
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        return new Shanguangdan();
    }
}