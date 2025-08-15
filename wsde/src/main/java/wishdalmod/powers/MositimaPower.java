package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.helpers.texiao.MositimaTimeEffect;

import java.util.HashMap;
import java.util.Map;

public class MositimaPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("MositimaPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private MositimaTimeEffect timeEffect;
    private boolean effectAdded = false;

    // 储存多个延迟伤害任务，每个元素是一个长度为2的数组：{剩余回合数, 伤害值}
    private final Map<Integer, Integer> delayMap = new HashMap<>();

    public MositimaPower(AbstractCreature owner) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.BUFF;
        this.isTurnBased = true;
        String path128 = "wishdaleResources/images/powers/MositimaPower84.png";
        String path48 = "wishdaleResources/images/powers/MositimaPower32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void onInitialApplication() {
        // 创建并添加特效
        timeEffect = new MositimaTimeEffect(owner);
        AbstractDungeon.effectList.add(timeEffect);
        effectAdded = true;
    }

    @Override
    public void stackPower(int stackAmount) {
        super.stackPower(stackAmount);
        // 如果堆叠时特效尚未添加，添加它
        if (!effectAdded) {
            timeEffect = new MositimaTimeEffect(owner);
            AbstractDungeon.effectList.add(timeEffect);
            effectAdded = true;
        }
    }

    @Override
    public void update(int slot) {
        super.update(slot);
        // 更新特效状态
        if (timeEffect != null && !timeEffect.isDone) {
            timeEffect.updatePowerStatus();
        }
    }

    @Override
    public void onRemove() {
        // 移除特效
        if (timeEffect != null) {
            timeEffect.isDone = true;
            timeEffect = null;
        }
    }

    @Override
    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            delayMap.merge(2, damageAmount, Integer::sum);
            updateDescription();
        }
        return 0;
    }

    @Override
    public void atEndOfRound() {
        Map<Integer, Integer> newMap = new HashMap<>();

        for (Map.Entry<Integer, Integer> entry : delayMap.entrySet()) {
            int newCountdown = entry.getKey() - 1;
            int damage = entry.getValue();
            if (newCountdown <= 0) {
                // 结算伤害
                addToBot(new LoseHPAction(owner, owner, damage));
            } else {
                // 延迟一回合
                newMap.merge(newCountdown, damage, Integer::sum);
            }
        }

        delayMap.clear();
        delayMap.putAll(newMap);

        if (delayMap.isEmpty()) {
            addToBot(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }

        updateDescription();
    }

    @Override
    public void updateDescription() {
        if (delayMap.isEmpty()) {
            this.description = DESCRIPTIONS[1];
        } else {
            StringBuilder sb = new StringBuilder(DESCRIPTIONS[0]);
            delayMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByKey())
                    .forEach(entry -> sb.append(String.format(" %d回合后受到%d点伤害；", entry.getKey(), entry.getValue())));
            this.description = sb.toString();
        }
    }
}