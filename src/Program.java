public class Program {

    public static void main(String[] args) {


        Game game = new Game(3,4);
        game.endPosition(2,0,0);
        game.endPosition(2,2,1);

        //WIN
//        game.endPosition(1,0,1);

        //LOSE
//        game.endPosition(1,0,1);
//        game.endPosition(2,3,1);


        MinMax minMax = new MinMax(game);

        game.allPlays(0);
        System.out.println();
        minMax.getActions();

    }
}
