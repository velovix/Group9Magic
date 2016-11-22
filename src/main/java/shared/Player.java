package shared;

import java.util.*;

public class Player
{
    private String name;
    private List<Card> hand;
    private Deck deck;
    private int health;

    public Player(String n, Deck d)
    {
        name = n;
        deck = d;
        hand = new ArrayList<>();
        health = 20;
    }

    /**
     * Creates the player's hand based on how many mulligans the player has already taken.
     */
    public void setHand(int maxCards)
    {
        hand = new ArrayList<>(maxCards);

        for (int i = 0; i < maxCards; i++)
        {
            hand.add(deck.dealCard());
        }
    }

    /**
     * Mulligans the player's hand. This means that the player returns their
     * hand to the deck, shuffles the deck and draws one fewer cards.
     */
    public void mulligan() {
        int currCards = hand.size();
        if (currCards == 0) {
            throw new RuntimeException("attempt to mulligan when player has no cards left");
        }

        deck.shuffle();
        setHand(currCards-1);
    }

    /**
     * Output hand in an string array consisting of each card.
     */
    public String[] outputHand()
    {
        String[] output = new String[hand.size()];

        for (int i = 0; i < hand.size(); i++)
        {
            output[i] = hand.get(i).outputCard();
        }

        return output;
    }

    /**
     * Get the player's current hand.
     */
    public List<Card> getHand()
    {
        return hand;
    }
}
