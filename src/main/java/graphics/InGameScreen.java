package graphics;

import shared.Card;
import shared.Deck;
import Mulligan.Mulligan;

import javax.swing.*;
import javax.swing.text.GapContent;
import javax.xml.soap.Text;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.SampleModel;
import java.io.IOException;
import java.util.List;

/**
 * Manages components of the in-game screen.
 */
class InGameScreen implements MouseListener, MouseMotionListener {

    private enum State {
        MULLIGAN, PLAYING, OPPONENT_PLAYING, REMOVE_CARD
    }

    /**
     * Coordinates and size of the hand display section. This is where cards in
     * the user's hand will be shown.
     */
    private static final int HAND_START_X = 165;
    private static final int HAND_START_Y = 590;
    private static final int HAND_WIDTH = 1173;
    private static final int HAND_HEIGHT = 177;

    /**
     * Button coordinates.
     */
    private static final int BUTTON1_X = 36;
    private static final int BUTTON1_Y = 455;
    private static final int BUTTON2_X = 36;
    private static final int BUTTON2_Y = 400;

    /**
     * In-play area.
     */
    private static final Rectangle IN_PLAY_AREA = new Rectangle(165, 300, 1180, 238);

    /**
     * Opponent in-play area.
     */
    private static final Rectangle OPPONENT_IN_PLAY_AREA = new Rectangle(164, 22, 1180, 238);

    /**
     * The player who is using the program.
     */
    private Player user;

    /**
     * An opponent communicating to us from another instance of the program.
     */
    private Player opponent;

    /**
     * The state the game is in.
     */
    private State state = State.MULLIGAN;

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
     * Button the user presses to accept their turn.
     */
    private Button acceptTurn;

    /**
     * Button the user presses to skip the opponent's turn.
     */
    private Button skipOpponentTurn;

    /**
     * Last recorded mouse position.
     */
    private Point mousePos = new Point(0, 0);

    /**
     * Describes to the user what they need to do.
     */
    private String desc;

