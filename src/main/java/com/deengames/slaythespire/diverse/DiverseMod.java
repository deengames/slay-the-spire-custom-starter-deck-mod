package com.deengames.slaythespire.diverse;

import com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass;
import com.megacrit.cardcrawl.daily.mods.Diverse;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.screens.custom.CustomMod;

import basemod.BaseMod;
import basemod.interfaces.AddCustomModeModsSubscriber;
import basemod.interfaces.ISubscriber;
import basemod.interfaces.PostCreateStartingRelicsSubscriber;
import basemod.interfaces.StartActSubscriber;
import basemod.interfaces.StartGameSubscriber;

import java.util.ArrayList;
import java.util.List;

import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;

@SpireInitializer
public class DiverseMod implements ISubscriber, PostCreateStartingRelicsSubscriber
{
	public static void initialize() {
        new DiverseMod();
    }
	
	public DiverseMod() {
		BaseMod.subscribe(this);
	}

	@Override
	public void receivePostCreateStartingRelics(PlayerClass arg0, ArrayList<String> arg1) {
		List<String> list = new ArrayList<String>();
		list.add(Diverse.ID); // rewards are all colours
		list.add(Insanity.ID); // start with random all-colour deck
		ModHelper.setMods(list);
	}
}
