package wishdalmod.characters;

import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import wishdalmod.cards.Strike;
import wishdalmod.helpers.CanyingXiaoguo;
import wishdalmod.modcore.WishdaleMod;
import wishdalmod.powers.ZuzongPower;
import wishdalmod.relics.Wishdalebadge;
import wishdalmod.screen.TypeSelectScreen;


import java.util.ArrayList;


// 继承CustomPlayer类
public class EW extends CustomPlayer {
    public CanyingXiaoguo CanyingXiaoguo = new CanyingXiaoguo();
    // 火堆的人物立绘（行动前）
    private static final String WISHDALE_ZC_SHOULDER_1 = "wishdaleResources/images/char/shoulder1.png";
    // 火堆的人物立绘（行动后）
    private static final String WISHDALE_ZC_SHOULDER_2 = "wishdaleResources/images/char/shoulder2.png";
    // 人物死亡图像
    private static final String CORPSE_IMAGE = "wishdaleResources/images/char/corpse.png";
    // 战斗界面左下角能量图标的每个图层
    private static final String[] ORB_TEXTURES = new String[]{
            "wishdaleResources/images/UI/orb/layer5.png",
            "wishdaleResources/images/UI/orb/layer4.png",
            "wishdaleResources/images/UI/orb/layer3.png",
            "wishdaleResources/images/UI/orb/layer2.png",
            "wishdaleResources/images/UI/orb/layer1.png",
            "wishdaleResources/images/UI/orb/layer6.png",
            "wishdaleResources/images/UI/orb/layer5d.png",
            "wishdaleResources/images/UI/orb/layer4d.png",
            "wishdaleResources/images/UI/orb/layer3d.png",
            "wishdaleResources/images/UI/orb/layer2d.png",
            "wishdaleResources/images/UI/orb/layer1d.png"
    };
    // 每个图层的旋转速度
    private static final float[] LAYER_SPEED = new float[]{-40.0F, -32.0F, 20.0F, -20.0F, 0.0F, -10.0F, -8.0F, 5.0F, -5.0F, 0.0F};
    // 祖宗的位置
    public static final float[] POSX = new float[] { 275.0F, 245.0F, 0.0F };
    public static final float[] POSY = new float[] { 20.0F, 285.0F, 340.0F };
    // 三个位置对应的祖宗
    public final Zuzong[] zuzongs = new Zuzong[3];
    // 当前存活的祖宗
    public final ArrayList<Zuzong> currentZuzongs = new ArrayList<>();

