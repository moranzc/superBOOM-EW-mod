package wishdalmod.patch;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DescriptionLine;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.characters.EW;

public class WishdaleBOOMBOOMPatch {
    @SpirePatch(clz = AbstractCard.class, method = "initializeDescription")
    public static class DescriptionPatch {
        @SpirePostfixPatch
        public static void Postfix(AbstractCard _inst) {
            if (!(AbstractDungeon.player instanceof EW))
                return;
            for (DescriptionLine line :_inst.description) {
                line.text = line.text.replace("消耗", "爆炸");
            }
            for (int i =0; i<_inst.keywords.size(); i++) {
                if (_inst.keywords.get(i).equals("消耗")) {
                    _inst.keywords.set(i, "wishdalemod:爆炸");
                }
            }
        }
    }
}