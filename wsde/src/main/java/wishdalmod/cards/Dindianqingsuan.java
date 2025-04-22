package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.CanyingAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Dindianqingsuan extends CustomCard {
    public static final String ID = ModHelper.makePath("Dindianqingsuan");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String IMG_PATH = ModHelper.getCardImagePath("Dindianqingsuan");
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;

    public Dindianqingsuan() {
        super(ID, CARD_STRINGS.NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 4 : 2,
                TypeSelectScreen.getType() == 0 ? CARD_STRINGS.EXTENDED_DESCRIPTION[0] : CARD_STRINGS.DESCRIPTION,
                TYPE, COLOR, RARITY, TARGET);
        updateCardAttributes();
    }

    // 根据模式设置初始数值
    private void updateCardAttributes() {
        if (TypeSelectScreen.getType() == 0) {
            this.isInnate = true;  // 固有
            this.selfRetain = true; // 保留
            this.baseDamage = 7;
            this.magicNumber = this.baseMagicNumber = 3;
            this.cost = this.costForTurn = 4;
        } else {
            this.baseDamage = 6;
            this.magicNumber = this.baseMagicNumber = 4;
            this.cost = this.costForTurn = 2;
        }
        this.initializeDescription();
    }

    // 使用时造成两次伤害并附加残影
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, this.damage, DamageInfo.DamageType.NORMAL)));
        AbstractDungeon.actionManager.addToBottom(new CanyingAction(m, p, this.magicNumber));
    }

    // 自定义升级逻辑
    public void upgrade() {
        if (TypeSelectScreen.getType() != 0 || this.timesUpgraded >= 10) {
            return;
        }

        this.timesUpgraded++;
        this.upgraded = true;
        this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
        this.initializeTitle();

        if (timesUpgraded <= 6) {
            this.upgradeDamage(1);
        } else if (timesUpgraded == 7) {
            this.upgradeDamage(3);
            this.upgradeMagicNumber(2);
            this.cost = this.costForTurn = 3;
        } else if (timesUpgraded == 8) {
            this.upgradeDamage(3);
        } else if (timesUpgraded == 9) {
            this.upgradeDamage(3);
            this.upgradeMagicNumber(2);
            this.cost = this.costForTurn = 2;
        }

        this.initializeDescription();
    }

    // 允许最多升级10次（仅模式0）
    public boolean canUpgrade() {
        return TypeSelectScreen.getType() == 0 && this.timesUpgraded < 10;
    }

    // 攻击牌打出后费用减1的效果（需在其他地方触发监听）
    public void triggerOnOtherCardPlayed(AbstractCard c) {
        if (TypeSelectScreen.getType() == 0 &&
                c.type == CardType.ATTACK &&
                this.costForTurn > 0) {
            this.costForTurn -= 1;
            if (this.costForTurn < 0) this.costForTurn = 0;
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new Dindianqingsuan();
    }
}
