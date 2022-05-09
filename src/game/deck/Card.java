package game.deck;

import java.util.Objects;

public class Card {
    private final String value;
    private final String suit;
    private final int points;

    public Card(String value, String suit) {
        this.value = value;
        this.suit = suit;
        if (value.equals("ace")) {
            points = 14;
        } else if (value.equals("king")) {
            points = 13;
        } else if (value.equals("queen")) {
            points = 12;
        } else if (value.equals("jack")) {
            points = 11;
        } else points = Integer.parseInt(value);
    }

    public int getValue() {
        return points;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value.equals(card.value) && suit.equals(card.suit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, suit);
    }
    @Override
    public String toString(){
        return value + " of " + suit;
    }
}
