package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;

/**
 * 用于战斗中临时增加技能牌的费用。
 */
public class JinengjiafeiAction extends AbstractGameAction {
    private final AbstractCard card;
    private final int costIncrease;

    public JinengjiafeiAction(AbstractCard card, int costIncrease) {
        this.card = card;
        this.costIncrease = costIncrease;
    }

    @Override
    public void update() {
        if (card != null) {
            // 增加原始费用
            card.cost += costIncrease;
            if (card.cost < 0) card.cost = 0; // 防止负数
            // 增加本回合费用
            card.costForTurn = card.cost;
            card.isCostModified = true;
        }
        this.isDone = true;
    }
}
