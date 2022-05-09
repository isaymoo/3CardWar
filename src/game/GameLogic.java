package game;

import game.deck.Card;
import game.deck.Deck;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;
import java.util.Scanner;

// This code assumes that a specific card was actually played by the specified player
public class GameLogic {
    public GameLogic(Player player1, GamePlayer player2){
        player = player1;
        opponent = player2;
    }
    static Player player;
    static GamePlayer opponent;
    Deck deck = new Deck();
    Scanner input = new Scanner(System.in);
    private boolean currentInWar = false;

    public int getTurns() {
        return turns;
    }

    private int turns;

    public Player getPlayer() {
        return player;
    }

    public GamePlayer getOpponent() {
        return opponent;
    }

    public void turn(Card playerCard, Card opponentCard) {
        turns++;
        if (opponentCard.getValue() > playerCard.getValue()) {
            opponent.won(playerCard, opponentCard);
        } else if (opponentCard.getValue() < playerCard.getValue()) {
            player.won(playerCard, opponentCard);
        } else {
//            System.out.println("War declared");
            Collection<Card> playerCards = new ArrayList<>();
            Collection<Card> opponentCards = new ArrayList<>();
            playerCards.add(playerCard);
            opponentCards.add(opponentCard);

            war(playerCards, opponentCards);
        }
        while (player.getHand().size() < 3 && player.getDeckSize() != 0){
            if (player.isAutomated()){
                Random random = new Random();
                int i = random.nextInt(player.getDeckSize());
                if (!player.unknownDeck.isEmpty()){
                    drawCard("New card in hand: ", player.unknownDeck.get(i));
                }
                else{
                    drawCard("New card in hand: ", player.deck.get(i));
                }
            }
            else{
                drawCard("New card in hand: ", null);
            }

        }
        if (opponent.getClass().equals(Player.class)){
            Player p = (Player) opponent;
            while(p.getHand().size() < 3 && opponent.getDeckSize() != 0){
                if (p.isAutomated()){
                    Random random = new Random();
                    int i = random.nextInt(opponent.getDeckSize());
                    if (!opponent.unknownDeck.isEmpty()){
                        drawCard2("New card in hand: ", opponent.unknownDeck.get(i));
                    }
                    else{
                        drawCard2("New card in hand: ", opponent.deck.get(i));
                    }
                }
                else{
                    drawCard2("New card in hand: ", null);
                }
            }
        }
//        System.out.println("Next turn");
    }

