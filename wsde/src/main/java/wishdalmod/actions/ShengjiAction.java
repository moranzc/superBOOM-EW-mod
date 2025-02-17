package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

public class ShengjiAction extends AbstractGameAction {
    private DamageInfo info;
    private AbstractCard theCard = null;

    public ShengjiAction(AbstractCreature target, DamageInfo info) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }
    public void update() {
        if (this.duration == Settings.ACTION_DUR_MED) {
            if (this.target != null) {
                AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
                this.target.damage(this.info);
                if ((((AbstractMonster) this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                    ArrayList<AbstractCard> possibleCards = new ArrayList();
                    Iterator var2 = AbstractDungeon.player.masterDeck.group.iterator();

                    while (var2.hasNext()) {
                        AbstractCard c = (AbstractCard) var2.next();
                        if (c.canUpgrade()) {
                            possibleCards.add(c);
                        }
                    }
                    Collections.shuffle(possibleCards, new Random(AbstractDungeon.miscRng.randomLong()));
                    if (!possibleCards.isEmpty()) {
                        if (possibleCards.size() == 1) {
                            ((AbstractCard) possibleCards.get(0)).upgrade();
                            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard) possibleCards.get(0));
                            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard) possibleCards.get(0)).makeStatEquivalentCopy()));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        } else {
                            ((AbstractCard) possibleCards.get(0)).upgrade();
                            ((AbstractCard) possibleCards.get(1)).upgrade();
                            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard) possibleCards.get(0));
                            AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard) possibleCards.get(1));
                            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard) possibleCards.get(0)).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard) possibleCards.get(1)).makeStatEquivalentCopy(), (float) Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, (float) Settings.HEIGHT / 2.0F));
                            AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                        }
                    }
                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }
                }
            }
        }
        this.tickDuration();
        if (this.isDone && this.theCard != null) {
            AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(this.theCard.makeStatEquivalentCopy()));
            this.addToTop(new WaitAction(Settings.ACTION_DUR_MED));
        }
    }
}
