package wishdalmod.relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.animations.TalkAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import wishdalmod.helpers.ModHelper;

public class Ruozhitutou extends CustomRelic {
    public static final String ID = ModHelper.makePath("Ruozhitutou");
    private static final String IMG = "wishdaleResources/images/relics/Ruozhitutou.png";
    private static final String IMG_OTL = "wishdaleResources/images/relics/Ruozhitutou_o.png";
    public Ruozhitutou() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), AbstractRelic.RelicTier.UNCOMMON, AbstractRelic.LandingSound.FLAT);
    }
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }
    public void atBattleStart() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new TalkAction(true, this.DESCRIPTIONS[1], 1.0F, 2.0F));
        CardCrawlGame.sound.play("Tutou");
    }
    public CustomRelic makeCopy() {
        return new Ruozhitutou();
    }
}