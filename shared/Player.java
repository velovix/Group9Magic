package shared;

public class Player
{
	private String name;
	private Card[] hand;
	private Deck deck;
	
	public Player(String n, Deck d)
	{
		name = n;
		deck = d;
	}
	
	/**
	 * Creates the player's hand based on how many mulligans the player has already taken.
	 */
	public void setHand(int maxCards)
	{
		hand = new Card[maxCards];
		
		for (int i = 0; i < maxCards; i++)
		{
			hand[i] = deck.dealCard();
		}
	}
	
	/**
	 * Output hand in an string array consisting of each card.
	 */
	public String[] outputHand()
	{
		String[] output = new String[hand.length];
		
		for (int i = 0; i < hand.length; i++)
		{
			output[i] = hand[i].outputCard();
		}
		
		return output;
	}
	
	/**
	 * Get the player's current hand.
	 */
	public Card[] getHand()
	{
		return hand;
	}
}