    /**
     * Constructs a new in-game screen.
     * @param parent the parent JFrame that we're displaying on
     */
    InGameScreen(JFrame parent) throws Exception {
        // Use the mulligan scenario to make prebuilt decks
        Mulligan m = new Mulligan();
        user = new Player("P1", m.getDeck());
        user.setHand(7);
        this.opponent = new Player("P2", new Deck(m.getDeck()));
        opponent.setHand(7);

        // Construct the mulligan button
        mulliganCards = new Button(BUTTON1_X, BUTTON1_Y, "Mulligan");
        mulliganCards.setActive(true);
        parent.addMouseListener(mulliganCards);
        mulliganCards.setRunnable(() -> {
            // The player wants to get a new hand of cards
            try {
                user.mulligan();
                if (user.getHand().size() == 0) {
                    mulliganCards.setActive(false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        // Construct the skip opponent turn button
        skipOpponentTurn = new Button(BUTTON1_X, BUTTON1_Y, "Continue");
        skipOpponentTurn.setActive(false);
        parent.addMouseListener(skipOpponentTurn);;
        skipOpponentTurn.setRunnable(() -> {
            // The player skips the opponent's turn
            resetButtons();
            state = State.PLAYING;
            desc = "It is your turn.";
            try {
                user.drawCard();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            resetUser();
            resetOpponent();
            acceptTurn.setActive(true);
        });

        // Construct the turn accept button
        acceptTurn = new Button(BUTTON2_X, BUTTON2_Y, "Accept");
        acceptTurn.setActive(false);
        parent.addMouseListener(acceptTurn);
        acceptTurn.setRunnable(() -> {
            // The player accepted their turn and is done
            resetButtons();
            if (user.getHand().size() <= 7) {
                state = State.OPPONENT_PLAYING;
                desc = "It is the\nopponent's turn.";
                playOpponent();
                skipOpponentTurn.setActive(true);
            } else {
                state = State.REMOVE_CARD;
                desc = "You must\nremove a card.";
            }
        });

        // Construct the accept button
        acceptCards = new Button(BUTTON2_X, BUTTON2_Y, "Accept");
        parent.addMouseListener(acceptCards);
        acceptCards.setRunnable(() -> {
            // The player accepted their hand and is ready to play
            resetButtons();
            acceptTurn.setActive(true);
            state = State.PLAYING;
            desc = "It is your turn.";
        });
        acceptCards.setActive(true);

        desc = "If this hand\nis not acceptable\nyou may\nmulligan.";

        // Load the background image
        background = Resources.getImage("resources/background.png");
    }

    private void playOpponent() {
        List<CardGfx> hand = opponent.getHand();
        for (CardGfx card : hand) {
            if (Rules.canPlay(opponent, card)) {
                hand.remove(card);
                List<CardGfx> inPlay = opponent.getInPlay();
                inPlay.add(card);
                break;
            }
        }
    }

    private void resetUser() {
        List<CardGfx> inPlay = user.getInPlay();
        for (CardGfx card : inPlay) {
            card.setTapped(false);
        }
        user.setPlayedLand(false);
        mulliganCards.setActive(false);
        acceptCards.setActive(false);
        acceptTurn.setActive(false);
        skipOpponentTurn.setActive(false);
    }

    private void resetOpponent() {
        List<CardGfx> inPlay = opponent.getInPlay();
        for (CardGfx card : inPlay) {
            card.setTapped(false);
        }
        opponent.setPlayedLand(false);
    }

    private void resetButtons() {
        mulliganCards.setActive(false);
        acceptCards.setActive(false);
        acceptTurn.setActive(false);
        skipOpponentTurn.setActive(false);
    }

    /**
     * Draws all in-game objects.
     * @param g the graphics to draw to
     * @param observer the observer, important for some reason
     */
    void draw(Graphics g, ImageObserver observer) throws IOException, FontFormatException {
        g.drawImage(background, 0, 0, observer);

        // Draw the user's hand
        List<CardGfx> userHand = user.getHand();
        for (int i=0; i<userHand.size(); i++) {
            if (userHand.get(i).isFloating()) {
                userHand.get(i).setX((int) mousePos.getX());
                userHand.get(i).setY((int) mousePos.getY());
            } else {
                int cardWidth = userHand.get(i).getWidth();
                int gap = (HAND_WIDTH % cardWidth) / userHand.size();
                userHand.get(i).setX(HAND_START_X + i * (cardWidth + gap));
                userHand.get(i).setY(HAND_START_Y);
            }

            userHand.get(i).draw(g, observer);
        }

        // Draw the in-play cards
        List<CardGfx> inPlay = user.getInPlay();
        for (int i=0; i<inPlay.size(); i++) {
            int cardWidth = inPlay.get(i).getWidth();
            int gap = ((int)IN_PLAY_AREA.getWidth() % cardWidth) / inPlay.size();
            inPlay.get(i).setX((int)IN_PLAY_AREA.getX() + i * (cardWidth + gap));
            inPlay.get(i).setY((int)IN_PLAY_AREA.getY());

            inPlay.get(i).draw(g, observer);
        }

        // Draw opponent in-play cards
        List<CardGfx> opponentInPlay = opponent.getInPlay();
        for (int i=0; i<opponentInPlay.size(); i++) {
            int cardWidth = opponentInPlay.get(i).getWidth();
            int gap = ((int)OPPONENT_IN_PLAY_AREA.getWidth() % cardWidth) / opponentInPlay.size();
            opponentInPlay.get(i).setX((int)OPPONENT_IN_PLAY_AREA.getX() + i * (cardWidth + gap));
            opponentInPlay.get(i).setY((int)OPPONENT_IN_PLAY_AREA.getY());

            opponentInPlay.get(i).draw(g, observer);
        }

        // Components for doing a mulligan
        mulliganCards.draw(g, observer);
        acceptCards.draw(g, observer);
        acceptTurn.draw(g, observer);
        skipOpponentTurn.draw(g, observer);

        g.setColor(Color.BLACK);
        g.setFont(Resources.getFont("resources/mrs-eaves-small-caps.ttf").deriveFont(16.0f));
        TextUtils.drawString(g, desc, 31, 300);
    }

    public void mousePressed(MouseEvent e) {
        if (state == State.PLAYING) {
            // Check if a card is being clicked
            for (CardGfx cardGfx : user.getHand()) {
                if (cardGfx.getRect().contains(e.getX(), e.getY())) {
                    cardGfx.setFloating(true);
                    break;
                }
            }
        }
    }

    public void mouseReleased(MouseEvent e) {
        if (state == State.PLAYING) {
            for (CardGfx cardGfx : user.getHand()) {
                if (cardGfx.isFloating()) {
                    cardGfx.setFloating(false);
                    if (IN_PLAY_AREA.contains(mousePos)) {
                        if (Rules.canPlay(user, cardGfx)) {
                            List<CardGfx> hand = user.getHand();
                            hand.remove(cardGfx);
                            List<CardGfx> inPlay = user.getInPlay();
                            inPlay.add(cardGfx);
                            cardGfx.setFloating(false);
                        }

                        break;
                    }
                }
            }
        } else if (state == State.REMOVE_CARD) {
            // The user needs to remove a card. The one they click is the one
            // they want to get rid of
            for (CardGfx cardGfx : user.getHand()) {
                if (cardGfx.getRect().contains(e.getX(), e.getY())) {
                    user.getHand().remove(cardGfx);
                    state = State.OPPONENT_PLAYING;
                    desc = "It is the\nopponent's turn.";
                    mulliganCards.setActive(false);
                    acceptCards.setActive(false);
                    acceptTurn.setActive(false);
                    skipOpponentTurn.setActive(false);
                    playOpponent();
                    resetUser();
                    resetOpponent();
                    skipOpponentTurn.setActive(true);
                    break;
                }
            }
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mouseMoved(MouseEvent e) {
        mousePos = new Point(e.getX(), e.getY());
    }

    public void mouseDragged(MouseEvent e) {
        mousePos = new Point(e.getX(), e.getY());
    }

}
