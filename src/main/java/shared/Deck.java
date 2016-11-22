package shared;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Deck
{
	private List<Card> deck;
	private int cardsUsed; // keeps track of cards taken from the deck (in play, graveyard, or in player's hand)

	public Deck()
	{
		deck = new ArrayList<Card>();
	}
	
	/**
	 * Add card to deck.
	 */
	public void addCard(Card card)
	{
		deck.add(card);
	}
	
	public void removeCard(Card card)
	{
		deck.remove(card);
		// *todo add exception handling to this?
	}
	
	/**
	 * Shuffle deck of cards.
	 */
    public void shuffle() 
    {
    	int change;
    	Card temp;
    	int n = deck.size();
    	Random random = new Random();
        random.nextInt();
        
        for (int i = 0; i < n; i++)
        {
        	change = i + random.nextInt(n - i);
            temp = deck.get(i);
            deck.set(i, deck.get(change));
            deck.set(change, temp);
        }
        cardsUsed = 0;
    }
	
    /**
     * Returns one card that has been "dealt" to player
     */
    public Card dealCard() 
    {
        if (cardsUsed == deck.size())
        {
            throw new IllegalStateException("Player has no more cards.");
        }
        
        cardsUsed++;
        return deck.get(cardsUsed - 1);
    }
    
    /**
     * Returns the amount of cards left in the player's deck.
     */
    public int remainingCards()
    {
    	return deck.size() - cardsUsed;
    }
}
