package game;

import game.deck.Card;

import java.util.ArrayList;
import java.util.List;

public class Player extends GamePlayer{
    public boolean isAutomated() {
        return isAutomated;
    }

    public Player(){
        isAutomated = false;
    }
    public Player (boolean isAutomated, List<Card> unknownDeck){
        this.isAutomated = isAutomated;
        this.unknownDeck = unknownDeck;
        for(int i = 0; i < 3; i++){
            moveToHand(unknownDeck.remove(0));
        }
    }

    private List<Card> hand = new ArrayList<>();
    private final boolean isAutomated;

    public List<Card> getHand() {
        return hand;
    }

    public void moveToHand(Card drawn) {
        deck.remove(drawn);
        unknownDeck.remove(drawn);
        hand.add(drawn);
        if (deck.size() < 1 && unknownDeck.isEmpty()) {
            shuffle();
        }
    }

    public Card cardPlayed(Card played) {

        hand.remove(played);
        if (deck.isEmpty() && unknownDeck.isEmpty()) {
            shuffle();
        }
        return played;
    }

    public Card war(Card played){
        if (deck.size() < 1 && unknownDeck.isEmpty()) {
            shuffle();
        }
        deck.remove(played);
        unknownDeck.remove(played);
        if (deck.size() < 1 && unknownDeck.isEmpty()) {
            shuffle();
        }
        return played;
    }
}
