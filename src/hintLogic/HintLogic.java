package hintLogic;

import game.GameLogic;
import game.Opponent;
import game.Player;
import game.deck.Card;
import game.deck.Deck;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class HintLogic {
    private GameLogic game;
    private String whoWon;
    private int howLong;

    public String getWhoWon() {
        return whoWon;
    }

    public int getHowLong() {
        return howLong;
    }

    public HintLogic(GameLogic game) {
        this.game = game;
    }
    public void playGame(){

        if (!game.getPlayer().isAutomated()) {
//            System.out.println("Current Hand:");
            game.drawCard("Card 1: ", null);
            game.drawCard("Card 2: ", null);
            game.drawCard("Card 3: ", null);
        }

//        System.out.println("First Turn");
        while (true) {
            Card card0 = game.getPlayer().getHand().get(0);
            Card card1;
            if (game.getPlayer().getHand().size() > 1) card1 = game.getPlayer().getHand().get(1);
            else card1 = new Card("0", "null");
            Card card2;
            if (game.getPlayer().getHand().size() > 2) card2 = game.getPlayer().getHand().get(2);
            else card2 = new Card("0", "null");
            Card card3;
            if (game.getPlayer().getHand().size() > 3) card3 = game.getPlayer().getHand().get(3);
            else card3 = new Card("0", "null");
            Card card4;
            if (game.getPlayer().getHand().size() > 4) card4 = game.getPlayer().getHand().get(4);
            else card4 = new Card("0", "null");
            double c0chance = getChance(card0, "pl");
            double c1chance = getChance(card1, "pl");
            double c2chance = getChance(card2, "pl");
            double c3chance = getChance(card3, "pl");
            double c4chance = getChance(card4, "pl");
//            System.out.println("Chance of winning with " + card0.toString() + ": " + c0chance + "%");
//            if (game.getPlayer().getHand().size() > 1)System.out.println("Chance of winning with " + card1.toString() + ": " + c1chance + "%");
//            if (game.getPlayer().getHand().size() > 2)System.out.println("Chance of winning with " + card2.toString() + ": " + c2chance + "%");

//            System.out.print("I recommend: ");

            Card best = findBestPlay(card0, card1, card2, card3, card4, c0chance, c1chance, c2chance, c3chance, c4chance);

//            System.out.println(best);
            Card[] cards;
            if (game.getOpponent().getClass().equals(Opponent.class)) {
                if (!game.getPlayer().isAutomated() && !((Opponent) game.getOpponent()).isRandomOpponent()) {
                    cards = game.inputLoop(null, null);
                } else if (game.getPlayer().isAutomated() && !((Opponent) game.getOpponent()).isRandomOpponent()) {
                    cards = game.inputLoop(null, best);
                } else if (!game.getPlayer().isAutomated() && ((Opponent) game.getOpponent()).isRandomOpponent()) {
                    Card toPlay;
                    Random random = new Random();
                    int i = random.nextInt(game.getOpponent().getDeckSize());
                    if (!game.getOpponent().getUnknownDeck().isEmpty())
                        toPlay = game.getOpponent().getUnknownDeck().get(i);
                    else toPlay = game.getOpponent().deck.get(i);
                    cards = game.inputLoop(game.opponentLoop(toPlay), null);
                } else {
                    Card toPlay;
                    Random random = new Random();
                    int i = random.nextInt(game.getOpponent().getDeckSize());
                    if (!game.getOpponent().getUnknownDeck().isEmpty())
                        toPlay = game.getOpponent().getUnknownDeck().get(i);
                    else toPlay = game.getOpponent().deck.get(i);
                    cards = game.inputLoop(toPlay, best);
                }
            }
            else{
                Card oC0 = ((Player) game.getOpponent()).getHand().get(0);
                Card oC1;
                if (((Player) game.getOpponent()).getHand().size() > 1) oC1 = ((Player) game.getOpponent()).getHand().get(1);
                else oC1 = new Card("0", "null");
                Card oC2;
                if (((Player) game.getOpponent()).getHand().size() > 2) oC2 = ((Player) game.getOpponent()).getHand().get(2);
                else oC2 = new Card("0", "null");
                Card oC3;
                if (((Player) game.getOpponent()).getHand().size() > 3) oC3 = ((Player) game.getOpponent()).getHand().get(3);
                else oC3 = new Card("0", "null");
                Card oC4;
                if (((Player) game.getOpponent()).getHand().size() > 4) oC4 = ((Player) game.getOpponent()).getHand().get(4);
                else oC4 = new Card("0", "null");
                double oc0chance = getChance(oC0, "op");
                double oc1chance = getChance(oC1, "op");
                double oc2chance = getChance(oC2, "op");
                double oc3chance = getChance(oC3, "op");
                double oc4chance = getChance(oC4, "op");
                Card obest = findBestPlay(oC0, oC1, oC2, oC3, oC4, oc0chance, oc1chance, oc2chance,oc3chance, oc4chance);
                if (!game.getPlayer().isAutomated() && !((Player) game.getOpponent()).isAutomated()) {
                    cards = game.inputLoop(null, null);
                } else if (game.getPlayer().isAutomated() && !((Player) game.getOpponent()).isAutomated()) {
                    cards = game.inputLoop(null, best);
                } else if (!game.getPlayer().isAutomated() && ((Player) game.getOpponent()).isAutomated()) {
                    cards = game.inputLoop(game.opponentLoop(obest), null);
                } else {
                    cards = game.inputLoop(obest, best);
                }
            }
            game.turn(cards[0], cards[1]);
            if (game.getPlayer().getDeckSize() + game.getPlayer().getDiscard().size() == 49) {
//                System.out.println("Player Won!");
                whoWon = "Player";
                break;
            }
            else if (game.getPlayer().getDeckSize() + game.getPlayer().getDiscard().size() + game.getPlayer().getHand().size() == 0){
//                System.out.println("Opponent won.");
                whoWon = "Opponent";
                break;
            }
        }
//        System.out.println("Turns played: " + game.getTurns());
        howLong = game.getTurns();

    }

    private Card findBestPlay(Card card0, Card card1, Card card2, Card card3, Card card4,
                              double c0chance, double c1chance, double c2chance, double c3chance, double c4chance) {
        Card zeroOfNull = new Card("0", "null");
        if (card0.equals(zeroOfNull)) {
            c0chance = 40;
        }
        if (card1.equals(zeroOfNull)){
            c1chance = 40;
        }
        if (card2.equals(zeroOfNull)){
            c2chance = 40;
        }
        if (card3.equals(zeroOfNull)){
            c3chance = 40;
        }
        if (card4.equals(zeroOfNull)){
            c4chance = 40;
        }
        double currBestOdds;
        double c0Odds = Math.abs(40.0 - c0chance);
        double c1Odds = Math.abs(40.0 - c1chance);
        double c2Odds = Math.abs(40.0 - c2chance);
        double c3Odds = Math.abs(40.0 - c3chance);
        double c4Odds = Math.abs(40.0 - c4chance);
        if (card0.getValue() < 9 && c0chance > 50) c0Odds += 25;
        if (card1.getValue() < 9 && c1chance > 50) c1Odds += 25;
        if (card2.getValue() < 9 && c2chance > 50) c2Odds += 25;
        if (card3.getValue() < 9 && c3chance > 50) c3Odds += 25;
        if (card4.getValue() < 9 && c4chance > 50) c4Odds += 25;
        c0Odds = getCardOdds(card0, c0Odds);
        currBestOdds = c0Odds;
        c1Odds = getCardOdds(card1, c1Odds);
        if (c1Odds > currBestOdds) currBestOdds = c1Odds;
        c2Odds = getCardOdds(card2, c2Odds);
        if (c2Odds > currBestOdds) currBestOdds = c2Odds;
        c3Odds = getCardOdds(card3, c3Odds);
        if (c3Odds > currBestOdds) currBestOdds = c3Odds;
        c4Odds = getCardOdds(card4, c4Odds);
        if (c4Odds > currBestOdds) currBestOdds = c4Odds;

        if (currBestOdds == c0Odds) return card0;
        else if (currBestOdds == c1Odds) return card1;
        else if (currBestOdds == c2Odds) return card2;
        else if (currBestOdds == c3Odds) return card3;
        else return card4;
    }

    private double getCardOdds(Card card, double cardOdds) {
        switch (card.getValue()){
            case 2:
                cardOdds *= 1.2;
                break;

            case 3:
                cardOdds *= 1.19;
                break;

            case 4:
                cardOdds *= 1.18;
                break;

            case 5:
                cardOdds *= 1.175;
                break;

            case 6:
                cardOdds *= 1.15;
                break;

            case 7:
                cardOdds *= 1.125;
                break;

            case 8:
                cardOdds *= 1.1;
                break;

            case 9:
                cardOdds *= 1.075;
                break;

            case 10:
                cardOdds *= 1.05;
                break;

            case 11:
                cardOdds *= 1.025;
                break;

            case 12:
                cardOdds *= 1.0;
                break;

            case 13:
                cardOdds *= 0.975;
                break;

            case 14:
                cardOdds *= 0.95;
                break;

            default:
                break;
        }
        return cardOdds;
    }

    public double getChance(Card card, String pl){
        if (card == null) return 50.0;
        List<Card> knownCards = new ArrayList<>();
        for (Card c: game.getPlayer().getHand()){
            if (!pl.equals("op"))knownCards.add(c);
        }
        for (Card c : game.getPlayer().getDeck()){
            knownCards.add(c);
        }
        for (Card c : game.getPlayer().getDiscard()){
            knownCards.add(c);
        }
        if (game.getOpponent().getClass().equals(Player.class)){
            if (game.getOpponent().getUnknownDeck().size() == 0){
                for (Card c: ((Player)game.getOpponent()).getHand()){
                    knownCards.add(c);
                }
            }
        }
        for (Card c : game.getOpponent().getDeck()){
            knownCards.add(c);
        }
        for (Card c : game.getOpponent().getDiscard()){
            knownCards.add(c);
        }
        if (pl.equals("op")) {
            for (Card c : ((Player) game.getOpponent()).getHand()) {
                knownCards.add(c);
            }
        }
        Deck d = new Deck();
        List<Card> whatsMissing = d.whatsMissing(knownCards);
        int total = 0;
        for (Card o: game.getOpponent().getDeck()) {
            if (card.getValue() > o.getValue()) total++;
        }
        for (Card o : whatsMissing){
            if (card.getValue() > o.getValue()) total++;
        }
        return Math.round(100.0 * total / (whatsMissing.size() + game.getOpponent().getDeck().size()) * 100.0) / 100.0;
    }
}
