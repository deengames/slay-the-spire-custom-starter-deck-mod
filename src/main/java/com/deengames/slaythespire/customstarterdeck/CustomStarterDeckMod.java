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
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;
import basemod.interfaces.PreDungeonUpdateSubscriber;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class CustomStarterDeckMod implements ISubscriber, PostCreateStartingRelicsSubscriber, PreDungeonUpdateSubscriber
{

	private final int CARDS_TO_PICK_FROM = 30;
	private final int CARDS_IN_DECK = 10;

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
	private void doIt() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String[] options = CardCrawlGame.languagePack.getCharacterString("Neow Event").OPTIONS;
		CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

		generatePoolOfCards(sealedGroup);

		NeowEvent event = (NeowEvent) AbstractDungeon.getCurrRoom().event;
		event.roomEventText.clearRemainingOptions();
		event.roomEventText.updateDialogOption(0, options[3]);

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
		AbstractDungeon.gridSelectScreen.open(sealedGroup, 1, label, false);
	}

	@Override
	public void receivePreDungeonUpdate() {
		try {
			doIt();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
