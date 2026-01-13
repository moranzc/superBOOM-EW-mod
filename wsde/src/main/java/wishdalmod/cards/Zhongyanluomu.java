package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ExplosionSmallEffect;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.Yishujiushibaozha;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Zhongyanluomu extends CustomCard {
    public static final String ID = ModHelper.makePath("Zhongyanluomu");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Zhongyanluomu");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.SKILL;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Zhongyanluomu() {
        super(ID, NAME, IMG_PATH,
                TypeSelectScreen.getType() == 0 ? 0 : 4,
                DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        if (TypeSelectScreen.getType() == 0) {
            this.magicNumber = this.baseMagicNumber = 40; // 模式0固定初始需求
        } else {
            this.magicNumber = this.baseMagicNumber = 2; // 模式1延迟回合数
        }
        this.exhaust = true;
        updateCardAttributes();
    }

    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        this.initializeDescription();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            for (AbstractCard c : p.hand.group.toArray(new AbstractCard[0])) {
                if (c != this) {
                    addToBot(new ExhaustSpecificCardAction(c, p.hand));
                }
            }
            for (AbstractCard c : p.drawPile.group.toArray(new AbstractCard[0])) {
                addToBot(new ExhaustSpecificCardAction(c, p.drawPile));
            }
            for (AbstractCard c : p.discardPile.group.toArray(new AbstractCard[0])) {
                addToBot(new ExhaustSpecificCardAction(c, p.discardPile));
            }
            addToBot(new ApplyPowerAction(p, p, new Yishujiushibaozha(p,1), 0));

        } else {
            addToBot(new ApplyPowerAction(p, p, new Yishujiushibaozha(p, 2), 0));
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        if (TypeSelectScreen.getType() == 0) {
            if (AbstractDungeon.player.exhaustPile.size() >= this.magicNumber) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            if (AbstractDungeon.player.drawPile.isEmpty()) {
                this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
            }
        }
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        boolean canUse = super.canUse(p, m);
        if (!canUse) return false;

        if (TypeSelectScreen.getType() == 0) {
            if (p.exhaustPile.size() < this.magicNumber) {
                this.cantUseMessage = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
                return false;
            }
        } else {
            if (p.drawPile.size() > 0 || p.discardPile.size() > 0) {
                this.cantUseMessage = CARD_STRINGS.UPGRADE_DESCRIPTION;
                return false;
            }
        }
        return true;
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.magicNumber = this.baseMagicNumber = 30; // 升级后需求改为30
                this.upgradeBaseCost(0);
            } else {
                upgradeMagicNumber(-1);
                this.rawDescription = CARD_STRINGS.DESCRIPTION;
            }
            this.initializeDescription();
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Zhongyanluomu();
    }
}
