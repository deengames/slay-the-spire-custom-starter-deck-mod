package com.deengames.slaythespire.customstarterdeck;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch(clz=ModHelper.class, method="isModEnabled")

public class AlwaysSealed {
    public static SpireReturn<Boolean> prefix(ModHelper _instance, String modID) {
        if (modID.equals(SealedDeck.ID)) {
            return SpireReturn.Return(true);
        } else {
            return SpireReturn.Continue();
        }
    }
}
