package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ConservePower;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import wishdalmod.actions.MositimaoneAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.MositimaTimeEffect;
import wishdalmod.powers.MositimaPower;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Mositima extends CustomCard {
    public static final String ID = ModHelper.makePath("Mositima");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Mositima");
    private static final String DESCRIPTION = TypeSelectScreen.getType() == 0 ? CARD_STRINGS.DESCRIPTION : CARD_STRINGS.EXTENDED_DESCRIPTION[0];
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.NONE;

    public Mositima() {
        super(ID, NAME, IMG_PATH,3, DESCRIPTION, CardType.SKILL, COLOR, RARITY, TARGET);
        this.exhaust = true;
        updateCardAttributes();
    }

    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 1) {
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[1];
            this.initializeDescription();
        }
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            this.addToBot(new VFXAction(new WhirlwindEffect(new Color(0F, 0F, 255F, 1.0F), true)));
            this.addToBot(new MositimaoneAction(p));
            this.addToBot(new ApplyPowerAction(p, p, new ConservePower(p, 1), 1));
        } else {
            // 添加模式2的特效
            this.addToBot(new VFXAction(new MositimaTimeEffect(p), 1.5f));
                this.addToBot(new ApplyPowerAction(p, p, new MositimaPower(p)));
        }
    }

    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            if (TypeSelectScreen.getType() == 0) {
                this.upgradeBaseCost(2);
            } else {
                this.upgradeBaseCost(2);
            }
            this.initializeDescription();
        }
    }

    public AbstractCard makeCopy() {
        return new Mositima();
    }
}