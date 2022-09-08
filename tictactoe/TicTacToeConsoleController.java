package lab7.tictactoe;

import java.io.IOException;
import java.util.Scanner;

/**
 * This class represents a  console controller for the TicTacToe Controller abstract.
 */
public class TicTacToeConsoleController implements TicTacToeController {
  private final Readable in;
  private final Appendable out;

  /**
   * This class represents a  console controller for the TicTacToe Controller abstract.
   * @param in the game state read from
   * @param out the game state read after
   */
  public TicTacToeConsoleController(Readable in, Appendable out) {
    if (in == null || out == null) {
      throw new IllegalArgumentException("Invalid Readable or Appendable");
    }
    this.in = in;
    this.out = out;
  }

  @Override
  public void playGame(TicTacToe m) throws IllegalStateException {
    boolean flag = false;
    Scanner scan = new Scanner(this.in);

    if (m == null) {
      throw new IllegalArgumentException("Input TicTacToe is null");
    }

    while (!m.isGameOver()) {
      if (!flag) {

        try {
          this.out.append(m.toString() + "\n");
        } catch (IOException e) {
          throw new IllegalStateException("Error when trying to output game state");
        }

        try {
          this.out.append("Enter a move for " + m.getTurn().toString() + ":\n");
        } catch (IOException e) {
          throw new IllegalStateException("Error when writing the turn prompt");
        }
      }
      String row = "";
      String col = "";
      if (scan.hasNext()) {
        row = scan.next();

        if (row.equalsIgnoreCase("q")) {
          try {
            this.out.append("Game quit! Ending game state:\n" + m.toString() + "\n");
            return;
          } catch (IOException e) {
            throw new IllegalStateException("Error when quitting the game.");
          }
        } else {
          col = scan.next();
          if (col.equalsIgnoreCase("q")) {
            try {
              this.out.append("Game quit! Ending game state:\n" + m.toString() + "\n");
              return;
            } catch (IOException e) {
              throw new IllegalStateException("Error when quitting the game.");
            }
          }
        }
      }

      //Input must be a pair of numbers, 1 to 3.
      int a = 0;
      int b = 0;
      try {
        a = Integer.parseInt(row);
        b = Integer.parseInt(col);
      } catch (NumberFormatException e) {
        flag = true;
      }

      try {
        a = a - 1;
        b = b - 1;
        m.move(a, b);
        flag = false;
      } catch (IllegalArgumentException e) {
        try {
          this.out.append(e.getMessage() + "\n");
        } catch (IOException e1) {
          e1.printStackTrace();
        }
      } catch (IllegalStateException e) {
        throw new IllegalStateException("Out Of Board");
      }
    }

    if (m.isGameOver()) {
      try {
        out.append(m.toString() + "\n");
        if (m.getWinner() == Player.X) {
          out.append("Game is over! X wins.");
        } else if (m.getWinner() == Player.O) {
          out.append("Game is over! O wins.");
        } else {
          out.append("Game is over! Tie game.");
        }
      } catch (IOException e) {
        throw new IllegalStateException("Failing Appendable", e);
      }
    }
    scan.close();
  }
}