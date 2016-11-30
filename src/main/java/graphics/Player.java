package graphics;

import shared.Deck;

import java.io.IOException;
import java.util.*;

public class Player {

    private String name;
    private List<CardGfx> hand;
    private List<CardGfx> inPlay;
    private Deck deck;
    private int health;
    private boolean playedLand;

    public Player(String n, Deck d) {
        name = n;
        deck = d;
        hand = new ArrayList<>();
        inPlay = new ArrayList<>();
        health = 20;
    }

    /**
     * Creates the player's hand based on how many mulligans the player has already taken.
     */
    public void setHand(int maxCards) throws IOException {
        hand = new ArrayList<>(maxCards);

        for (int i = 0; i < maxCards; i++) {
            hand.add(new CardGfx(deck.dealCard()));
        }
    }

    public void drawCard() throws IOException {
        hand.add(new CardGfx(deck.dealCard()));
    }

    /**
     * Mulligans the player's hand. This means that the player returns their
     * hand to the deck, shuffles the deck and draws one fewer cards.
     */
    public void mulligan() throws IOException {
        int currCards = hand.size();
        if (currCards == 0) {
            throw new RuntimeException("attempt to mulligan when player has no cards left");
        }

        deck.shuffle();
        setHand(currCards-1);
    }

    public void setPlayedLand(boolean playedLand) {
        this.playedLand = playedLand;
    }

    public boolean getPlayedLand() {
        return playedLand;
    }

    /**
     * Output hand in an string array consisting of each card.
     */
    public String[] outputHand() {
        String[] output = new String[hand.size()];

        for (int i = 0; i < hand.size(); i++) {
            output[i] = hand.get(i).outputCard();
        }

        return output;
    }

    /**
     * Get the player's current hand.
     */
    public List<CardGfx> getHand() {
        return hand;
    }

    /**
     * Get the cards the user has in play.
     */
    public List<CardGfx> getInPlay() {
        return inPlay;
    }
}
