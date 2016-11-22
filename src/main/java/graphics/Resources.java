package graphics;

import java.util.*;
import java.nio.file.*;
import java.awt.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.ImageIO;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.*;

class Resources {

    private static final double CARD_SCALE_FACTOR = 0.5;

    private static Map<String, BufferedImage> cardImageCache;
    private static Map<String, Font> fontCache;

    static {
        cardImageCache = new HashMap<>();
        fontCache = new HashMap<>();
    }

    /**
     * Loads the image found at the given file path.
     * @param regPath the path to look for the image at
     * @return loaded image
     */
    static BufferedImage getImage(String regPath) throws IOException {
        InputStream image = Files.newInputStream(Paths.get(regPath));
        return ImageIO.read(image);
    }

    /**
     * Loads the card image by its multiverse ID from the Wizards of the Coast
     * site.
     * @param multiverseID the id to find the card with
     * @return loaded image
     */
    static BufferedImage getCardImage(String multiverseId) throws IOException {
        // Don't ask Wizards for the image if we have already loaded it
        if (!cardImageCache.containsKey(multiverseId)) {
            HttpClient client = HttpClients.createDefault();

            // Ask the site for the image
            HttpUriRequest request = RequestBuilder.get()
                .setUri("http://gatherer.wizards.com/Handlers/image.ashx?multiverseId="
                        + multiverseId
                        + "&type=card")
                .build();
            HttpResponse response = client.execute(request);

            // The image is a bit large, so we'll scale it down a bit
            BufferedImage image = ImageIO.read(response.getEntity().getContent());
            image = ImageUtils.scale(image, CARD_SCALE_FACTOR, CARD_SCALE_FACTOR);

            // Save the image to the cache
            cardImageCache.put(multiverseId, image);
        }

        return cardImageCache.get(multiverseId);
    }

    static Font getFont(String regPath) throws IOException, FontFormatException {
        if (!fontCache.containsKey(regPath)) {
            InputStream font = Files.newInputStream(Paths.get(regPath));
            fontCache.put(regPath, Font.createFont(Font.TRUETYPE_FONT, font));
        }

        return fontCache.get(regPath);
    }

}
