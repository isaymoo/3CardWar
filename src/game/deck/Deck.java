package game.deck;

import java.util.ArrayList;
import java.util.List;


public class Deck {
    public final String[] CARD_VALUES = {
            "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "jack", "queen", "king", "ace"
    };
    public final List<String> values = new ArrayList<>();
    public final List<String> suits = new ArrayList<>();
    public final String[] SUITS = {
            "clubs", "diamonds", "hearts", "spades"
    };
    public List<Card> cards = new ArrayList<>();

    public Deck() {
        values.addAll(List.of(CARD_VALUES));
        suits.addAll(List.of(SUITS));
        List<Card> shuffle = new ArrayList<>();
        for (String value : CARD_VALUES) {
            for (String suit : SUITS) {
                Card card = new Card(value, suit);
                shuffle.add(card);
            }
        }

        while (shuffle.size() > 0){
            int i = (int) (Math.random() * shuffle.size());
            Card removed = shuffle.remove(i);
            cards.add(removed);
        }
    }
    public List<Card> whatsMissing(List<Card> other){
        List<Card> toReturn = new ArrayList<>();
        for (Card c : cards){
            if (!other.contains(c)){
                toReturn.add(c);
            }
        }
        return toReturn;
    }
}
