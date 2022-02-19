package game;

public class TwoPlayerGame {
    private final Board board;
    private final Player player1;
    private final Player player2;

    //modded
    private int matches = 0;

    public TwoPlayerGame(Board board, Player player1, Player player2, int matches) {
        this.board = board;
        this.player1 = player1;
        this.player2 = player2;
        this.matches = matches;
    }

    public int play(boolean log) {
        if (matches % 2 == 0) {
            while (true) {
                final int result1 = makeMove(player1, 1, log);
                if (result1 != -1) {
                    System.out.println(matches);
                    return result1;
                }
                final int result2 = makeMove(player2, 2, log);
                if (result2 != -1) {
                    System.out.println(matches);
                    return result2;
                }
            }
        } else {
            while (true) {
                final int result2 = makeMove(player1, 2, log);
                if (result2 != -1) {
                    System.out.println(matches);
                    return result2;
                }
                final int result1 = makeMove(player2, 1, log);
                if (result1 != -1) {
                    System.out.println(matches);
                    return result1;
                }

            }
        }
    }

    private int makeMove(Player player, int no, boolean log) {
        final Move move = player.makeMove(board.getPosition(), board.getRows(), board.getCols());
        final GameResult result = board.makeMove(move);
        if (log) {
            System.out.println();
            System.out.println("Player: " + no);
            System.out.println(move);
            System.out.println(board);
            System.out.println("Result: " + result);
        }
        switch (result) {
            case WIN:
                return no;
            case LOOSE:
                return 3 - no;
            case DRAW:
                return 0;
            case UNKNOWN:
                return -1;
            default:
                throw new AssertionError("Unknown makeMove result " + result);
        }
    }
}
