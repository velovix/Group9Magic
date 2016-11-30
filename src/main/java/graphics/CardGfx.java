package graphics;

class CardGfx extends Card {

    private BufferedImage image;
    private Rectangle rect;

    private int x;
    private int y;

    private boolean floating;

    public CardGfx(Card card) {
        super(card);

        image = Resources.getCardImage(multiverseId);
        rect = new Rectangle(x, y, getWidth(), getHeight());
    }

    public void setX(int x) {
        this.x = x;
        rect = new Rectangle(x, y, getWidth(), getHeight());
    }

    public void setY(int y) {
        this.y = y;
        rect = new Rectangle(x, y, getWidth(), getHeight());
    }

    public void setFloating(boolean floating) {
        this.floating = floating;
    }

    public boolean isFloating() {
        return floating;
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public void draw(Graphics g, ImageObserver observer) {
        g.drawImage(image, x, y, observer);
    }

}
