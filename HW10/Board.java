package game;

public interface Board {
    int getRows();

    int getCols();

    Position getPosition();

    GameResult makeMove(Move move);
}