    // 人物的本地化文本，如卡牌的本地化文本一样，如何书写见下
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString("wishdalemod:wishdale");
    public EW(String name) {
        super(name, PlayerColorEnum.WISHDALE_ZC,ORB_TEXTURES,"wishdaleResources/images/UI/orb/vfx.png", LAYER_SPEED, null, null);
        // 人物对话气泡的大小，如果游戏中尺寸不对在这里修改（libgdx的坐标轴左下为原点）
        this.dialogX = (this.drawX + 0.0F * Settings.scale);
        this.dialogY = (this.drawY + 150.0F * Settings.scale);
        // 初始化你的人物，如果你的人物只有一张图，那么第一个参数填写你人物图片的路径。
        this.initializeClass(
                "wishdaleResources/images/char/character.png", // 人物图片
                WISHDALE_ZC_SHOULDER_2, WISHDALE_ZC_SHOULDER_1,
                CORPSE_IMAGE, // 人物死亡图像
                this.getLoadout(),
                0.0F, 0.0F,
                200.0F, 220.0F, // 人物碰撞箱大小，越大的人物模型这个越大
                new EnergyManager(3) // 初始每回合的能量
        );

        // 小人动画
        this.loadAnimation("wishdaleResources/images/char/char_1035_wisdel.atlas",
                        "wishdaleResources/images/char/char_1035_wisdel.json",
                        1.8F);
        AnimationState.TrackEntry e = this.state.setAnimation(0, "Idle", true);
        e.setTime(e.getEndTime() * MathUtils.random());
        e.setTimeScale(1.2F);

    // 待机和Die动画  过渡0.1秒
        this.stateData.setMix("Idle", "Die", 0.1F);
        this.state.setAnimation(0, "Idle", true);
    }
    // 死亡动画
    public void playDeathAnimation() {
        this.state.setAnimation(0, "Die", false);
    }
    //卡牌动画
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        if (c.type == AbstractCard.CardType.ATTACK) {
            if (AbstractDungeon.player.hasPower("wishdalemod:BaolielimingPower")) {
                this.state.setAnimation(0, "Skill_3_Loop", false);
                this.state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
            }
            else if (c instanceof wishdalmod.cards.Dindianqingsuan) {
                this.state.setAnimation(0, "Skill_1", false);
            }
            else {
                int randomAttack = MathUtils.random(0, 2);
                if (randomAttack == 0) {
                    this.state.setAnimation(0, "Attack_A", false);
                } else if (randomAttack == 1) {
                    this.state.setAnimation(0, "Attack_B", false);
                } else {
                    this.state.setAnimation(0, "Attack_C", false);
                }
                this.state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
        else if (c.type == AbstractCard.CardType.POWER) {
            if (c instanceof wishdalmod.cards.Baolieliming) {
                this.state.setAnimation(0, "Skill_3_Begin", false);
                this.state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
            }
            else if (AbstractDungeon.player.hasPower("wishdalemod:BaolielimingPower")) {
                this.state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
            }
            else {
                this.state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
        else {
            if (AbstractDungeon.player.hasPower("wishdalemod:BaolielimingPower")) {
                this.state.addAnimation(0, "Skill_3_Idle", true, 0.0F);
            }
            else{
                this.state.addAnimation(0, "Idle", true, 0.0F);
            }
        }
        super.useCard(c, monster, energyOnUse);
    }

    // 初始卡组的ID，可直接写或引用变量
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add("wishdalemod:Strike");
        retVal.add("wishdalemod:Strike");
        retVal.add("wishdalemod:Strike");
        retVal.add("wishdalemod:Strike");
        retVal.add("wishdalemod:Strike");
        retVal.add("wishdalemod:Defend");
        retVal.add("wishdalemod:Defend");
        retVal.add("wishdalemod:Defend");
        retVal.add("wishdalemod:Dindianqingsuan");
        retVal.add("wishdalemod:Sihunlingdeyuxi");
        return retVal;
    }
    // 初始遗物的ID
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        retVal.add(Wishdalebadge.ID);
        return retVal;
    }
    public CharSelectInfo getLoadout() {
        if (TypeSelectScreen.getType() == 0) {
            return new CharSelectInfo(
                    characterStrings.NAMES[0], // 人物名字
                    characterStrings.TEXT[0], // 人物介绍
                    47, // 当前血量
                    47, // 最大血量
                    0, // 初始充能球栏位
                    99, // 初始携带金币
                    5, // 每回合抽牌数量
                    this, // 别动
                    this.getStartingRelics(), // 初始遗物
                    this.getStartingDeck(), // 初始卡组
                    false
            );
        } else {
            return new CharSelectInfo(
                    characterStrings.NAMES[0], // 人物名字
                    characterStrings.TEXT[0], // 人物介绍
                    79, // 当前血量
                    79, // 最大血量
                    0, // 初始充能球栏位
                    99, // 初始携带金币
                    5, // 每回合抽牌数量
                    this, // 别动
                    this.getStartingRelics(), // 初始遗物
                    this.getStartingDeck(), // 初始卡组
                    false
            );
        }

    }

    // 人物名字（出现在游戏左上角）
    public String getTitle(AbstractPlayer.PlayerClass playerClass) {
        return characterStrings.NAMES[0];
    }

    // 你的卡牌颜色（这个枚举在最下方创建）
    
    public AbstractCard.CardColor getCardColor() {
        return PlayerColorEnum.WISHDALE_RED;
    }
    // 翻牌事件出现的你的职业牌
    public AbstractCard getStartCardForEvent() {
        return new Strike();
    }
    // 卡牌轨迹颜色
    public com.badlogic.gdx.graphics.Color getCardTrailColor() {
        return WishdaleMod.MY_COLOR;
    }
    // 高进阶带来的生命值损失
    public int getAscensionMaxHPLoss() {
        return 9;
    }
    // 卡牌的能量字体，没必要修改
    public BitmapFont getEnergyNumFont() {
        return FontHelper.energyNumFontBlue;
    }
    // 人物选择界面点击你的人物按钮时触发的方法，这里为屏幕轻微震动
    public void doCharSelectScreenSelectEffect() {
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
        CardCrawlGame.sound.play("BOOM");
    }

    // 碎心图片
    public ArrayList<CutscenePanel> getCutscenePanels() {
        ArrayList<CutscenePanel> panels = new ArrayList<>();
        // 有两个参数的，第二个参数表示出现图片时播放的音效
        panels.add(new CutscenePanel("wishdaleResources/images/char/Victory1.png", "ATTACK_MAGIC_FAST_1"));
        panels.add(new CutscenePanel("wishdaleResources/images/char/Victory2.png"));
        panels.add(new CutscenePanel("wishdaleResources/images/char/Victory3.png"));
        return panels;
    }

    // 自定义模式选择你的人物时播放的音效
    
    public String getCustomModeCharacterButtonSoundKey() {
        return "ATTACK_HEAVY";
    }

    // 游戏中左上角显示在你的名字之后的人物名称
    
    public String getLocalizedCharacterName() {
        return characterStrings.NAMES[0];
    }

    // 创建人物实例，照抄
    
    public AbstractPlayer newInstance() {
        return new EW(this.name);
    }

    // 第三章面对心脏说的话
    
    public String getSpireHeartText() {
        return characterStrings.TEXT[1];
    }

    // 打心脏的颜色，不是很明显
    
    public com.badlogic.gdx.graphics.Color getSlashAttackColor() {
        return WishdaleMod.MY_COLOR;
    }

    // 吸血鬼事件文本，主要是他（索引为0）和她（索引为1）的区别（机器人另外）
    
    public String getVampireText() {
        return Vampires.DESCRIPTIONS[1];
    }

    // 卡牌选择界面选择该牌的颜色
    
    public Color getCardRenderColor() {
        return WishdaleMod.MY_COLOR;
    }

    // 第三章面对心脏造成伤害时的特效
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        return new AbstractGameAction.AttackEffect[]{AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL, AbstractGameAction.AttackEffect.SLASH_HEAVY, AbstractGameAction.AttackEffect.FIRE, AbstractGameAction.AttackEffect.SLASH_DIAGONAL};
    }
    public static class PlayerColorEnum {
        @SpireEnum
        public static AbstractPlayer.PlayerClass WISHDALE_ZC;
        @SpireEnum
        public static AbstractCard.CardColor WISHDALE_RED;
    }

