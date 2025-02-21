package wishdalmod.cards;

import basemod.abstracts.CustomCard;
import basemod.abstracts.DynamicVariable;
import basemod.helpers.TooltipInfo;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.ArrayList;
import java.util.List;


public abstract class AbstractEWCard extends CustomCard {
    public int baseSecondMagicNumber;
    public int secondMagicNumber;
    public boolean upgradedSecondMagicNumber;
    private float rotationTimer = 0.0F;
    private int previewIndex = 0;
    public ArrayList<AbstractCard> previewList = null;
    public AbstractEWCard(String id, String name, String img, int cost, String rawDescription, AbstractCard.CardType type, AbstractCard.CardColor color, AbstractCard.CardRarity rarity, AbstractCard.CardTarget target) { super(id, name, img, cost, rawDescription, type, color, rarity, target); }
    public List<TooltipInfo> getCustomTooltips() { return new ArrayList(); }
    public void upgradeSecondMagicNumber(int amount) {
        this.baseSecondMagicNumber += amount;
        this.secondMagicNumber = this.baseSecondMagicNumber;
        this.upgradedSecondMagicNumber = true;
    }
    public boolean extraTriggered() { return false; }
    public void triggerOnGlowCheck() {
        if (extraTriggered()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }
    public static int staticCalcDmg(AbstractMonster m, int baseDmg, DamageInfo.DamageType type, boolean isFriendDamage) {
        if (m == null) return baseDmg;
        float tmp = baseDmg;
        for (AbstractPower power : m.powers)
            tmp = power.atDamageReceive(tmp, type);
        if (m.hasPower("nearlmod:Duel") && isFriendDamage) {
            tmp = MathUtils.floor(tmp * (1.0F - 0.01F * (m.getPower("nearlmod:Duel")).amount));
        }
        for (AbstractPower power : m.powers)
            tmp = power.atDamageFinalReceive(tmp, type);
        if (tmp < 0.0F) {
            tmp = 0.0F;
        } else if (!isFriendDamage && m.currentBlock <= 0 && AbstractDungeon.player.hasPower("nearlmod:DayBreakPower") && type == DamageInfo.DamageType.NORMAL) {
            tmp += 3.0F;
        }
        return MathUtils.floor(tmp);
    }
    public int calculateSingleDamage(AbstractMonster m, int baseDmg, boolean isFriendDamage) { return staticCalcDmg(m, baseDmg, this.damageTypeForTurn, isFriendDamage); }
    public static void addSpecificCardsToReward(AbstractCard card) {
        ArrayList<AbstractCard> cards = new ArrayList<AbstractCard>();
        cards.add(card);
        addSpecificCardsToReward(cards);
    }
    public static void addSpecificCardsToReward(ArrayList<AbstractCard> cards) {
        for (AbstractCard card : cards) {
            if (!card.canUpgrade())
                continue;  for (AbstractRelic r : AbstractDungeon.player.relics)
                r.onPreviewObtainCard(card);
        }
        RewardItem item = new RewardItem();
        item.cards = cards;
        AbstractDungeon.getCurrRoom().addCardReward(item);
    }
    public AbstractCard makeStatEquivalentCopy() {
        AbstractCard card = super.makeStatEquivalentCopy();
        card.exhaust = this.exhaust;
        card.isEthereal = this.isEthereal;
        card.retain = this.retain;
        card.selfRetain = this.selfRetain;
        card.rawDescription = this.rawDescription;
        card.initializeDescription();
        return card;
    }
    public void update() {
        super.update();
        if (this.previewList == null) {
            return;
        }
        if (this.hb.hovered) {
            if (this.rotationTimer <= 0.0F) {
                this.rotationTimer = 2.0F;
                this.cardsToPreview = (AbstractCard)this.previewList.get(this.previewIndex);
                if (this.previewIndex == this.previewList.size() - 1) {
                    this.previewIndex = 0;
                } else {
                    this.previewIndex++;
                }
            } else {
                this.rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }
}