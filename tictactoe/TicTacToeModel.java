package lab7.tictactoe;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * This class represents a model of a game TicTacToe.
 */
public class TicTacToeModel implements TicTacToe {
  private Player[][] board;
  private Player winner;

  /**
   * Constructor takes no arguments. Initialize a new TicTacToeModel.
   */
  public TicTacToeModel() {
    this.board = new Player[3][3];
    this.winner = null;
  }


  @Override
  public String toString() {
    // Using Java stream API to save code:
    return Arrays.stream(this.getBoard())
        .map(row -> " " + Arrays.stream(row).map(p -> p == null ? " " : p.toString())
            .collect(Collectors.joining(" | ")))
        .collect(Collectors.joining("\n-----------\n"));
    // This is the equivalent code as above, but using iteration, and still using
    // the helpful built-in String.join method.
  }

  @Override
  public void move(int r, int c) throws IllegalArgumentException, IllegalStateException {
    if (this.isGameOver()) {
      throw new IllegalStateException("The game is over. No more players can be made!");
    }
    if (r < 0 || c < 0 || r > 2 || c > 2) {
      throw new IllegalArgumentException("That position is not on the board!");
    }
    if (this.getMarkAt(r, c) == Player.X || this.getMarkAt(r, c) == Player.O) {
      throw new IllegalArgumentException("That position is occupied!");
    }
    this.board[r][c] = this.getTurn();

    if ((this.board[r][0] == this.board[r][1] && this.board[r][1] == this.board[r][2])
        || (this.board[0][c] == this.board[1][c] && this.board[1][c] == this.board[2][c])
        || (this.board[0][0] == this.board[1][1] && this.board[1][1] == this.board[2][2]
        && this.board[1][1] != null)
        || (this.board[2][0] == this.board[1][1] && this.board[1][1] == this.board[0][2]
        && this.board[1][1] != null)) {
      this.winner = this.board[r][c];
    }
  }


  @Override
  public Player getTurn() {
    int xTotal = 0;
    int oTotal = 0;
    for (int row = 0; row < 3; row++) {
      for (int col = 0; col < 3; col++) {
        if (this.board[row][col] == Player.X) {
          xTotal++;
        } else if (this.getMarkAt(row, col) == Player.O) {
          oTotal++;
        }
      }
    }
    if (xTotal > oTotal) {
      return Player.O;
    }
    return Player.X;
  }

  @Override
  public boolean isGameOver() {
    int spaceFilled = Arrays.stream(this.board).map(
        row -> Arrays.stream(row).map(
            p -> p == null ? 0 : 1).reduce(0, (a, b) -> a + b)).reduce(0, (a, b) -> a + b);
    if (spaceFilled >= 9) {
      return true;
    }
    return this.getWinner() != null;
  }

  @Override
  public Player getWinner() {
    return this.winner;
  }

  @Override
  public Player[][] getBoard() {
    //return Arrays.stream(this.board).map(Player[]::clone).toArray(b -> board.clone());
    return Arrays.stream(this.board).map((Player[] row) -> row.clone())
        .toArray((int length) -> new Player[length][]);
  }

  @Override
  public Player getMarkAt(int r, int c) throws IllegalArgumentException {
    if (r < 0 || c < 0 || r > 2 || c > 2) {
      throw new IllegalArgumentException("That position is not on the board!");
    }
    return this.board[r][c];
  }
}
