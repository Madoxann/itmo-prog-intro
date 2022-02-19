package game;

import java.util.Arrays;
import java.util.Map;

public class TicTacToeBoard implements Board, Position {
    private static final Map<Cell, String> CELL_TO_STRING = Map.of(
            Cell.E, ".",
            Cell.X, "X",
            Cell.O, "0"
    );

    private final Cell[][] field;
    private Cell turn;
    private final int k;
    private final int rows;
    private final int columns;

    public TicTacToeBoard(int n, int m, int k) {
        this.k = k;
        rows = m;
        columns = n;
        field = new Cell[m][n];
        for (Cell[] row : field) {
            Arrays.fill(row, Cell.E);
        }
        turn = Cell.X;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return columns;
    }

    @Override
    public Cell getTurn() {
        return turn;
    }

    @Override
    public Position getPosition() {
        return this;
    }

    //TODO: turn reading logic is here
    @Override
    public GameResult makeMove(Move move) {
        if (!isValid(move)) {
            return GameResult.LOOSE;
        }

        field[move.getRow()][move.getCol()] = move.getValue();
        if (checkWin(move)) {
            return GameResult.WIN;
        }

        if (checkDraw()) {
            return GameResult.DRAW;
        }

        turn = turn == Cell.X ? Cell.O : Cell.X;
        return GameResult.UNKNOWN;
    }

    //TODO: edit
    private boolean checkDraw() {
        int count = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < columns; c++) {
                if (field[r][c] == Cell.E) {
                    count++;
                }
            }
        }
        if (count == 0) {
            return true;
        }
        return false;
    }


    //col - x
    //row - y
    private boolean checkWin(Move move) {
        int count = 0;
        for (int i = move.getRow() - k + 1; i < move.getRow() + k; i++) {
            if (isInField(i, move.getCol())) {
                if (field[i][move.getCol()] == turn ) {
                    count++;
                } else {
                    count = 0;
                }
                if (count == k) {
                    return true;
                }
            }
        }
        count = 0;
        for (int i = move.getCol() - k + 1; i < move.getCol() + k; i++) {
            if (isInField(move.getRow(), i)) {
                if (field[move.getRow()][i] == turn) {
                    count++;
                } else {
                    count = 0;
                }
                if (count == k) {
                    return true;
                }
            }
        }
        count = 0;
        for (int diag = -k + 1; diag < k; diag++) {
            if(isInField(move.getRow() + diag,move.getCol() +  diag)) {
                if(field[move.getRow() + diag][move.getCol() + diag] == turn) {
                    count++;
                } else {
                    count = 0;
                }
                if (count == k) {
                    return true;
                }
            }
        }
        count = 0;
        for (int diag = -k + 1; diag < k; diag++) {
            if(isInField(move.getRow() + diag,move.getCol() - diag)) {
                if(field[move.getRow() + diag][move.getCol() - diag] == turn) {
                    count++;
                } else {
                    count = 0;
                }
                if (count == k) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isInField(final int row, final int col) {
        return 0 <= row && row < rows
                && 0 <= col && col < columns;
    }

    public boolean isValid(final Move move) {
        return isInField(move.getRow(), move.getCol()) && field[move.getRow()][move.getCol()] == Cell.E
                    && turn == move.getValue();
    }

    @Override
    public Cell getCell(int row, int column) {
        return field[row][column];
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for(int i = 0; i < columns+1; i++) sb.append(i);
        sb.append(System.lineSeparator());

        for (int r = 0; r < rows; r++) {
            sb.append(r + 1);
            for (Cell cell : field[r]) {
                sb.append(CELL_TO_STRING.get(cell));
            }
            sb.append(System.lineSeparator());
        }
        sb.setLength(sb.length() - System.lineSeparator().length());
        return sb.toString();
    }
}
