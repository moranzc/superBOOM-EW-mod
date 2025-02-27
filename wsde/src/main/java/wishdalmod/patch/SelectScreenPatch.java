package wishdalmod.patch;

import basemod.ReflectionHacks;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import wishdalmod.characters.EW;
import wishdalmod.screen.TypeSelectScreen;


public class SelectScreenPatch {
    public static boolean isPuzzlerSelected() { return (CardCrawlGame.chosenCharacter == EW.PlayerColorEnum.WISHDALE_ZC && ((Boolean)ReflectionHacks.getPrivate(CardCrawlGame.mainMenuScreen.charSelectScreen, CharacterSelectScreen.class, "anySelected")).booleanValue());}
    @SpirePatch(clz = CharacterSelectScreen.class, method = "render")
    public static class RenderButtonPatch
    {
        @SpirePostfixPatch
        public static void Postfix(CharacterSelectScreen _inst, SpriteBatch sb) {
            if (SelectScreenPatch.isPuzzlerSelected()) {
                TypeSelectScreen.Inst.render(sb);
            }
        }
    }

    @SpirePatch(clz = CharacterSelectScreen.class, method = "update")
    public static class UpdateButtonPatch
    {
        @SpirePostfixPatch
        public static void Prefix(CharacterSelectScreen _inst) {
            if (SelectScreenPatch.isPuzzlerSelected()) {
                TypeSelectScreen.Inst.update();
            }
        }
    }
}
