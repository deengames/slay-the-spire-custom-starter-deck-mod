package com.deengames.slaythespire.customstarterdeck;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.random.Random;

@SpirePatch(clz = com.megacrit.cardcrawl.neow.NeowEvent.class, method = SpirePatch.CONSTRUCTOR, paramtypez = boolean.class)
public class NeowEventPatches {
    @SpirePostfixPatch
    public static void Postfix(NeowEvent event, boolean isDone) {
    }
}