package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.MindblastEffect;
import wishdalmod.characters.EW;
import wishdalmod.helpers.ModHelper;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Hunlingwoliu extends CustomCard {
    public static final String ID = ModHelper.makePath("Hunlingwoliu");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Hunlingwoliu");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;

    public Hunlingwoliu() {
        super(ID, NAME, IMG_PATH, 3, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 9;
        this.block = this.baseBlock = 9;
        this.isMultiDamage = true;
    }
    public void applyPowers() {
        super.applyPowers();
        int zuzongCount = 0;
        if (AbstractDungeon.player instanceof EW) {
            zuzongCount = ((EW) AbstractDungeon.player).currentZuzongs.size();
        }
        this.costForTurn = Math.max(0, this.cost - zuzongCount);
        this.isCostModifiedForTurn = this.costForTurn != this.cost;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        int zuzongCount = 0;
        if (p instanceof EW) {
            zuzongCount = ((EW) p).currentZuzongs.size();
        }
        int damageMultiplier = zuzongCount > 0 ? 2 : 1;
        this.damage = 9  * damageMultiplier;
        this.block = 9 * damageMultiplier;

        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        if (Settings.FAST_MODE) {
            this.addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        } else {
            this.addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));
        }

        this.addToBot(new DamageAllEnemiesAction
                (p, DamageInfo.createDamageMatrix(this.damage, true),
                        DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
        this.addToBot(new GainBlockAction(p, p, this.block));
    }
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(3);
            this.upgradeBlock(3);
            this.initializeDescription();
        }
    }
    public AbstractCard makeCopy() {
        Hunlingwoliu tmp = new Hunlingwoliu();
        if (AbstractDungeon.player != null) {
            tmp.cost = Math.max(0, this.cost - ((EW)AbstractDungeon.player).currentZuzongs.size());
            tmp.costForTurn = tmp.cost;
        }
        return tmp;
    }
}
