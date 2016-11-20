package shared;

import java.util.Arrays;

public class Card
{
	private String name;
	private int[] mana; // mana could have multiple types
	private String[] manaType; // white, blue, black, red, green, *todo look at adding validation to this
	private String type; // creature, Land, etc etc
	private int power; // base power
	private int toughness; // base toughness
	private String ability;
	
	/**
	 * Constructor for creatures with abilities
	 */
	public Card(String n, int[] m, String[] mT, String t, int p, int tough, String a)
	{
		name = n;
		mana = m;
		manaType = mT;
		type = t;
		power = p;
		toughness = tough;
		ability = a;
	}
	
	/**
	 * Construct for creatures with no abilities
	 */
	public Card(String n, int[] m, String[] mT, String t, int p, int tough)
	{
		name = n;
		mana = m;
		manaType = mT;
		type = t;
		power = p;
		toughness = tough;
	}
	
	/**
	 * Constructor for Land Cards
	 */
	public Card(String n, String t)
	{
		name = n;
		type = t;
	}
	
	/**
	 * Constructor for Instant, Sorcery, and Enchantment cards
	 */
	public Card(String n, int[] m, String[] mT, String t, String a)
	{
		name = n;
		mana = m;
		manaType = mT;
		type = t;
		ability = a;
	}
	
	/**
	 * Output the card into a readable string format.
	 */
	public String outputCard()
	{
		String output = "";
		
		switch (type)
		{
			case "Land":
					output += name + "(" + type + ")";
					break;
			case "Creature":
					output += name + " (mana:" + Arrays.toString(mana) + Arrays.toString(manaType) + ", Type: " + type + ", P/T: " + power + "/" + toughness;
					if (ability != null)
					{
						output += ", Ability: " + ability;
					}
					output += ")";
					break;
			case "Instant":
			case "Sorcery":
			case "Enchantment":
			default:
				output += name + " (mana:" + Arrays.toString(mana) + Arrays.toString(manaType) + ", Type: " + type + ", Ability: " + ability + ")";				
		}
		return output;
	}
}
