package graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import shared.*;
import Mulligan.Mulligan;

/**
 * Entry point of the application.
 */
public class App extends JFrame {

    private static final int SCREEN_WIDTH = 1440;
    private static final int SCREEN_HEIGHT = 810;

    private InGameScreen inGameScreen;

    /**
     * Formal entry point of the application. Swing does not work in a static
     * context, so the main method just passes execution off to an instance of
     * the App class.
     */
    public static void main(String[] args) throws Exception {
        new App();
    }

    /**
     * The effective entrypoint of the application.
     */
     App() throws Exception {
         // Set some basic Swing settings
         setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
         setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
         setLocationRelativeTo(null);
         setVisible(true);
         setResizable(false);
         setFocusable(true);
         createBufferStrategy(2);
         setLayout(null);

         // Build the in-game screen
         inGameScreen = new InGameScreen(this);
         addMouseListener(inGameScreen);
         addMouseMotionListener(inGameScreen);

         // Start the main loop
         mainLoop();
    }

    /**
     * The main execution loop. Runs until the user quits the application.
     */
    private void mainLoop() throws IOException, FontFormatException {
        while (true) {
            draw();
        }
    }

    /**
     * Draws a single frame.
     */
    private void draw() throws IOException, FontFormatException {
        BufferStrategy bf = getBufferStrategy();
        Graphics2D g = (Graphics2D) bf.getDrawGraphics();

        // First clear the screen by drawing a screen-sized white rectangle
        g.setColor(new Color(255, 255, 255));
        g.fillRect(0, 0, SCREEN_WIDTH, SCREEN_HEIGHT);

        // Draw the in-game screen
        inGameScreen.draw(g, this);

        // Update the graphics on screen
        bf.show();
        getToolkit().sync();
    }

}