    public static class PlayerLibraryEnum {
        // 这个变量未被使用（呈现灰色）是正常的
        @SpireEnum
        public static CardLibrary.LibraryType WISHDALE_RED;
    }

    //祖宗
    public void removeDeadZuzong(Zuzong zuzong) {
        currentZuzongs.remove(zuzong);
    }
    public void render(SpriteBatch sb) {
        super.render(sb);
        if ((AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT || AbstractDungeon.getCurrRoom() instanceof MonsterRoom) && !isDead) {
            currentZuzongs.removeIf(r -> r.isDead);
            for (Zuzong r: currentZuzongs) r.render(sb);
        }
    }
    public void update() {
        super.update();
        currentZuzongs.removeIf(r -> r.isDead);
        for (Zuzong r : currentZuzongs) r.update();
    }
    public void applyStartOfTurnPowers() {
        super.applyStartOfTurnPowers();
        currentZuzongs.removeIf(r -> r.isDead);
        for (Zuzong r : currentZuzongs) {
            r.applyStartOfTurnPowers();
        }
    }

    public void summonZuzong(int maxHealth, int strength, int block) {
        if (maxHealth <= 0) maxHealth = 1;
        for (int i = 0; i < 3; i++) {
            if (zuzongs[i] == null || zuzongs[i].isDead) {
                zuzongs[i] = new Zuzong(this, maxHealth, POSX[i], POSY[i]);
                zuzongs[i].showHealthBar();
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(this, this,
                        new ZuzongPower(this, 1), 1));

                if (block > 0) {
                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(zuzongs[i], block));
                }
                if (strength > 0) {
                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(zuzongs[i], this,
                            new StrengthPower(zuzongs[i], strength)));
                }
                currentZuzongs.add(zuzongs[i]);
                return;
            }
        }
    }
//    public void summonZuzong(int maxHealth, int strength, int block) {
//        if (maxHealth <= 0) maxHealth = 1;
//        for (int i = 0; i < 3; i++)
//            if (zuzongs[i] == null || zuzongs[i].isDead) {
//                zuzongs[i] = new Zuzong(this, maxHealth, POSX[i], POSY[i]);
//                zuzongs[i].showHealthBar();
//                if (block > 0) {
//                    AbstractDungeon.actionManager.addToTop(new GainBlockAction(zuzongs[i], block));
//                }
//                if (strength > 0) {
//                    AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(zuzongs[i], this, new StrengthPower(zuzongs[i], strength)));
//                }
//                currentZuzongs.add(zuzongs[i]);
//                return;
//            }
//    }
    public void die() {isDead = true;}
    public void damage(DamageInfo info) {
        super.damage(info);
    }
//    public void damage(DamageInfo info) {
//        currentZuzongs.removeIf(r -> r.isDead);
//        if (info.type == DamageInfo.DamageType.NORMAL && !currentZuzongs.isEmpty()) {
//            currentZuzongs.get(currentZuzongs.size() - 1).damage(info);
//            for (AbstractRelic r : relics) r.onAttackedToChangeDamage(info, 0);
//            for (AbstractPower p : powers) p.onAttackedToChangeDamage(info, 0);
//            if (info.owner != null) {
//                for (AbstractPower p : powers) p.onAttacked(info, 0);
//                for (AbstractRelic r : relics) r.onAttacked(info, 0);
//            }
//            for (AbstractRelic r : relics)  r.onLoseHpLast(0);
//        }
//        else {
//            super.damage(info);
//        }
//        if (this.currentHealth <= 0)
//        {
//            die();
//            }
//    }

    public void onVictory() {
        super.onVictory();
        for (Zuzong r : currentZuzongs) {
            r.isDead = true;
        }
        currentZuzongs.clear();
        this.state.addAnimation(0, "Idle", true, 0.0F);
    }

}
