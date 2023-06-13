import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.neow.NeowEvent;
import com.megacrit.cardcrawl.neow.NeowRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

@SpirePatch(clz=NeowRoom.class, method="onPlayerEntry")
public class ShowSealedDeck {
	
	private final static int CARDS_IN_DECK = 10;
	private final static int CARDS_TO_PICK_FROM = 30;

	@SpirePostfixPatch
	// Pilfered from NeowEvent.dailyBlessing
	private static void onPlayerEntry(NeowRoom __instance) 
	{
		String[] options = CardCrawlGame.languagePack.getCharacterString("Neow Event").OPTIONS;
		CardGroup sealedGroup = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);

		generatePoolOfCards(sealedGroup);

		// NeowEvent event = (NeowEvent) AbstractDungeon.getCurrRoom().event;
		// event.roomEventText.clearRemainingOptions();
		// event.roomEventText.updateDialogOption(0, options[3]);

		promptPlayerToPickCards(sealedGroup, options[4]);
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
}