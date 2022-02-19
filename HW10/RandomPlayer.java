package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    private int matchesWon;
    private final int matchesToWin;

    public RandomPlayer(int matches) {
        matchesWon = 0;
        matchesToWin = matches;
    }

    @Override
    public Move makeMove(Position position, int row, int col) {
        while (true) {
            final Move move = new Move(
                    random.nextInt(row),
                    random.nextInt(col),
                    position.getTurn()
            );
            if (position.isValid(move)) {
                return move;
            }
        }
    }

    @Override
    public void Won() {
        matchesWon++;
    }

    @Override
    public boolean isWinner() {
        return matchesToWin <= matchesWon;
    }
}
