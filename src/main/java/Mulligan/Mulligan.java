package Mulligan;

import java.util.Scanner;

import shared.*;


public class Mulligan
{
	private Deck deck = new Deck();
	private Card akroanJailer;
	private Card yokedOx;
	private Card plains;
	private Card celestialFlare;
	private Card guideHartebeest;
	private Card maritimeGuard;
	private Card island;
	private Card negate;
	private Card disperse;
	private static boolean done = false;

	public Mulligan() {
		makeCards();
		makeDeck();
	}
	
    public Deck getDeck() {
        return deck;
    }
	
	private void makeCards()
	{
		akroanJailer = new Card("398656", "Akroan Jailer", new int[]{1}, new String[]{"white"}, "Creature", 1, 1);
		yokedOx = new Card("398671", "Yoked Ox", new int[]{1}, new String[]{"white"}, "Creature", 0, 4);
		plains = new Card("420954", "Plains", "Land");
		celestialFlare = new Card("398488", "Celestial Flare", new int[]{2}, new String[]{"white"}, "Instant", "Target Player sacrifices an attacking or blocking creature.");
		guideHartebeest = new Card("398599", "Totem-Guide HarteBeest", new int[]{4,1}, new String[]{"any", "white"}, "Creature", 2, 5);
		maritimeGuard = new Card("398670", "Maritime Guard", new int[]{1,1}, new String[]{"any", "white"}, "Creature", 1, 3);
		island = new Card("420959", "Island", "Land");
		negate = new Card("416874", "Negate", new int[]{1, 1}, new String[]{"any", "blue"}, "Instant", "Counter target noncreature spell");
		disperse = new Card("413371", "Disperse", new int[]{1, 1}, new String[]{"any", "blue"}, "Instant", "Return target nonland permanent to its owner's hand.");
	}
	
	private void makeDeck()
	{
		// add 4 of each land
		for (int i = 0; i < 4; i++)
		{
			deck.addCard(plains);
			deck.addCard(island);	
		}
		
		// add 2 of all other cards
		for (int i = 0; i < 2; i++)
		{
			deck.addCard(akroanJailer);
			deck.addCard(yokedOx);
			deck.addCard(celestialFlare);
			deck.addCard(guideHartebeest);
			deck.addCard(maritimeGuard);
			deck.addCard(negate);
			deck.addCard(disperse);
		}

		deck.shuffle();
	}
	
	
	public static void main(String[] arg)
	{
		int maxCards = 7; // player starts with 7 cards before being able to mulligan
		Scanner reader = new Scanner(System.in);
		String input;
		
		Mulligan mulligan = new Mulligan();
		mulligan.makeCards();
		mulligan.makeDeck();
		Player player = new Player("Player", mulligan.deck);
		
		while (!done)
		{
			mulligan.deck.shuffle();
			player.setHand(maxCards);
			
			System.out.println("Your hand is: ");
			for (String card : player.outputHand())
			{
				System.out.println(card);
			}
			
			System.out.println("\nWould you like to mulligan your hand? (Y/n): ");
			input = reader.nextLine().toUpperCase();
			
			if (input.equals("Y"))
			{
				maxCards--;
			}
			else
			{
				done = true;
			}
			
			if (maxCards == 0)
			{
				done = true;
				System.out.println("You have run out of mulligans and will begin your turn with zero cards.");
			}
		}
		
		System.out.println("Your final hand is: ");
		for (String card : player.outputHand())
		{
			System.out.println(card);
		}
	}
}
