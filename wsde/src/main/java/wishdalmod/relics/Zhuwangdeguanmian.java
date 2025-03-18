package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import wishdalmod.helpers.ModHelper;

public class Zhuwangdeguanmian extends CustomRelic {
    public static final String ID = ModHelper.makePath("Zhuwangdeguanmian");
    private static final String IMG = "wishdaleResources/images/relics/Zhuwangdeguanmian.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Zhuwangdeguanmian_o.png";
    private static final int BASE_STRENGTH = 5;
    private static final int FULL_SET_STRENGTH = 15;
    private boolean isActive = false;

    public Zhuwangdeguanmian() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + 5 + DESCRIPTIONS[1] + 15 + DESCRIPTIONS[2];
    }
    private boolean shouldActivate() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null || p.isDeadOrEscaped()) return false;

        int currentHP = p.currentHealth;
        int maxHP = p.maxHealth;
        int threshold = (int) Math.max(10, maxHP * 0.1);
        return currentHP <= threshold;
    }
    private void checkActivation() {
        if (AbstractDungeon.getCurrRoom() != null &&
                AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {

            boolean shouldBeActive = shouldActivate();
            if (shouldBeActive != isActive) {
                if (shouldBeActive) {
                    activateRelic(AbstractDungeon.player);
                } else {
                    deactivateRelic(AbstractDungeon.player);
                }
            }
        }
    }
    public void atTurnStart() {
        checkActivation();
    }
    public void onLoseHp(int damageAmount) {
        checkActivation();
    }
    public void atBattleStart() {
        checkActivation();
    }
    private void activateRelic(AbstractPlayer player) {
        int strengthAmount = hasFullSet() ? 15 : 5;
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, strengthAmount), strengthAmount));
        addToBot(new RelicAboveCreatureAction(player, this));
        flash();
        beginLongPulse();
        isActive = true;
    }
    private void deactivateRelic(AbstractPlayer player) {
        int strengthAmount = hasFullSet() ? 15 : 5;
        addToBot(new ApplyPowerAction(player, player, new StrengthPower(player, -strengthAmount), -strengthAmount));
        stopPulse();
        isActive = false;
    }
    private boolean hasFullSet() {
        String[] KING_RELICS = {
                Guowangdexinqiang.ID,
                Guowangdeyanshen.ID,
                Guowangdekaijia.ID,
                Zhuwangdeguanmian.ID,
                GuowangdeHujie.ID
        };
        int count = 0;
        for (String relicId : KING_RELICS) {
            if (AbstractDungeon.player.hasRelic(relicId)) count++;
        }
        return count >= 3;
    }
    public void onVictory() {
        stopPulse();
        isActive = false;
    }
    public AbstractRelic makeCopy() {
        return new Zhuwangdeguanmian();
    }
}