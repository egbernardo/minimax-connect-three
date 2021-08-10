import java.util.NoSuchElementException;
import java.util.PriorityQueue;

public class MinMax {

    private int infinitPlus;
    private int infinitMinus;
    private Move actions;

    public MinMax (Game game){
        this.infinitPlus = Integer.MAX_VALUE/2;
        this.infinitMinus = Integer.MIN_VALUE/2;

        this.actions = this.minMaxDecision(game);
    }

    private int utility(Game game){
        int sumMax =
                game.diagonalsSequence(0)
                        + game.horizontalSequence(0)
                        + game.verticalSequence(0);

        int sumMin =
                game.diagonalsSequence(1)
                        + game.horizontalSequence(1)
                        + game.verticalSequence(1);

        game.utility = (sumMax - sumMin);

        if(game.utility > 0){
            return this.infinitPlus;
        }else if(game.utility < 0){
            return this.infinitMinus;
        }
        return game.utility;
    }

    private int maxValue(Game game){ // RED: 0
        int value = this.infinitMinus;
        PriorityQueue<Move> moves = new PriorityQueue<>();

        if (terminalTest(game)){
            return this.utility(game);
        }
        this.sucessors(game,0);
        for (Game gMax : game.getNextStages()) {
            value = Integer.max(this.infinitMinus, minValue(gMax));
            moves.add(new Move(value, gMax));
        }
        try {
            game.setBestMove(moves.remove());
            game.utility = game.getBestMove().utility;
        }catch (NoSuchElementException e){
        }
        return game.utility;
    }

    private int minValue(Game game){ // BLACK: 1
        int value = this.infinitPlus;
        PriorityQueue<Move> moves = new PriorityQueue<>();
        if (terminalTest(game)){
            return this.utility(game);
        }
        this.sucessors(game,1);
        for (Game gMin : game.getNextStages()) {
            value = Integer.min(this.infinitPlus, maxValue(gMin));
            moves.add(new Move(value, gMin));
        }
        try {
            game.setBestMove(moves.remove());
            game.utility = game.getBestMove().utility;
        }catch (NoSuchElementException e){
        }
        return game.utility;
    }

    private boolean terminalTest(Game game) {
        return game.isTerminal();
    }

    private void sucessors(Game game, int colorDisk){
        game.playNextStages(colorDisk);
    }

    private Move minMaxDecision(Game game){
        game.utility = this.maxValue(game);
        return game.getBestMove();
    }

    public void getActions() {
        while (actions.hasNextMove()){
            System.out.println(actions.toString());
            actions = actions.getNext();
        }
    }
}
