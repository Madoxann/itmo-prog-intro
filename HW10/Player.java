package game;

public interface Player {
    Move makeMove(Position position, int row, int col);

    void Won();

    boolean isWinner();
}
