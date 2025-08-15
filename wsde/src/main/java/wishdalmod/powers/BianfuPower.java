package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.helpers.ModHelper;
import wishdalmod.monsters.Kongkazi;

public class BianfuPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("BianfuPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public AbstractMonster source;

    private float bianfuDeathX = -1;
    private float bianfuDeathY = -1;

    public BianfuPower(AbstractCreature owner, AbstractMonster source) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.type = PowerType.DEBUFF;
        this.source = source;
        this.amount = 1;
        this.isTurnBased = false;

        String path128 = "wishdaleResources/images/powers/Bianfu84.png";
        String path48 = "wishdaleResources/images/powers/Bianfu32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atEndOfTurn(boolean isPlayer) {
        if (isPlayer) {
            addToBot(new DamageAction(owner, new DamageInfo(owner, 12, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.FIRE));
            addToBot(new LoseHPAction(owner, owner, 2));
        }
    }

    @Override
    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount <= 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }
        updateDescription();
    }

    @Override
    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        int previousAmount = this.amount;
        this.amount -= reduceAmount;

        // 实际减少的层数
        int actualReduction = Math.min(reduceAmount, previousAmount);

        if (actualReduction > 0 && canTriggerEffect()) {
            triggerEffect(actualReduction);
        }

        if (this.amount <= 0) {
            addToTop(new RemoveSpecificPowerAction(owner, owner, this.ID));
        }
        updateDescription();
    }

    @Override
    public void onRemove() {
        if (this.amount > 0 && canTriggerEffect()) {
            triggerEffect(this.amount);
        }
    }

    /**
     * 判断是否可以触发恐卡兹和年代印痕
     */
    private boolean canTriggerEffect() {
        // 战斗已结束则不触发
        if (AbstractDungeon.getCurrRoom().isBattleEnding()) {
            return false;
        }
        // 计算存活怪物数量
        long aliveCount = AbstractDungeon.getMonsters().monsters.stream()
                .filter(m -> !m.isDeadOrEscaped())
                .count();
        // 如果只剩下一个怪物（便符本身），不触发
        if (aliveCount <= 1 && source != null && !source.isDeadOrEscaped()) {
            return false;
        }
        return true;
    }

    /**
     * 触发效果：添加年代印痕并召唤恐卡兹
     */
    private void triggerEffect(int times) {
        for (int i = 0; i < times; i++) {
            // 给玩家添加年代印痕（新版构造函数）
            addToBot(new ApplyPowerAction(owner, owner, new NiandaiyinhenPower(owner)));

            // 生成恐卡兹位置
            float spawnX, spawnY;
            if (bianfuDeathX > 0 && bianfuDeathY > 0) {
                spawnX = bianfuDeathX;
                spawnY = bianfuDeathY;
            } else {
                spawnX = Settings.WIDTH / 2f;
                spawnY = Settings.HEIGHT * 0.3f;
            }

            // 添加随机偏移防止重叠
            spawnX += MathUtils.random(-50f, 50f);
            spawnY += MathUtils.random(-20f, 20f);

            // 限制范围
            float minX = Settings.WIDTH * 0.1f;
            float maxX = Settings.WIDTH * 0.9f;
            float minY = Settings.HEIGHT * 0.2f;
            float maxY = Settings.HEIGHT * 0.8f;
            spawnX = MathUtils.clamp(spawnX, minX, maxX);
            spawnY = MathUtils.clamp(spawnY, minY, maxY);

            // 召唤恐卡兹
            Kongkazi kongkazi = new Kongkazi(spawnX, spawnY);
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(kongkazi, true));
        }
    }

    public void setBianfuDeathPos(float x, float y) {
        float minX = Settings.WIDTH * 0.1f;
        float maxX = Settings.WIDTH * 0.9f;
        float minY = Settings.HEIGHT * 0.2f;
        float maxY = Settings.HEIGHT * 0.8f;
        this.bianfuDeathX = MathUtils.clamp(x, minX, maxX);
        this.bianfuDeathY = MathUtils.clamp(y, minY, maxY);
    }

    @Override
    public boolean canPlayCard(AbstractCard card) {
        return card.type != AbstractCard.CardType.SKILL;
    }

    @Override
    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (type == DamageInfo.DamageType.NORMAL) {
            return damage * 1.5f;
        }
        return damage;
    }

    @Override
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount;
    }
}
