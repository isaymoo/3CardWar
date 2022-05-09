package game;

import game.deck.Card;

import java.util.List;

public class Opponent extends GamePlayer {
    public Opponent(){
        randomOpponent = false;
    }
    public Opponent(boolean randomOpponent, List<Card> unknownDeck){
        this.randomOpponent = randomOpponent;
        this.unknownDeck = unknownDeck;
    }

    public boolean isRandomOpponent() {
        return randomOpponent;
    }

    private final boolean randomOpponent;

    public Card cardPlayed(Card played) {
        if (getDeckSize() < 1 && unknownDeck.size() < 4) {
            discard.addAll(unknownDeck);
            shuffle();
        }
        deck.remove(played);
        unknownDeck.remove(played);
        if (getDeckSize() < 1 && unknownDeck.size() < 4) {
            discard.addAll(unknownDeck);
            shuffle();
        }

        return played;
    }
}
