import game.GameLogic;
import game.Player;
import game.deck.Card;
import game.deck.Deck;
import hintLogic.HintLogic;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AutoVersusAuto {
    public static void main(String[] args){
        Map<String, List<Integer>> winMap = new HashMap<>();
        for (int j = 0; j < 10000; j++){
            Deck deck = new Deck();
            List<Card> playerCards = new ArrayList<>();
            List<Card> opponentCards = new ArrayList<>();
            for (int i = 0; i < 26; i++){
                playerCards.add(deck.cards.remove(0));
            }
            for (int i = 0; i < 26; i++){
                opponentCards.add(deck.cards.remove(0));
            }
            Player player = new Player(true, playerCards);
            Player player2 = new Player(true, opponentCards);
            GameLogic game = new GameLogic(player, player2);
            HintLogic withHints = new HintLogic(game);
            withHints.playGame();
            if (winMap.containsKey(withHints.getWhoWon())){
                List<Integer> mapList = winMap.get(withHints.getWhoWon());
                mapList.add(withHints.getHowLong());
                winMap.put(withHints.getWhoWon(), mapList);
            }
            else{
                List<Integer> mapList = new ArrayList<>();
                mapList.add(withHints.getHowLong());
                winMap.put(withHints.getWhoWon(), mapList);
            }
            System.out.println(j);
        }
        try {
            File file = new File("AutoVAuto.csv");
            FileWriter writer = new FileWriter(file);
            writer.write(",Program 1,Program 2\r\n");
            List<Integer> playerWins = winMap.get("Player");
            List<Integer> opponentWins = winMap.get("Opponent");
            int length = playerWins.size();
            int totalPlayerWinTurns = 0;
            int totalOpponentWinTurns = 0;
            if (length < opponentWins.size()) length = opponentWins.size();
            for (int i = 0; i < length; i++){
                writer.write(",");
                if (i < playerWins.size()){
                    writer.write(playerWins.get(i).toString());
                    totalPlayerWinTurns += playerWins.get(i);
                }
                writer.write(",");
                if (i < opponentWins.size()){
                    writer.write(opponentWins.get(i).toString());
                    totalOpponentWinTurns += opponentWins.get(i);
                }
                writer.write("\r\n");

            }
            int playerAv = totalPlayerWinTurns/playerWins.size();
            int opAv = totalOpponentWinTurns/opponentWins.size();
            writer.write("Average Turns To Win," + playerAv + ",");
            writer.write(opAv + "\r\n");
            writer.close();

        }
        catch(Exception e){

        }
    }

}
