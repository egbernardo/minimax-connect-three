import java.util.LinkedList;
import java.util.List;

public class Game {
    static int idStages;
    private final int stage;
    int utility;
    private int[][] board;
    private int currentDisk;
    private List<Game> nextStages;
    private Move move; // PATCH better move

    public Game(int lengthX, int lengthY){
        this.board = new int[lengthX][lengthY];
        this.nextStages = new LinkedList<>();
        this.initialize();
        this.stage = Game.idStages++;
    }
    private Game(int[][] board){
        this.copyBoard(board);
        this.nextStages = new LinkedList<>();
        this.stage = Game.idStages++;
    }

    private void initialize(){
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j <= this.board.length; j++) {
                this.board[i][j] = -1; // invalid
            }
        }
    }
    private void copyBoard(int[][] board){
        this.board = new int[board.length][board[0].length];

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }

    public int getStage() {
        return stage;
    }
    public void setCurrentDisk(int currentDisk) {
        this.currentDisk = currentDisk;
    }
    public int getCurrentDisk() {
        return currentDisk;
    }
    public void setBestMove(Move move) {
        this.move = move;
    }
    public Move getBestMove() {
        return move;
    }

    public void playNextStages(int disk){
        int xLine = 0; //aux
        int jCollumn = 0;
        boolean free = false;

        for (int j = 0; j < this.board[0].length; j++) { // run column

            if (this.board[xLine][j] == -1) { // drop disk
                for (int k = xLine; k < this.board.length; k++) { // slip disk
                    if (this.board[k][j] == -1) { // check free position
                        xLine = k;
                        jCollumn = j;
                        free = true;
                    }
                }
                if(free){
                    Game nextStage = new Game(this.board);
                    nextStage.setCurrentDisk(disk);
                    nextStage.endPosition(xLine, jCollumn, disk); // end of move
                    xLine = 0;
                    jCollumn = 0;

                    this.nextStages.add(nextStage);
                }
            }
        }
    }
    public List<Game> getNextStages(){
        return nextStages;
    }
    public void endPosition(int positionX, int positionY, int colorDisk){
        if(this.board[positionX][positionY] == -1){
            this.board[positionX][positionY] = colorDisk;
        }
    }

    public int horizontalSequence(int disk){
        int count = 0;
        int  times = 0;
        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {

                if(this.board[i][j] == disk){

                    for (int k = j; k <= j+2; k++) {
                        if(k < this.board[0].length && this.board[i][k] == disk){
                            count++;
                        }
                    }
                    if (count == 3){
                        times++;
                    }
                    count = 0;
                }
            }
        }
        return times;
    }
    public int verticalSequence(int disk){
        int count = 0;
        int  times = 0;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {

                if(this.board[i][j] == disk){

                    for (int k = i; k <= i+2; k++) {
                        if( k < this.board.length && this.board[k][j] == disk){
                            count++;
                        }
                    }
                    if (count == 3){
                        times++;
                    }
                    count = 0;
                }
            }
        }
        return times;
    }
    public int diagonalsSequence(int disk){
        int countLR = 0;
        int countRL = 0;
        int  times = 0;

        for (int i = 0; i < this.board.length; i++) {
            for (int j = 0; j < this.board[0].length; j++) {

                if(this.board[i][j] == disk) {
                    int iAuxLR = i;
                    int jAuxLR = j;
                    int iAuxRL = i;
                    int jAuxRL = j;

                    for (int k = 0; k < 3; k++) {

                        if (iAuxLR < this.board.length && jAuxLR < this.board[0].length && this.board[iAuxLR][jAuxLR] == disk) {
                            countLR++;
                        }

                        if (iAuxRL < this.board.length && jAuxRL > -1 && this.board[iAuxRL][jAuxRL] == disk) {
                            countRL++;
                        }

                        iAuxLR++;
                        jAuxLR++;
                        iAuxRL++;
                        jAuxRL--;
                    }
                    if (countLR == 3){
                        times++;
                    }
                    if (countRL == 3){
                        times++;
                    }
                    countLR = 0;
                    countRL = 0;
                }
            }
        }
        return times;
    }
    public boolean isTerminal(){
        boolean terminal = true;

        for (int[] xBoard : this.board) {
            for (int j = 0; j < this.board[0].length; j++) {
                if (xBoard[j] == -1) {
                    terminal = false;
                    return terminal;
                }
            }
        }
        return terminal;
    }

    public void allPlays(int depth){

        this.espace(depth);
        System.out.print("("+this.stage+"): { ");
        for (Game game : this.nextStages) {
//            System.out.print(game.stage+" ");
            System.out.print(game+" ");
        }
        System.out.println("}");

        for (Game game : this.nextStages) {
            this.prevPlays(game,depth);
        }
    }
    private void prevPlays(Game game, int depth){

        if(game != null){
            this.espace(depth);
            System.out.print("|  ("+game.stage+")- { ");

            for (Game g: game.nextStages) {
//                System.out.print(g.stage+ " ");
                System.out.print(g+ " ");
            }
            System.out.println("}");

            for (Game g: game.nextStages) {
                g.allPlays(depth+1);
            }
        }
    }
    private void espace(int depth) {
        for (int i = 0; i < depth; i++) {
            System.out.print("|  ");
        }
    }

    @Override
    public String toString() {
        return  "{stage=" + stage +
                ", utility=" + utility +
                '}';
    }
}