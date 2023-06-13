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

	@Override
	public void receiveStartGame() {
		// doIt();
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
