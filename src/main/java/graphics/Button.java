package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;

/**
 * A button. If the user clicks it, something probably happens.
 */
class Button implements MouseListener {

    private static final float DEFAULT_FONT_SIZE = 16.0f;

    private int x, y;
    private boolean active;

    private BufferedImage bg;
    private BufferedImage image;
    private Rectangle bounds;

    private Runnable runnable;

    /**
     * Constructs a button at the given points with the given contents.
     * @param x x position
     * @param y y position
     * @param text text to put on the button
     */
    Button(int x, int y, String text) throws IOException, FontFormatException {
        this.x = x;
        this.y = y;

        bg = Resources.getImage("resources/button.png");
        bounds = new Rectangle(x, y, bg.getWidth(), bg.getHeight());

        // Create a JLabel that will be used to construct the button
        JLabel label = new JLabel(new ImageIcon(bg));
        label.setText(text);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.CENTER);
        label.setSize(bg.getWidth(), bg.getHeight());
        label.setFont(Resources.getFont("resources/mrs-eaves-small-caps.ttf").deriveFont(DEFAULT_FONT_SIZE));
        label.setForeground(Color.white);

        // Assemble the button
        image = new BufferedImage(bg.getWidth(), bg.getHeight(), BufferedImage.TYPE_INT_ARGB);
        // Get a Graphics2D from the button background so we can draw the text
        // on top of it
        Graphics2D g = image.createGraphics();
        //g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        label.paint(g);
    }

    /**
     * Sets the action that will be run when the button is pressed to the given
     * runnable.
     * @param runnable the action to run when button is pressed
     */
    void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }

    /**
     * Sets whether or not the button is active. An inactive button is
     * invisible and cannot be clicked.
     * @param active whether or not the button is active
     */
    void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Draws the button on screen.
     * @param g graphics to draw on
     * @param observer important for some reason
     */
    void draw(Graphics g, ImageObserver observer) {
        if (active) {
            g.drawImage(image, x, y, observer);
        }
    }

    /**
     * Runs when the user clicks the mouse button.
     * @param e event info
     */
    public void mouseClicked(MouseEvent e) {
        // Run the action if the user clicks within the button with the left
        // mouse button
        if (active && e.getButton() == MouseEvent.BUTTON1 && bounds.contains(e.getPoint())) {
            if (runnable != null) {
                runnable.run();
            }
        }
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }
}
