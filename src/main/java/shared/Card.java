package shared;

import java.util.Arrays;

public class Card
{
    protected String multiverseId;
    protected String name;
    protected int[] mana; // mana could have multiple types
    protected String[] manaType; // white, blue, black, red, green, *todo look at adding validation to this
    protected String type; // creature, Land, etc etc
    protected int power; // base power
    protected int toughness; // base toughness
    protected String ability;
    protected boolean tapped;

        /**
         * Sets the card values to the same as the given card.
         * @param card the card to copy from 
         */
        public Card(Card card) {
            multiverseId = card.multiverseId;
            name = card.name;
            mana = card.mana;
            manaType = card.manaType;
            type = card.type;
            power = card.power;
            toughness = card.toughness;
            ability = card.ability;
            tapped = card.tapped;
        }

    /**
     * Constructor for creatures with abilities
     */
    public Card(String mId, String n, int[] m, String[] mT, String t, int p, int tough, String a)
    {
                multiverseId = mId;
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
    public Card(String mId,String n, int[] m, String[] mT, String t, int p, int tough)
    {
                multiverseId = mId;
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
    public Card(String mId, String n, String t)
    {
                multiverseId = mId;
        name = n;
        type = t;
    }

    /**
     * Constructor for Instant, Sorcery, and Enchantment cards
     */
    public Card(String mId, String n, int[] m, String[] mT, String t, String a)
    {
                multiverseId = mId;
        name = n;
        mana = m;
        manaType = mT;
        type = t;
        ability = a;
    }

    public String getMultiverseId() {
                                  return multiverseId;
        }

    public int[] getMana() {
        return mana;
    }

    public String[] getManaType() {
        return manaType;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isTapped() {
        return tapped;
    }

    public void setTapped(boolean tapped) {
        this.tapped = tapped;
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
