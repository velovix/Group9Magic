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

class InGameScreen {

    private static final int HAND_START_X = 165;
    private static final int HAND_START_Y = 590;
    private static final int HAND_WIDTH = 1173;
    private static final int HAND_HEIGHT = 177;

    private Player user;
    private Player opponent;

    private Image background;

    private Button mulliganCards;
    private Button acceptCards;

    InGameScreen(JFrame parent) throws Exception {
        // Use the mulligan scenario to make prebuilt decks
        Mulligan m = new Mulligan();
        user = new Player("P1", m.getDeck());
        user.setHand(7);
        this.opponent = new Player("P2", new Deck());

        // Construct buttons
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

    void draw(Graphics g, ImageObserver observer) throws IOException {
        g.drawImage(background, 0, 0, observer);

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

        mulliganCards.draw(g, observer);
        acceptCards.draw(g, observer);
    }

}
