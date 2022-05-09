import game.GameLogic;
import game.Opponent;
import game.Player;
import hintLogic.HintLogic;

public class PlayGameWithHintLogic {
    public static void main(String[] args){
        Player player = new Player();
        Opponent opponent = new Opponent();
        GameLogic game = new GameLogic(player, opponent);
        HintLogic withHints = new HintLogic(game);
        withHints.playGame();
    }
}
