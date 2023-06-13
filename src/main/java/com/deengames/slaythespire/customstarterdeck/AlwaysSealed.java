package com.deengames.slaythespire.customstarterdeck;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.helpers.ModHelper;

@SpirePatch(clz=ModHelper.class, method="isModEnabled")
public class AlwaysSealed {
    public static SpireReturn<Boolean> prefix(ModHelper _instance, String modID) {
        if (modID.equals(SealedDeck.ID)) {
            return SpireReturn.Return(true);
        } else {
            return SpireReturn.Continue();
        }
    }

    private AlwaysSealed() { }
}
