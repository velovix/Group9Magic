package graphics;

import shared.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides rules functionality.
 */
public class Rules {

    public static boolean canPlay(Player player, Card card) {
        int blueMana = 0;
        int whiteMana = 0;

        List<Card> willTap = new ArrayList<>();

        // Tally up every land card in play
        for (CardGfx inPlayCard : player.getInPlay()) {
            if (!inPlayCard.isTapped()) {
                if (inPlayCard.getType().equals("Land")) {
                    if (inPlayCard.getName().equals("Plains")) {
                        whiteMana++;
                    } else if (inPlayCard.getName().equals("Island")) {
                        blueMana++;
                    }
                }
            }
        }

        int[] manaCnt = card.getMana();
        String[] manaTypes = card.getManaType();

        if (manaCnt != null && manaTypes != null) {
            for (int i = 0; i < manaTypes.length; i++) {
                if (manaTypes[i].equals("blue")) {
                    if (manaCnt[i] <= blueMana) {
                        int tapped = 0;
                        for (CardGfx inPlayCard : player.getInPlay()) {
                            if (!inPlayCard.isTapped() && !willTap.contains(inPlayCard) && inPlayCard.getType().equals("Land") && inPlayCard.getName().equals("Island")) {
                                willTap.add(inPlayCard);
                                tapped++;
                                if (tapped == manaCnt[i]) {
                                    blueMana -= tapped;
                                    break;
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                } else if (manaTypes[i].equals("white")) {
                    if (manaCnt[i] <= whiteMana) {
                        int tapped = 0;
                        for (CardGfx inPlayCard : player.getInPlay()) {
                            if (!inPlayCard.isTapped() && !willTap.contains(inPlayCard) && inPlayCard.getType().equals("Land") && inPlayCard.getName().equals("Plains")) {
                                willTap.add(inPlayCard);
                                tapped++;
                                if (tapped == manaCnt[i]) {
                                    whiteMana -= tapped;
                                    break;
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                } else if (manaTypes[i].equals("any")) {
                    if (manaCnt[i] <= whiteMana + blueMana) {
                        int blueTapped = 0;
                        int whiteTapped = 0;
                        for (CardGfx inPlayCard : player.getInPlay()) {
                            if (!inPlayCard.isTapped() && !willTap.contains(inPlayCard) && inPlayCard.getType().equals("Land")) {
                                if (inPlayCard.getName().equals("Island")) {
                                    blueTapped++;
                                } else if (inPlayCard.getName().equals("Plains")) {
                                    whiteTapped++;
                                }
                                willTap.add(inPlayCard);
                                if (blueTapped + whiteTapped == manaCnt[i]) {
                                    blueMana -= blueTapped;
                                    whiteMana -= whiteTapped;
                                    break;
                                }
                            }
                        }
                    } else {
                        return false;
                    }
                }
            }
        }

        if (card.getType().equals("Land")) {
            if (player.getPlayedLand()) {
                return false;
            } else {
                player.setPlayedLand(true);
            }
        }

        for (Card c : willTap) {
            c.setTapped(true);
        }

        return true;
    }

}
