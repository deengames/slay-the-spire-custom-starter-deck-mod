package com.deengames.slaythespire.customstarterdeck;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

import basemod.BaseMod;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;
import basemod.interfaces.PreDungeonUpdateSubscriber;
import basemod.interfaces.PreStartGameSubscriber;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class CustomStarterDeckMod implements ISubscriber, PostCreateStartingRelicsSubscriber, PreDungeonUpdateSubscriber
//PreStartGameSubscriber
//PostCreateStartingDeckSubscriber <--- really close, you pick character BUT too early to get current room
//PostCreateStartingRelicsSubscriber
//StartActSubscriber
{
	boolean HACKITY_HACK = false;

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
		ModHelper.setMods(list);
	}

	// Pilfered from NeowEvent.dailyBlessing
	private void doIt() {
		if (!HACKITY_HACK)
		{
			HACKITY_HACK = true;
			CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
			
			for (int i = 0; i < 30; i++) {
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

			AbstractDungeon.gridSelectScreen.open(sealedGroup, 1, CardCrawlGame.languagePack.getCharacterString("Neow Event").OPTIONS[4], false);
		}
	}

	@Override
	public void receivePreDungeonUpdate() {
		doIt();
	}
}
