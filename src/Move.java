public class Move implements Comparable<Move>{

    public int utility;
    public Game action; //num

    public Move(int utility, Game action){
        this.utility = utility;
        this.action = action;
    }

    public boolean hasNextMove(){
        boolean nextMove = false;
        if(this.action.getBestMove() != null){
            nextMove = true;
        }
        return nextMove;
    }
    public Move getNext(){
        return this.action.getBestMove();
    }
    @Override
    public int compareTo(Move o) {
        int compare = 0;

        if(this.action.getCurrentDisk() == 0) {
            if(this.utility > o.utility) compare = -1; //first
            if(this.utility < o.utility) compare = 1;

        }else if(this.action.getCurrentDisk() == 1){
            if(this.utility > o.utility) compare = 1; //last
            if(this.utility < o.utility) compare = -1;
        }
        return compare;
    }

    @Override
    public String toString() {
        return "BEST MOVE{" +
                "utility=" + utility +
                ", action=" + action.getStage() +
                '}';
    }
}
