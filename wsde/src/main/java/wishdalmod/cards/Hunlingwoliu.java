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
        this.baseDamage = 9;
        this.baseBlock = 9;
        this.isMultiDamage = true;
    }
    public void applyPowers() {
        super.applyPowers();

        int zuzongCount = (AbstractDungeon.player instanceof EW) ? ((EW) AbstractDungeon.player).currentZuzongs.size() : 0;
        this.costForTurn = Math.max(0, this.cost - zuzongCount);
        this.isCostModifiedForTurn = this.costForTurn != this.cost;
        this.baseDamage = 9 * (zuzongCount + 1);
        this.baseBlock = 9 * (zuzongCount + 1);

        super.applyPowers();
    }
    public void onMoveToDiscard() {
        super.onMoveToDiscard();
        this.costForTurn = this.cost;
        this.isCostModifiedForTurn = false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new SFXAction("ATTACK_HEAVY"));
        this.addToBot(new VFXAction(p, new MindblastEffect(p.dialogX, p.dialogY, p.flipHorizontal), 0.1F));

        this.addToBot(new DamageAllEnemiesAction(
                p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE));
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
        return new Hunlingwoliu();
    }
}