    private void war(Collection<Card> playerCards, Collection<Card> opponentCards) {
        currentInWar = true;
        Card playerFlipped;
        Card opponentFlipped;
        if (!player.isAutomated()) {
            for (int i = 0; i < 3; i++) {
                if (player.getDeckSize() > 1) {
                    playerCards.add(playerLoop(true, null));
                }
            }
            if (player.getDeckSize() > 0) {
                playerFlipped = playerLoop(true, null);
                playerCards.add(playerFlipped);
            } else playerFlipped = new Card("0", "null");
        }
        else{
            for (int i = 0; i < 3; i++) {
                if (player.getDeckSize() > 1) {
                    Random random = new Random();
                    int index = random.nextInt(player.getDeckSize());
                    Card toPlay;
                    if(!player.unknownDeck.isEmpty()) toPlay = player.unknownDeck.get(index);
                    else toPlay = player.deck.get(index);
                    playerCards.add(playerLoop(true, toPlay));
                }
            }
            if (player.getDeckSize() > 0) {
                int index = (int) (Math.random() * (player.getDeckSize()));
                if(!player.unknownDeck.isEmpty()) playerFlipped = player.unknownDeck.get(index);
                else playerFlipped = player.deck.get(index);
                playerCards.add(playerLoop(true, playerFlipped));
            }
            else playerFlipped = new Card("0", "null");
        }
        if (opponent.getClass().equals(Opponent.class)){
            Opponent o = (Opponent) opponent;
            if (o.isRandomOpponent()){
                for (int i = 0; i < 3; i++) {
                    if (o.getDeckSize() > 1) {
                        Random random = new Random();
                        int index = random.nextInt(opponent.getDeckSize());
                        Card toPlay;
                        if(!opponent.unknownDeck.isEmpty()) toPlay = opponent.unknownDeck.get(index);
                        else toPlay = opponent.deck.get(index);
                        opponentCards.add(opponentLoop(toPlay));
                    }
                    else break;
                }
                if (o.getDeckSize() > 0) {
                    Random random = new Random();
                    int index = random.nextInt(opponent.getDeckSize());
                    if(!opponent.unknownDeck.isEmpty()) opponentFlipped = opponent.unknownDeck.get(index);
                    else opponentFlipped = opponent.deck.get(index);
                    opponentCards.add(opponentLoop(opponentFlipped));
                }
                else opponentFlipped = new Card("0", "null");
            }
            else{
                for (int i = 0; i < 3; i++) {
                    if (opponent.getDeckSize() > 1) {
                        opponentCards.add(opponentLoop(null));
                    }
                    else break;
                }
                if (opponent.getDeckSize() > 0) {
                    opponentFlipped = opponentLoop(null);
                    opponentCards.add(opponentFlipped);
                }
                else opponentFlipped = new Card("0", "null");
            }
        }
        else{
            Player p = (Player) opponent;
            if (!p.isAutomated()) {
                for (int i = 0; i < 3; i++) {
                    if (opponent.getDeckSize() > 1) {
                        opponentCards.add(opponentLoop(null));
                    }
                }
                if (opponent.getDeckSize() > 0) {
                    opponentFlipped = opponentLoop(null);
                    opponentCards.add(opponentFlipped);
                } else opponentFlipped = new Card("0", "null");
            }
            else{
                for (int i = 0; i < 3; i++) {
                    if (opponent.getDeckSize() > 1) {
                        int index = (int) (Math.random() * (opponent.getDeckSize()));
                        Card toPlay;
                        if(!opponent.unknownDeck.isEmpty()) toPlay = opponent.unknownDeck.get(index);
                        else toPlay = opponent.deck.get(index);
                        opponentCards.add(opponentLoop(toPlay));
                    }
                }
                if (opponent.getDeckSize() > 0) {
                    int index = (int) (Math.random() * (opponent.getDeckSize()));
                    if(!opponent.unknownDeck.isEmpty()) opponentFlipped = opponent.unknownDeck.get(index);
                    else opponentFlipped = opponent.deck.get(index);
                    opponentCards.add(opponentLoop(opponentFlipped));
                }
                else opponentFlipped = new Card("0", "null");
            }
        }


        if (opponentFlipped.getValue() > playerFlipped.getValue()) {
            opponent.wonWar(opponentCards, playerCards);
        } else if (opponentFlipped.getValue() < playerFlipped.getValue()) {
            player.wonWar(opponentCards, playerCards);
        } else {
//            System.out.println("War declared");
            war(playerCards, opponentCards);
        }
        currentInWar = false;
    }

    public void gameLoop() {
//        System.out.println("Current Hand:");

        drawCard("Card 1: ", null);
        drawCard("Card 2: ", null);
        drawCard("Card 3: ", null);

//        System.out.println("First Turn");
        while (true) {
            Card[] cards = inputLoop(null, null);
            turn(cards[0], cards[1]);
            if (player.getDeckSize() == 49) {
//                System.out.println("Player Won!");
                break;
            }
            else if (opponent.getDeckSize() == 52){
//                System.out.println("Opponent won.");
                break;
            }
        }
    }
    public void drawCard(String text, Card toDraw){
        if (toDraw == null) {
            while (true) {
//                System.out.print(text);
                String cardPlayed = input.nextLine();
                String[] parsed = cardPlayed.split(" ");
                if (deck.values.contains(parsed[0].toLowerCase())) {
                    if (parsed[1].equals("of")) {
                        if (deck.suits.contains(parsed[2].toLowerCase())) {
                            Card newCard = new Card(parsed[0].toLowerCase(), parsed[2].toLowerCase());
                            if (player.getHand().contains(newCard) || player.getDiscard().contains(newCard) ||
                                    opponent.getDeck().contains(newCard) || opponent.getDiscard().contains(newCard)) {

                            }
                            else {
                                player.moveToHand(newCard);
                                break;
                            }
                        }
                    }
                }
//                System.out.println("Invalid Card.");
            }
        }
        else{
            player.moveToHand(toDraw);
        }
    }
    public void drawCard2(String text, Card toDraw){
        if (toDraw == null) {
            while (true) {
//                System.out.print(text);
                String cardPlayed = input.nextLine();
                String[] parsed = cardPlayed.split(" ");
                if (deck.values.contains(parsed[0].toLowerCase())) {
                    if (parsed[1].equals("of")) {
                        if (deck.suits.contains(parsed[2].toLowerCase())) {
                            Card newCard = new Card(parsed[0].toLowerCase(), parsed[2].toLowerCase());
                            if (player.getHand().contains(newCard) || player.getDiscard().contains(newCard) ||
                                    opponent.getDeck().contains(newCard) || opponent.getDiscard().contains(newCard)) {

                            }
                            else {
                                ((Player)opponent).moveToHand(newCard);
                                break;
                            }
                        }
                    }
                }
//                System.out.println("Invalid Card.");
            }
        }
        else{
            ((Player)opponent).moveToHand(toDraw);
        }
    }

