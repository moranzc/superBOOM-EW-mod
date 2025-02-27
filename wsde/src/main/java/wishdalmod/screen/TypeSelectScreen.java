package wishdalmod.screen;

import basemod.BaseMod;
import basemod.abstracts.CustomSavable;
import basemod.interfaces.ISubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.UIStrings;
import wishdalmod.cards.ZhanshiBOM;
import wishdalmod.cards.ZhanshiBOOM;
import wishdalmod.characters.EW;


import java.util.ArrayList;
import java.util.logging.Logger;

public class TypeSelectScreen extends Object implements ISubscriber, CustomSavable<Integer> {
    public static TypeSelectScreen Inst;
    public int index;
    private AbstractCard cardToPreview;
    public TypeSelectScreen() {
        this.index = 0;
        this.curName = "";
        this.nextName = "";
        this.index = 0;
        refresh();
        this.leftHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        this.rightHb = new Hitbox(70.0F * Settings.scale, 70.0F * Settings.scale);
        BaseMod.subscribe(this);
        BaseMod.addSaveField("wishdalemod_type", this);
    }
    public Hitbox leftHb; public Hitbox rightHb; public String curName; public String nextName;
    public static int getType() {
        if (Inst == null)
            return 0;
        return Inst.index;
    }
    public int prevIndex() { return (this.index - 1 < 0) ? (list.size() - 1) : (this.index - 1); }
    public int nextIndex() { return (this.index + 1 > list.size() - 1) ? 0 : (this.index + 1); }

    public void refresh() {
        this.curName = uiStrings.TEXT[this.index + 1];
        this.nextName = uiStrings.TEXT[nextIndex() + 1];
        this.cardToPreview = (AbstractCard)list.get(this.index);
    }

    public void update() {
        float centerX = (float)Settings.WIDTH * 0.8F;
        float centerY = (float)Settings.HEIGHT * 0.3F;
        this.leftHb.move(centerX - 200.0F * Settings.scale, centerY);
        this.rightHb.move(centerX + 200.0F * Settings.scale, centerY);
        updateInput();
    }

    private void updateInput() {
        if (CardCrawlGame.chosenCharacter == EW.PlayerColorEnum.WISHDALE_ZC) {
            this.leftHb.update();
            this.rightHb.update();
            if (this.leftHb.clicked) {
                this.leftHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = prevIndex();
                refresh();
            }
            if (this.rightHb.clicked) {
                this.rightHb.clicked = false;
                CardCrawlGame.sound.play("UI_CLICK_1");
                this.index = nextIndex();
                refresh();
            }
            if (InputHelper.justClickedLeft) {
                if (this.leftHb.hovered) {
                    this.leftHb.clickStarted = true;
                }

                if (this.rightHb.hovered) {
                    this.rightHb.clickStarted = true;
                }
            }
        }
    }
    public void render(SpriteBatch sb) {
        Logger.getGlobal().info("正在渲染");
        float centerX = Settings.WIDTH * 0.8F;
        float centerY = Settings.HEIGHT * 0.3F;
        renderCard(sb, centerX, centerY);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, uiStrings.TEXT[0], centerX, centerY + 300.0F * Settings.scale, Color.WHITE, 1.25F);
        Color color = Settings.GOLD_COLOR.cpy();
        color.a /= 2.0F;
        float dist = 100.0F * Settings.scale;
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.curName, centerX, centerY, Settings.GOLD_COLOR);
        FontHelper.renderFontCentered(sb, FontHelper.cardTitleFont, this.nextName, centerX + dist * 1.5F, centerY - dist * 0.5F, color);
        if (this.leftHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_LEFT_ARROW, this.leftHb.cX - 24.0F, this.leftHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        if (this.rightHb.hovered) {
            sb.setColor(Color.LIGHT_GRAY);
        } else {
            sb.setColor(Color.WHITE);
        }
        sb.draw(ImageMaster.CF_RIGHT_ARROW, this.rightHb.cX - 24.0F, this.rightHb.cY - 24.0F, 24.0F, 24.0F, 48.0F, 48.0F, Settings.scale, Settings.scale, 0.0F, 0, 0, 48, 48, false, false);
        this.rightHb.render(sb);
        this.leftHb.render(sb);
    }
    private boolean isHovered(Hitbox hb) { return (InputHelper.mX > hb.x && InputHelper.mX < hb.x + hb.width && InputHelper.mY > hb.y && InputHelper.mY < hb.y + hb.height); }
    public void renderCard(SpriteBatch sb, float x, float y) {
        if (this.cardToPreview != null) {
            this.cardToPreview.current_x = x;
            this.cardToPreview.current_y = y + 150.0F * Settings.scale;
            this.cardToPreview.hb.move(x, y + 150.0F * Settings.scale);
            this.cardToPreview.drawScale = 0.7F;
            this.cardToPreview.render(sb);
            if (isHovered(this.cardToPreview.hb)) {
                TipHelper.renderTipForCard(this.cardToPreview, sb, this.cardToPreview.keywords);
            }
        }
    }
    public void onLoad(Integer integer) {
        this.index = integer.intValue();
        refresh();
    }
    public Integer onSave() { return Integer.valueOf(this.index); }
    private static final UIStrings uiStrings = CardCrawlGame.languagePack.getUIString("wishdalemod:Types");
    private static final ArrayList<AbstractCard> list = new ArrayList(); static  {
        list.add(new ZhanshiBOM());
        list.add(new ZhanshiBOOM());
        Inst = new TypeSelectScreen();
    }
}
