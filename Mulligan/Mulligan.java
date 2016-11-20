package Mulligan;

import java.util.Scanner;

import shared.*;


public class Mulligan
{
	private static Deck deck = new Deck();
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
	
	
	private void makeCards()
	{
		akroanJailer = new Card("Akroan Jailer", new int[]{1}, new String[]{"white"}, "Creature", 1, 1);
		yokedOx = new Card("Yoked Ox", new int[]{1}, new String[]{"white"}, "Creature", 0, 4);
		plains = new Card("Plains", "Land");
		celestialFlare = new Card("Celestial Flare", new int[]{2}, new String[]{"white"}, "Instant", "Target Player sacrifices an attacking or blocking creature.");
		guideHartebeest = new Card("Totem-Guide HarteBeest", new int[]{4,1}, new String[]{"any", "white"}, "Creature", 2, 5);
		maritimeGuard = new Card("Maritime Guard", new int[]{1,1}, new String[]{"any", "white"}, "Creature", 1, 3);
		island = new Card("Island", "Land");
		negate = new Card("Negate", new int[]{1, 1}, new String[]{"any", "blue"}, "Instant", "Counter target noncreature spell");
		disperse = new Card("Disperse", new int[]{1, 1}, new String[]{"any", "blue"}, "Instant", "Return target nonland permanent to its owner's hand.");
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
