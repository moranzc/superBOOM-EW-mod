package wishdalmod.helpers;

import basemod.BaseMod;
import basemod.ModLabeledToggleButton;
import basemod.ModPanel;
import com.badlogic.gdx.graphics.Texture;
import com.evacipated.cardcrawl.modthespire.lib.SpireConfig;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.UIStrings;
import java.io.IOException;
import java.util.Properties;

public class ModConfig {
    private static ModPanel settingsPanel;
    public ModConfig() {
    }
    public static void initModSettings() {
    }
    public static void initModConfigMenu() {
        settingsPanel = new ModPanel();
        String modConfDesc = CardCrawlGame.languagePack.getUIString("wishdalemod:Enabled").TEXT[0];
        Texture badge = ImageMaster.loadImage("wishdaleResources/images/UI/orb/boom.png");
        BaseMod.registerModBadge(badge, "wishdalemod", "zc", modConfDesc, settingsPanel);
    }
}
