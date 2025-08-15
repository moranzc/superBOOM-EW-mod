package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;

import java.util.ArrayList;

public class NiandaiyinhenPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("NiandaiyinhenPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public NiandaiyinhenPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.amount = 1; // 固定值，不叠加
        this.isTurnBased = false;

        String path128 = "wishdaleResources/images/powers/Niandaiyinhen84.png";
        String path48 = "wishdaleResources/images/powers/Niandaiyinhen32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        updateDescription();
    }

    // 禁止叠加
    @Override
    public void stackPower(int stackAmount) {
        // 什么也不做，让它无法叠加
    }

    // 添加音效（同混乱）
    @Override
    public void playApplyPowerSfx() {
        CardCrawlGame.sound.play("POWER_CONFUSION", 0.05F);
    }

    // 在抽牌时处理
    @Override
    public void onCardDraw(AbstractCard card) {
        // 收集当前手牌中新抽到的技能牌
        ArrayList<AbstractCard> skillCards = new ArrayList<>();
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c.type == AbstractCard.CardType.SKILL && c.cost >= 0) {
                skillCards.add(c);
            }
        }

        if (!skillCards.isEmpty()) {
            // 随机挑一张技能牌
            AbstractCard chosen = skillCards.get(AbstractDungeon.cardRandomRng.random(skillCards.size() - 1));
            chosen.cost += 1;
            chosen.costForTurn = chosen.cost;
            chosen.isCostModified = true;
        }
    }

    @Override
    public void updateDescription() {
        // 描述可写成“你每次抽牌时，随机一张技能牌的费用增加1”
        this.description = DESCRIPTIONS[0];
    }
}