    private Card playerLoop(boolean inWar, Card toPlay) {
        Card played;
        if (player.getHand().size() < 3) player.shuffle();
        if (!player.isAutomated()) {
            if (!inWar) {
                played = player.cardPlayed(cardLoop("Player"));
                while (player.getHand().size() < 3 && player.getDeckSize() > 0) {
                    drawCard("New card in hand: ", null);
                }
            } else {
                played = player.war(cardLoop("Player"));
            }
        }
        else{
            if (!inWar) {
                played = player.cardPlayed(toPlay);
//                System.out.println("Player Played: " + played);
                while (player.getHand().size() < 3 && player.getDeckSize() > 0) {
                    Random random = new Random();
                    int i = random.nextInt(player.getDeckSize());
                    if (!player.unknownDeck.isEmpty()){
                        drawCard("New card in hand: ", player.unknownDeck.get(i));
                    }
                    else{
                        drawCard("New card in hand: ", player.deck.get(i));
                    }
                }
            } else {
                played = player.war(toPlay);
//                System.out.println("Player Played: " + played);
            }
        }
        return played;
    }

    private Card cardLoop(String who) {
        String cardPlayed;
        Card newCard;

        while (true) {
//            System.out.print(who + " card played: ");
            cardPlayed = input.nextLine();
            String[] parsed = cardPlayed.split(" ");
            if (deck.values.contains(parsed[0].toLowerCase())) {
                if (parsed[1].equals("of")) {
                    if (deck.suits.contains(parsed[2].toLowerCase())) {
                        newCard = new Card(parsed[0].toLowerCase(), parsed[2].toLowerCase());
                        if (who.equals("Player") || who.equals("Player 2")) {
                            if ((!player.getHand().contains(newCard) && !currentInWar &&
                                    !opponent.getDeck().contains(newCard) && !opponent.getDiscard().contains(newCard)) ||
                                    (player.getHand().contains(newCard) && currentInWar &&
                                    !opponent.getDeck().contains(newCard) && !opponent.getDiscard().contains(newCard))) {
                            }
                            else break;
                        }
                        else {
                            if (player.getDeck().contains(newCard) || player.getDiscard().contains(newCard) ||
                                    player.getHand().contains(newCard) || opponent.getDiscard().contains(newCard)) {

                            } else break;
                        }
                    }
                }
            }
//            System.out.println("Invalid Card.");
        }
        return newCard;
    }

    public Card opponentLoop(Card toPlay) {
        if (opponent.getClass().equals(Opponent.class)) {
            Opponent o = (Opponent) opponent;
            if (!o.isRandomOpponent()) return opponent.cardPlayed(cardLoop("Opponent"));
            else{
//                System.out.println("Opponent Played: " + toPlay);
                return opponent.cardPlayed(toPlay);
            }
        }
        else{
            Player p = (Player) opponent;
            if (p.getHand().size() < 3){
                opponent.shuffle();
            }
            Card played;
            if (!p.isAutomated()){
                if (!currentInWar) {
                    played = opponent.cardPlayed(cardLoop("Player 2"));
//                    System.out.println("Opponent Played: " + played);
                    while (p.getHand().size() < 3 && opponent.getDeckSize() != 0){
                        drawCard2("New card in hand: ", null);
                    }
                } else {
                    played = ((Player)opponent).war(cardLoop("Player 2"));
//                    System.out.println("Opponent Played: " + played);
                }
            }
            else{
                if (!currentInWar) {
                    played = opponent.cardPlayed(toPlay);
//                    System.out.println("Opponent Played: " + played);
                    while (p.getHand().size() < 3 &&  opponent.getDeckSize() != 0) {
                        int i = (int) (Math.random() * (p.getDeckSize()));
                        if (opponent.deck.isEmpty()) drawCard2("New card in hand: ", opponent.unknownDeck.get(i));
                        else drawCard2("New card in hand: ", opponent.deck.get(i));
                    }
                } else {
                    played = ((Player)opponent).war(toPlay);
//                    System.out.println("Opponent Played: " + played);
                }
            }
            return played;
        }
    }

    public Card[] inputLoop(Card opponentPlayed, Card playerPlayed) {
        Card[] loop;
        loop = new Card[]{playerLoop(false, playerPlayed), opponentLoop(opponentPlayed)};
        return loop;
    }
}
