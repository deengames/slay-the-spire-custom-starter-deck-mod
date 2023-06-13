package com.deengames.slaythespire.customstarterdeck;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;
import basemod.interfaces.StartGameSubscriber;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class CustomStarterDeckMod implements ISubscriber, PostCreateStartingRelicsSubscriber, StartGameSubscriber
{

	private final int CARDS_TO_PICK_FROM = 30;
	private final int CARDS_IN_DECK = 10;

	// private static final Random random = new Random();

	public static void initialize() {
        new CustomStarterDeckMod();
    }
	
	public CustomStarterDeckMod() {
		BaseMod.subscribe(this);
	}

	@Override
	public void receivePostCreateStartingRelics(PlayerClass arg0, ArrayList<String> arg1) {
		// Set custom mode, we need it later
		List<String> list = new ArrayList<String>();
		list.add(SealedDeck.ID);
		list.add(Insanity.ID);
		ModHelper.setMods(list);
	}

	// Pilfered from NeowEvent.dailyBlessing
	private void doIt() 
	{
		String[] options = CardCrawlGame.languagePack.getCharacterString("Neow Event").OPTIONS;
		CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

		generatePoolOfCards(sealedGroup);

		// NeowEvent event = (NeowEvent) AbstractDungeon.getCurrRoom().event;
		// event.roomEventText.clearRemainingOptions();
		// event.roomEventText.updateDialogOption(0, options[3]);

		promptPlayerToPickCards(sealedGroup, options[4]);
	}

	private void generatePoolOfCards(CardGroup sealedGroup)
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

	private void promptPlayerToPickCards(CardGroup sealedGroup, String label)
	{
		AbstractDungeon.gridSelectScreen.open(sealedGroup, CARDS_IN_DECK, label, false);
	}

	@Override
	public void receiveStartGame() {
		doIt();
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
