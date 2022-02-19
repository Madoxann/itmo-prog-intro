package game;

public class SequentialPlayer implements Player {

    private int matchesWon;
    private final int matchesToWin;

    public SequentialPlayer(int matches) {
        matchesWon = 0;
        matchesToWin = matches;
    }

    @Override
    public Move makeMove(Position position, int row, int col) {
        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                final Move move = new Move(r, c, position.getTurn());
                if (position.isValid(move)) {
                    return move;
                }
            }
        }
        throw new AssertionError("No valid moves");
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
