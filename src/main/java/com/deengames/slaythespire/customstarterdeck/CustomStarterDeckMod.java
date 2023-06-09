package com.deengames.slaythespire.customstarterdeck;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.daily.mods.Specialized;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

import basemod.BaseMod;
import basemod.interfaces.AddCustomModeModsSubscriber;
import basemod.interfaces.EditCardsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;
import basemod.interfaces.PostDungeonInitializeSubscriber;
import basemod.interfaces.PostDungeonUpdateSubscriber;
import basemod.interfaces.PostInitializeSubscriber;
import basemod.interfaces.PreDungeonUpdateSubscriber;
import basemod.interfaces.PreUpdateSubscriber;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class CustomStarterDeckMod implements ISubscriber, PostDungeonInitializeSubscriber, PostInitializeSubscriber
// PostCreateStartingRelicsSubscriber
{
	public static void initialize() {
        new CustomStarterDeckMod();
    }
	
	public CustomStarterDeckMod() {
		BaseMod.subscribe(this);
	}


	@Override
	public void receivePostInitialize() {
		List<String> list = new ArrayList<String>();
		list.add(SealedDeck.ID);
		ModHelper.setMods(list);
	}

	@Override
	public void receivePostDungeonInitialize() {
		List<String> list = new ArrayList<String>();
		list.add(Specialized.ID);
		list.add(SealedDeck.ID);
		ModHelper.setMods(list);
	}
}
