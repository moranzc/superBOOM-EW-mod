package wishdalmod.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import wishdalmod.cards.Baolieliming;
import wishdalmod.cards.Shefu;

import java.util.ArrayList;


public class RandomizeDrawCardCostAction
        extends AbstractGameAction

{
    public void update() {
        for (AbstractCard c : DrawCardAction.drawnCards) {
            boolean randomize = true;
            for(String s:cardsExcept){
                if(s.equals(c.cardID)){
                    randomize = false;
                    break;
                }
            }
            if(c.cost>=0&&randomize){
                int newCost = AbstractDungeon.cardRandomRng.random(3);
                if (c.cost != newCost) {
                    c.cost = newCost;
                    c.costForTurn = c.cost;
                    c.isCostModified = true;
                }
            }
        }
        this.isDone = true;
    }
    private static final ArrayList<String> cardsExcept = new ArrayList<>();
    static {
        cardsExcept.add(Shefu.ID);
        cardsExcept.add(Baolieliming.ID);
    }
}
