package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.cardManip.PurgeCardEffect;
import wishdalmod.actions.ShengjiAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;


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
        updateCardAttributes();
    }
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.tags.add(CardTags.HEALING);
            this.magicNumber = this.baseMagicNumber = 3;
        } else {
            this.tags.add(CardTags.HEALING);
            this.magicNumber = this.baseMagicNumber = 5;
        }
        this.initializeDescription();
    }

    public void triggerOnGlowCheck() {
        if (TypeSelectScreen.getType() == 0) {
            if (this.useTimes == 2) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        } else {
            if (this.useTimes == 5) {
                this.glowColor = AbstractCard.GREEN_BORDER_GLOW_COLOR.cpy();
            } else {
                this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
            }
        }
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        upgradeMagicNumber(-1);
        applyPowers();
        for (AbstractCard caa : AbstractDungeon.player.masterDeck.group) {
            if (caa.uuid == this.uuid) {
                caa.baseMagicNumber = caa.magicNumber = this.magicNumber;
                this.upgradedMagicNumber = true;
                if (caa.magicNumber == 0) {
                    caa.untip();
                    caa.unhover();
                    AbstractDungeon.topLevelEffects.add(new PurgeCardEffect(caa, Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F));
                    AbstractDungeon.player.masterDeck.removeCard(caa); break;
                }
                caa.applyPowers();
                break;
            }
        }
        if (this.baseMagicNumber == 0) {
            this.exhaust = true;
        }
        this.useTimes++;
        if (TypeSelectScreen.getType() == 0) {
            if (this.useTimes == 3) {
                this.exhaust = true;
            }
        } else {
            if (this.useTimes == 5) {
                this.exhaust = true;
            }
        }
        if (TypeSelectScreen.getType() == 0) {
            this.addToBot(new ShengjiAction(m, new DamageInfo(p, 12, this.damageTypeForTurn)));
        } else {
            this.addToBot(new ShengjiAction(m, new DamageInfo(p, 18, this.damageTypeForTurn)));
        }
    }
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(2);
        }
    }
    public AbstractCard makeCopy() {
        return new Zixuechengcai();
    }
}