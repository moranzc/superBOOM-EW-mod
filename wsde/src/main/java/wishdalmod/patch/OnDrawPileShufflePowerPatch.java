package wishdalmod.patch;

import basemod.ReflectionHacks;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.ShuffleAction;
import com.megacrit.cardcrawl.actions.defect.ShuffleAllAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.powers.BianxieshibujizhanPower;

public class OnDrawPileShufflePowerPatch {
    @SpirePatch(clz = EmptyDeckShuffleAction.class, method = "<ctor>")
    public static class ShufflePatch1 {
        public static void Postfix(EmptyDeckShuffleAction __instance) {
            if (AbstractDungeon.player.hasPower(BianxieshibujizhanPower.POWER_ID)) {
                ((BianxieshibujizhanPower) AbstractDungeon.player.getPower(BianxieshibujizhanPower.POWER_ID)).onShuffle();
            }
        }
    }

    @SpirePatch(clz = ShuffleAction.class, method = "update")
    public static class ShufflePatch2 {
        public static void Postfix(ShuffleAction __instance) {
            boolean b = ((Boolean) ReflectionHacks.getPrivate(__instance, ShuffleAction.class, "triggerRelics")).booleanValue();
            if (b) {
                if (AbstractDungeon.player.hasPower(BianxieshibujizhanPower.POWER_ID)) {
                    ((BianxieshibujizhanPower) AbstractDungeon.player.getPower(BianxieshibujizhanPower.POWER_ID)).onShuffle();
                }
            }
        }

        @SpirePatch(clz = ShuffleAllAction.class, method = "<ctor>")
        public static class ShufflePatch3 {
            public static void Postfix(ShuffleAllAction __instance) {
                if (AbstractDungeon.player.hasPower(BianxieshibujizhanPower.POWER_ID)) {
                    ((BianxieshibujizhanPower) AbstractDungeon.player.getPower(BianxieshibujizhanPower.POWER_ID)).onShuffle();
                }

            }
        }

    }
}