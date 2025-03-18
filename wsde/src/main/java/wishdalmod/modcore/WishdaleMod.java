package wishdalmod.modcore;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.mod.stslib.icons.CustomIconHelper;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.google.gson.Gson;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.*;
import wishdalmod.actions.BaozhaAction;
import wishdalmod.cards.*;
import wishdalmod.characters.EW;
import wishdalmod.helpers.ImageHelper;
import wishdalmod.helpers.ModConfig;
import wishdalmod.relics.*;
import wishdalmod.util.BoomIcon;

import java.nio.charset.StandardCharsets;
import static com.megacrit.cardcrawl.core.Settings.language;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_RED;
import static wishdalmod.characters.EW.PlayerColorEnum.WISHDALE_ZC;

@SpireInitializer
public class WishdaleMod implements PostExhaustSubscriber,EditCardsSubscriber,EditStringsSubscriber,EditCharactersSubscriber,EditRelicsSubscriber,PostInitializeSubscriber,AddAudioSubscriber,EditKeywordsSubscriber{
    public static final String MY_CHARACTER_BUTTON = "wishdaleResources/images/char/Character_Button.png";
    public static final String MY_CHARACTER_PORTRAIT = "wishdaleResources/images/char/Character_Portrait.png";
    public static final String BG_ATTACK_512 = "wishdaleResources/images/512/bg_attack_512.png";
    public static final String BG_POWER_512 = "wishdaleResources/images/512/bg_power_512.png";
    public static final String BG_SKILL_512 = "wishdaleResources/images/512/bg_skill_512.png";
    public static final String SMALL_ORB = "wishdaleResources/images/char/small_orb.png";
    public static final String BG_ATTACK_1024 = "wishdaleResources/images/1024/bg_attack.png";
    public static final String BG_POWER_1024 = "wishdaleResources/images/1024/bg_power.png";
    public static final String BG_SKILL_1024 = "wishdaleResources/images/1024/bg_skill.png";
    public static final String BIG_ORB = "wishdaleResources/images/char/card_orb.png";
    public static final String ENEYGY_ORB = "wishdaleResources/images/char/cost_orb.png";
    public static final Color MY_COLOR = new Color(136.0F / 255.0F, 39.0F / 255.0F, 39.0F / 255.0F, 1.0F);
    public void receivePostInitialize() {
        ModConfig.initModConfigMenu();
        ImageHelper.initThis();
    }
    public WishdaleMod() {
        BaseMod.subscribe(this);
        BaseMod.addColor(WISHDALE_RED, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, MY_COLOR, BG_ATTACK_512, BG_SKILL_512, BG_POWER_512, ENEYGY_ORB, BG_ATTACK_1024, BG_SKILL_1024, BG_POWER_1024, BIG_ORB, SMALL_ORB);
        ModConfig.initModSettings();
    }

    public void receivePostExhaust(AbstractCard abstractCard) {
        if (AbstractDungeon.player instanceof EW) {
            AbstractDungeon.actionManager.addToTop(new BaozhaAction());
        }
    }

