package graphics;

import shared.Card;
import shared.Deck;
import shared.Player;
import Mulligan.Mulligan;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.List;

/**
 * Manages components of the in-game screen.
 */
class InGameScreen {

    /**
     * Coordinates and size of the hand display section. This is where cards in
     * the user's hand will be shown.
     */
    private static final int HAND_START_X = 165;
    private static final int HAND_START_Y = 590;
    private static final int HAND_WIDTH = 1173;
    private static final int HAND_HEIGHT = 177;

    /**
     * The player who is using the program.
     */
    private Player user;

    /**
     * An opponent communicating to us from another instance of the program.
     */
    private Player opponent;

    /**
     * The background image of the board. Contains unmoving components.
     */
    private BufferedImage background;

    /**
     * Button the user presses to mulligan their hand.
     */
    private Button mulliganCards;

    /**
     * Button the user presses to accept their hand.
     */
    private Button acceptCards;

    /**
     * Constructs a new in-game screen.
     * @param parent the parent JFrame that we're displaying on
     */
    InGameScreen(JFrame parent) throws Exception {
        // Use the mulligan scenario to make prebuilt decks
        Mulligan m = new Mulligan();
        user = new Player("P1", m.getDeck());
        user.setHand(7);
        this.opponent = new Player("P2", new Deck());

        // Construct the mulligan button
        mulliganCards = new Button(36, 455, "Mulligan");
        mulliganCards.setActive(true);
        parent.addMouseListener(mulliganCards);
        mulliganCards.setRunnable(() -> {
            // The player wants to get a new hand of cards
            user.mulligan();
            if (user.getHand().size() == 0) {
                mulliganCards.setActive(false);
            }
        });

        // Construct the accept button
        acceptCards = new Button(36, 400, "Accept");
        parent.addMouseListener(acceptCards);
        acceptCards.setRunnable(() -> {
            // The player accepted their hand and is ready to play
            acceptCards.setActive(false);
            mulliganCards.setActive(false);
        });
        acceptCards.setActive(true);

        // Load the background image
        background = Resources.getImage("resources/background.png");
    }

    /**
     * Draws all in-game objects.
     * @param g the graphics to draw to
     * @param observer the observer, important for some reason
     */
    void draw(Graphics g, ImageObserver observer) throws IOException {
        g.drawImage(background, 0, 0, observer);

        // Draw the user's hand
        List<Card> userHand = user.getHand();
        for (int i=0; i<userHand.size(); i++) {
            BufferedImage image = Resources.getCardImage(userHand.get(i).getMultiverseId());
            int cardWidth = image.getWidth();
            int gap = (HAND_WIDTH % cardWidth) / userHand.size();
            
            g.drawImage(image,
                    HAND_START_X + i * (cardWidth + gap),
                    HAND_START_Y,
                    observer);
        }

        // Components for doing a mulligan
        mulliganCards.draw(g, observer);
        acceptCards.draw(g, observer);
    }

}
