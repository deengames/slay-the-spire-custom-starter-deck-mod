package com.deengames.slaythespire.customstarterdeck;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class CustomStarterDeckPatches {

    private static boolean isDone = false;

    // @SpirePatch(clz=ModHelper.class, method="isModEnabled")
    // public static class StackedDeckIsAlwaysEnabled {
    //     @SpirePrefixPatch
    //     public static SpireReturn<Boolean> Prefix(ModHelper instance, String modID) {
    //         System.out.println("**** checking: " + modID);

    //         if (modID.equals(SealedDeck.ID)) {
    //             System.out.println("***************************** spire return true");
    //             return SpireReturn.Return(true);
    //         }

    //         return SpireReturn.Continue();
    //     }
    // }

	// private static final Random random = new Random();

	private final static int CARDS_IN_DECK = 10;
	private final static int CARDS_TO_PICK_FROM = 30;

    // @SpirePatch(clz=NeowEvent.class, method="playSfx")
    // public static class ShowDeckAfterNeowRoom {
    //     public static void Postfix(NeowEvent __instance) {
    //         if (!isDone) {
    //             isDone = true;
    //             System.out.println("***************** meow");
    //             CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
    //             generatePoolOfCards(sealedGroup);
    //             promptPlayerToPickCards(sealedGroup, "Pick " + CARDS_IN_DECK + " cards for your starting deck.");
    //         }
    //     }
    // }

    @SpirePatch(clz=NeowEvent.class, method="talk")
    public static class ShowDeckAfterTalk {
        @SpirePostfixPatch
        public static void Postfix(NeowEvent instance) {
            if (!isDone) {
                isDone = true;
                System.out.println("***************** talkity talk");
                NeowEvent.rng = new Random(Settings.seed);
                CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                generatePoolOfCards(sealedGroup);
                promptPlayerToPickCards(sealedGroup, "Pick " + CARDS_IN_DECK + " cards for your starting deck.");
            }
        }
    }


    private static void generatePoolOfCards(CardGroup sealedGroup)
	{
		// Pick 30ish random cards for the player to pick from
        for (int i = 0; i < CARDS_TO_PICK_FROM; i++) {
            AbstractCard card = AbstractDungeon.getCard(AbstractDungeon.rollRarity());
            if (!sealedGroup.contains(card)) {
                sealedGroup.addToBottom(card.makeCopy());
            } else {
                i--;
            } 
        }
		
		for (AbstractCard c : sealedGroup.group)
		{
			UnlockTracker.markCardAsSeen(c.cardID); 
		}
	}

	private static void promptPlayerToPickCards(CardGroup sealedGroup, String label)
	{
		AbstractDungeon.gridSelectScreen.open(sealedGroup, CARDS_IN_DECK, label, false);
	}

    // private static AbstractCard.CardRarity getCardRarityFallback()
	// {
	// 	int roll = random.random(99);
	// 	int rareRate = 3;
	// 	if (roll < rareRate)
	// 		return AbstractCard.CardRarity.RARE;
	// 	if (roll < 40)
	// 		return AbstractCard.CardRarity.UNCOMMON;
	// 	return AbstractCard.CardRarity.COMMON;
	// }
}