    public static void initialize() {
        new WishdaleMod();
    }
//注册
    public void receiveEditCards() {
        CustomIconHelper.addCustomIcon(BoomIcon.get());
        BaseMod.addCard(new Strike());//打击
        BaseMod.addCard(new Strike());//打击
        BaseMod.addCard(new Defend());//防御
        BaseMod.addCard(new Sihunlingdeyuxi());//死魂灵的余息
        BaseMod.addCard(new Dindianqingsuan());//定点清算
        //攻击
        BaseMod.addCard(new Shouliudan());//手榴弹
        BaseMod.addCard(new Zhenhandan());//震撼弹
        BaseMod.addCard(new Baohedaji());//饱和打击
        BaseMod.addCard(new Zhongyanluomu());//终焉落幕
        BaseMod.addCard(new Yigongdaishou());//以攻代守
        BaseMod.addCard(new Zidongsheji());//自动射击
        BaseMod.addCard(new Wuxianhuoli());//无限火力
        BaseMod.addCard(new Guozaimoshi());//过载模式
        BaseMod.addCard(new Yinbao());//音爆
        BaseMod.addCard(new Yizhanyangzhan());//以战养战
        BaseMod.addCard(new Yishanghuanshang());//以伤换伤
        BaseMod.addCard(new Zhupaojiwushuzengfudanyuan());//主炮级巫术增幅单元
        BaseMod.addCard(new Diedai());//迭代
        BaseMod.addCard(new Yineisidetou());//伊内丝的头
        BaseMod.addCard(new Zixuechengcai());//自学成才
        BaseMod.addCard(new Dindianqingsuan());//定点清算
        BaseMod.addCard(new Canyingbishou());//残影匕首
        //BaseMod.addCard(new 预设());//预设
        //BaseMod.addCard(new 预设());//预设
        //BaseMod.addCard(new 预设());//预设
        //BaseMod.addCard(new 预设());//预设
        //BaseMod.addCard(new 预设());//预设
        //技能
        BaseMod.addCard(new Kunanchenshuzhe());//苦难陈述者
        BaseMod.addCard(new Lingguangyishan());//灵光一闪
        BaseMod.addCard(new Dailizhihui());//代理指挥
        BaseMod.addCard(new Boom());//BOOM!
        BaseMod.addCard(new Chufaqianyanjiang());//出发前演讲
        BaseMod.addCard(new Muguangdaizhi());//目光呆滞
        BaseMod.addCard(new Fangshouyibo());//放手一搏
        BaseMod.addCard(new Shanguangdan());//闪光弹
        BaseMod.addCard(new Zhanshuguihua());//战术规划
        BaseMod.addCard(new Buwendingbuji());//不稳定补给
        BaseMod.addCard(new Chongzu());//重组
        BaseMod.addCard(new Tudou());//土豆，土豆
        BaseMod.addCard(new Mositima());//莫斯提马
        BaseMod.addCard(new Shunshen());//瞬身
        BaseMod.addCard(new Gedang());//格挡
        BaseMod.addCard(new Meide());//美德
        BaseMod.addCard(new Huandan());//换弹
        BaseMod.addCard(new Boming());//搏命
        BaseMod.addCard(new Kuangzhan());//狂战
        //BaseMod.addCard(new 预设());//预设
        //BaseMod.addCard(new 预设());//预设

        //能力
        BaseMod.addCard(new Hunlingpingzhang());//魂灵屏障
        BaseMod.addCard(new Zhanhouxiuzheng());//战后休息
        BaseMod.addCard(new Shefu());//设伏
        BaseMod.addCard(new Zhishixuebao());//芝士雪豹
        BaseMod.addCard(new Baolieliming());//爆裂黎明
        BaseMod.addCard(new Yinbi());//隐蔽
        BaseMod.addCard(new Bianxieshibujizhan());//便携式补给站
        BaseMod.addCard(new Fulingkaijia());//附灵铠甲
        BaseMod.addCard(new Bianyuanxingzhe());//边缘行者
        BaseMod.addCard(new Xuying());//虚影
        BaseMod.addCard(new Hunlingqiyue());//魂灵契约
        BaseMod.addCard(new Yanchenhuanrao());//烟雾缭绕（烟尘环绕

        //特殊
        BaseMod.addCard(new ZhanshiBOM());//展示简单
        BaseMod.addCard(new ZhanshiBOOM());//展示呆滞
        BaseMod.addCard(new Sihunling());//死魂灵（爆裂黎明）
    }

    public void receiveEditCharacters() {
        BaseMod.addCharacter(new EW(CardCrawlGame.playerName), MY_CHARACTER_BUTTON, MY_CHARACTER_PORTRAIT, WISHDALE_ZC);

    }
    public void receiveEditKeywords() {
        Gson gson = new Gson();
        String lang = "ENG";
        if (language == Settings.GameLanguage.ZHS) {
                lang = "ZHS";
        }
            String json = Gdx.files.internal("wishdaleResources/localization/"+lang+"/keywords.json").readString(String.valueOf(StandardCharsets.UTF_8));
        Keyword[] keywords = gson.fromJson(json, Keyword[].class);
        if (keywords != null) {
            for (Keyword keyword : keywords) {
                BaseMod.addKeyword("wishdalemod", keyword.NAMES[0], keyword.NAMES, keyword.DESCRIPTION);
            }
        }
    }
    public void receiveEditStrings() {
        String lang;
        if (Settings.language == Settings.GameLanguage.ZHS) {
            lang = "ZHS";
        } else {
            lang = "ENG";
        }
        BaseMod.loadCustomStringsFile(CardStrings.class, "wishdaleResources/localization/"+lang+"/cards.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, "wishdaleResources/localization/"+lang+"/characters.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, "wishdaleResources/localization/"+lang+"/relics.json");
        BaseMod.loadCustomStringsFile(PowerStrings.class, "wishdaleResources/localization/"+lang+"/powers.json");
        BaseMod.loadCustomStringsFile(UIStrings.class, "wishdaleResources/localization/"+lang+"/ui.json");

    }
    public void receiveEditRelics() {
        BaseMod.addRelicToCustomPool(new Wishdalebadge(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new AncestralLauncher(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new GoldBoneDice(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new RoaringHand(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new AncientTreeFruit(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new Ruozhitutou(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new HotWaterBottle(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new Guowangdexinqiang(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new Guowangdekaijia(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new Guowangdeyanshen(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new Zhuwangdeguanmian(), WISHDALE_RED);
        BaseMod.addRelicToCustomPool(new GuowangdeHujie(), WISHDALE_RED);

    }
    public void receiveAddAudio() {
        BaseMod.addAudio("BOOM", "wishdaleResources/audio/BOOM.mp3");
        BaseMod.addAudio("Tutou", "wishdaleResources/audio/Tutou.mp3");
    }

}
