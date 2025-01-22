package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;
import wishdalmod.helpers.ModHelper;
import java.util.ArrayList;
import java.util.Random;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Zhupaojiwushuzengfudanyuan extends CustomCard {
    public static final String ID = ModHelper.makePath("Zhupaojiwushuzengfudanyuan");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Zhupaojiwushuzengfudanyuan");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static ArrayList<Integer> RNGS = new ArrayList();
    private static final Random r = new Random();
    
    public Zhupaojiwushuzengfudanyuan() { this(0); }
    
    public Zhupaojiwushuzengfudanyuan(int upgrades) {
        super(ID, NAME, IMG_PATH, 2, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.damage = this.baseDamage = 5;
        this.magicNumber = this.baseMagicNumber = 2;
        this.timesUpgraded = upgrades;
        this.isMultiDamage = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
            for (AbstractMonster m3 : AbstractDungeon.getMonsters().monsters) {
                if (!m3.isDeadOrEscaped()) {
                        addToBot(new VFXAction(new SearingBlowEffect(m3.hb.cX, m3.hb.cY, this.timesUpgraded), 0.2F));
                }

            }
            for (int i = 0; i < this.magicNumber; i++) {
                this.addToBot(new DamageAllEnemiesAction(p, this.multiDamage, this.damageTypeForTurn, AbstractGameAction.AttackEffect.FIRE));
            }
    }
    public void upgrade() {
        if (AbstractDungeon.player != null) {
            int rng;
            if (this.cost == 0) {
                rng = AbstractDungeon.miscRng.random(1);
            } else {
                rng = AbstractDungeon.miscRng.random(2);
            }

            if (this.timesUpgraded == RNGS.size()) {
                RNGS.add(Integer.valueOf(rng));
            }

            switch (((Integer)RNGS.get(this.timesUpgraded)).intValue()) {
                case 0:
                    int damageIncrease = 5 + this.timesUpgraded;
                    upgradeDamage(damageIncrease);
                    break;
                case 1:
                    upgradeMagicNumber(1);
                    break;
                case 2:
                    upgradeBaseCost(this.cost - 1);
                    break;
            }
            this.timesUpgraded++;
            this.upgraded = true;
            this.name = NAME + "+" + this.timesUpgraded;
            initializeTitle();
        } else {
            upgradeBaseCost(1);
            upgradeDamage(2);
            upgradeMagicNumber(1);
        }
    }

    public boolean canUpgrade() { return true; }

    public AbstractCard makeCopy() {
        return new Zhupaojiwushuzengfudanyuan();
    }
}
