package game;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int n, m, k, matches;
        Player human1, human2;
        loop:
        while (true) {
            try {
                System.out.print("N: ");
                n = in.nextInt();
                System.out.print("M: ");
                m = in.nextInt();
                System.out.print("K: ");
                k = in.nextInt();
                System.out.print("Matches: ");
                matches = in.nextInt();
                System.out.print("Your first player (1 - Human, 2 - Random, 3 - Sequential): ");
                switch (in.nextInt()) {
                    case 1:
                        human1 = new HumanPlayer(new Scanner(System.in), matches);
                        break;
                    case 2:
                        human1 = new RandomPlayer(matches);
                        break;
                    case 3:
                        human1 = new SequentialPlayer(matches);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }
                System.out.print("Your second player (1 - Human, 2 - Random, 3 - Sequential): ");
                switch (in.nextInt()) {
                    case 1:
                        human2 = new HumanPlayer(new Scanner(System.in), matches);
                        break;
                    case 2:
                        human2 = new RandomPlayer(matches);
                        break;
                    case 3:
                        human2 = new SequentialPlayer(matches);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value");
                }
                break;
            } catch (InputMismatchException e) {
                System.out.println("Please, enter numbers, not something else ");
                in.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("See you later!");
                System.exit(0);
            } catch (RuntimeException e) {
                System.out.println("You probably did something unexpected. Please, repeat your input");
                in.nextLine();
            }
        }

        int matchCounter = 0;
        while (!(human1.isWinner() || human2.isWinner())) {
            final int result;
            result = new TwoPlayerGame(
                    new TicTacToeBoard(n, m, k),
                    human1,
                    human2,
                    matchCounter
            ).play(true);
            matchCounter++;
            switch (result) {
                case 1:
                    System.out.println("First player WON");
                    System.out.println("Second player LOST");
                    human1.Won();
                    break;
                case 2:
                    System.out.println("Second player WON");
                    System.out.println("First player LOST");
                    human2.Won();
                    break;
                case 0:
                    System.out.println("Draw");
                    break;
                default:
                    throw new AssertionError("Unknown result " + result);
            }
        }
        if (human1.isWinner()) {
            System.out.println("First player WON a game!");
        } else {
            System.out.println("Second player WON a game!");
        }
    }
}
