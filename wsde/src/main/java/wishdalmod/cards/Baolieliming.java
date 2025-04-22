package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import wishdalmod.actions.SummonZuzongAction;
import wishdalmod.helpers.ModHelper;
import wishdalmod.powers.BaolielimingPower;
import wishdalmod.screen.TypeSelectScreen;

import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;

public class Baolieliming extends CustomCard {
    public static final String ID = ModHelper.makePath("Baolieliming");
    private static final CardStrings CARD_STRINGS = CardCrawlGame.languagePack.getCardStrings(ID);
    private static final String NAME = CARD_STRINGS.NAME;
    private static final String IMG_PATH = ModHelper.getCardImagePath("Baolieliming");
    private static final String DESCRIPTION = CARD_STRINGS.DESCRIPTION;
    private static final CardType TYPE = CardType.POWER;
    private static final CardColor COLOR = WISHDALE_RED;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    public Baolieliming() {
        super(ID, NAME, IMG_PATH, TypeSelectScreen.getType() == 0 ? 70 : 5, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        this.baseMagicNumber = 6;
        this.magicNumber = this.baseMagicNumber;
        this.cardsToPreview = new Sihunling();
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

//    @Override
//    public void triggerWhenDrawn() {
//        // 根据升级状态，添加初始技力卡
//        if (this.timesUpgraded >= 1 && this.timesUpgraded <= 3) {
//            // 1到3次升级：1张初始技力卡和1张升级版初始技力卡
//            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 1));
//            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 1)); // 升级版初始技力
//        } else if (this.timesUpgraded >= 4 && this.timesUpgraded <= 6) {
//            // 4到6次升级：3张升级版初始技力卡
//            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 3)); // 升级版初始技力
//        } else if (this.timesUpgraded >= 7 && this.timesUpgraded <= 9) {
//            // 7到9次升级：2张升级版初始技力卡和1张初始技力卡
//            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 2)); // 升级版初始技力
//            this.addToBot(new MakeTempCardInHandAction(new Chushijili(), 1)); // 初始技力
//        } else if (this.timesUpgraded == 10) {
//            // 第10次升级：不再添加初始技力卡，并将费用设置为10
//            this.setCostForTurn(10);
//        }
//    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (TypeSelectScreen.getType() == 0) {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(35, 7, 0));
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(35, 7, 0));
            this.addToBot(new ApplyPowerAction(p, p, new BaolielimingPower(p, this.magicNumber), this.magicNumber));
            this.rawDescription = CARD_STRINGS.EXTENDED_DESCRIPTION[0];
        } else {
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(15, 0, 0));
            AbstractDungeon.actionManager.addToTop(new SummonZuzongAction(15, 0, 0));
            this.addToBot(new ApplyPowerAction(p, p, new BaolielimingPower(p, this.magicNumber), this.magicNumber));
            this.rawDescription = CARD_STRINGS.DESCRIPTION;
        }
        CardCrawlGame.sound.play("BOOM");
    }

    @Override
    public void upgrade() {
        if (this.timesUpgraded < 10) {
            this.timesUpgraded++;
            this.upgraded = true;
            this.name = CARD_STRINGS.NAME + "+" + this.timesUpgraded;
            // 调整升级时的费用
            switch (this.timesUpgraded) {
                case 1:
                    setCustomCost(65);
                    break;
                case 2:
                    setCustomCost(60);
                    break;
                case 3:
                    setCustomCost(55);
                    break;
                case 4:
                    setCustomCost(50);
                    break;
                case 5:
                    setCustomCost(45);
                    break;
                case 6:
                    setCustomCost(40);
                    break;
                case 7:
                    setCustomCost(35);
                    break;
                case 8:
                    setCustomCost(30);
                    break;
                case 9:
                    setCustomCost(25);
                    break;
                case 10:
                    setCustomCost(10);
                    break;
            }
        }
    }
    private void setCustomCost(int newCost) {
        this.cost = newCost;
        this.costForTurn = newCost;
        this.isCostModified = true;
    }
    @Override
    public boolean canUpgrade() {
        return this.timesUpgraded < 10; // 允许升级最多10次
    }

    @Override
    public AbstractCard makeCopy() {
        return new Baolieliming();
    }
}
