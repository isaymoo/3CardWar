package game;

import game.deck.Card;
import game.deck.Deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class GamePlayer {
    public List<Card> deck = new ArrayList<>();
    public List<Card> discard = new ArrayList<>();
    protected List<Card> unknownDeck = new ArrayList<>();

    public List<Card> getUnknownDeck() {
        return unknownDeck;
    }

    public void shuffle() {
        Deck d = new Deck();
        deck.removeAll(d.cards);
        deck.addAll(discard);
        discard.removeAll(deck);
    }

    public List<Card> getDeck() {
        return deck;
    }

    public List<Card> getDiscard() {
        return discard;
    }

    public int getDeckSize() {
        if (!unknownDeck.isEmpty()){
            return unknownDeck.size();
        }
        else if (!deck.isEmpty()){
            return deck.size();
        }
        else{
            if (!discard.isEmpty()) shuffle();
            return deck.size();
        }
    }
    public abstract Card cardPlayed(Card card);
    public void won(Card one, Card two) {
        discard.add(one);
        discard.add(two);
        if (deck.size() < 1 && unknownDeck.isEmpty()) {
            shuffle();
        }
    }

    public void wonWar(Collection<Card> one, Collection<Card> two) {
        discard.addAll(one);
        discard.addAll(two);
        if (deck.size() < 1 && unknownDeck.isEmpty()) {
            shuffle();
        }
    }
}
