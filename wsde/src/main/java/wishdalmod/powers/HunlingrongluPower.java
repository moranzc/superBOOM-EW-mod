package wishdalmod.powers;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import wishdalmod.actions.RandomizeDrawCardStatsAction;
import wishdalmod.helpers.ModHelper;

import java.util.ArrayList;
import java.util.List;

public class HunlingrongluPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.makePath("HunlingrongluPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    private final List<AbstractCard> copiedCards = new ArrayList<>();

    public HunlingrongluPower(AbstractCreature owner, int amount, AbstractCard copiedCard) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.BUFF;

        this.copiedCards.add(copiedCard.makeStatEquivalentCopy());

        String path128 = "wishdaleResources/images/powers/Hunlingronglu84.png";
        String path48 = "wishdaleResources/images/powers/Hunlingronglu32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        updateDescription();
    }
    public void stackPower(int stackAmount, AbstractCard newCard) {
        if (!this.copiedCards.contains(newCard)) {
            this.copiedCards.add(newCard.makeStatEquivalentCopy());
        }
        this.amount = stackAmount;
        updateDescription();
    }

    public void atStartOfTurn() {
        flash();
        ArrayList<AbstractCard> generatedCards = new ArrayList<>();

        for (AbstractCard card : copiedCards) {
            for (int i = 0; i < this.amount; i++) {
                AbstractCard copy = card.makeStatEquivalentCopy();
                AbstractDungeon.player.hand.addToHand(copy);
                generatedCards.add(copy);
            }
        }

        this.addToBot(new RandomizeDrawCardStatsAction(generatedCards));
        this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }
    public void updateDescription() {
        StringBuilder desc = new StringBuilder(DESCRIPTIONS[0]).append(this.amount).append(" ");
        for (AbstractCard card : copiedCards) {
            desc.append(card.name).append(", ");
        }
        this.description = desc.substring(0, desc.length() - 2) + DESCRIPTIONS[1];
    }
}
