package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer implements Player {
    private Scanner in;

    private int matchesWon;
    private final int matchesToWin;

    public HumanPlayer(Scanner in, int matches) {
        this.in = in;
        matchesWon = 0;
        matchesToWin = matches;
    }

    @Override
    public Move makeMove(Position position, int row, int col) {
        System.out.println();
        System.out.println("Current position");
        System.out.println(position);
        System.out.println("Enter you move for " + position.getTurn());
        while (true) {
            try {
                Move ret = new Move(in.nextInt() - 1, in.nextInt() - 1, position.getTurn());
                if (!position.isValid(ret)) {
                    System.out.println("Please, enter valid move for " + position.getTurn());
                } else {
                    return ret;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please, enter valid move for " + position.getTurn());
                in.nextLine();
            } catch (NoSuchElementException e){
                System.out.println("See you later!");
                System.exit(0);
            }
        }

    }

    public void Won() {
        matchesWon++;
    }

    public boolean isWinner() {
        return matchesToWin <= matchesWon;
    }
}
