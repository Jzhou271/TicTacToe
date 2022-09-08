package lab7.test;

import lab7.tictactoe.TicTacToe;
import lab7.tictactoe.FailingAppendable;
import lab7.tictactoe.TicTacToeConsoleController;
import lab7.tictactoe.TicTacToeController;
import lab7.tictactoe.TicTacToeModel;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Test cases for the tic tac toe controller, using mocks for readable and appendable.
 */
public class TicTacToeControllerTest {
  // ADDITIONAL TEST CASES TO IMPLEMENT:
  // Play game to completion, where there is a winner
  // Input where the q comes instead of an integer for the row
  // Input where the q comes instead of an integer for the column
  // Input where non-integer garbage comes instead of an integer for the row
  // Input where non-integer garbage comes instead of an integer for the column
  // Input where the move is integers, but outside the bounds of the board
  // Input where the move is integers, but invalid because the cell is occupied
  // Multiple invalid moves in a row of various kinds
  // Input including valid moves interspersed with invalid moves, game is played to completion
  // What happens when the input ends "abruptly" -- no more input, but not quit, and game not over
  // THIS IS NOT AN EXHAUSTIVE LIST

  /**
   * Test for single valid move.
   */
  @Test
  public void testSingleValidMove() throws IOException {
    TicTacToe m = new TicTacToeModel();
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(new StringReader("2 2 q"), gameLog);
    c.playGame(m);
    assertEquals("   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for X:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "Enter a move for O:\n"
        + "Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   | X |  \n"
        + "-----------\n"
        + "   |   |  \n", gameLog.toString());
  }

  /**
   * Test for bogus input as row.
   */
  @Test
  public void testBogusInputAsRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("!#$ 2 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    // split the output into an array of lines
    String[] lines = gameLog.toString().split("\n");
    // check that it's the correct number of lines
    assertEquals(13, lines.length);
    // check that the last 6 lines are correct
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  ", lastMsg);
    // note no trailing \n here, because of the earlier split
  }

  /**
   * Test for bogus input as col.
   */
  @Test
  public void testBogusInputAsCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 !#$ q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(13, lines.length);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals("Game quit! Ending game state:\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  \n"
        + "-----------\n"
        + "   |   |  ", lastMsg);
  }

  @Test
  public void testTieGame() {
    TicTacToe m = new TicTacToeModel();
    // note the entire sequence of user inputs for the entire game is in this one string:
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(60, lines.length);
    assertEquals("Game is over! Tie game.", lines[lines.length - 1]);
  }

  @Test(expected = IllegalStateException.class)
  public void testFailingAppendable() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 1 2 1 3 2 3 2 1 3 1 3 2");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }

  /**
   * Test for game to completion, where there is a winner X.
   */
  @Test
  public void testWinnerX() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 1 3 2 1 3 1");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(36, lines.length);
    assertEquals("Game is over! X wins.", lines[lines.length - 1]);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals(" O |   | X\n"
        + "-----------\n"
        + " O | X |  \n"
        + "-----------\n"
        + " X |   |  \n"
        + "Game is over! X wins.", lastMsg);
  }

  /**
   * Test for game to completion, where there is a winner O.
   */
  @Test
  public void testWinnerO() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 1 3 3 1 3 3 2 1");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(42, lines.length);
    assertEquals("Game is over! O wins.", lines[lines.length - 1]);
    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals(" O |   | X\n"
        + "-----------\n"
        + " O | X |  \n"
        + "-----------\n"
        + " O |   | X\n"
        + "Game is over! O wins.", lastMsg);
  }

  /**
   * Test for Input where non-integer for the row.
   */
  @Test
  public void testInvalidRow() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 garbage 1 q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("That position is not on the board!", lines[lines.length - 7]);
  }

  /**
   * Test for Input where non-integer for the column.
   */
  @Test
  public void testInvalidCol() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 3 garbage q");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");
    assertEquals(19, lines.length);
    assertEquals("That position is not on the board!", lines[lines.length - 7]);
  }

  /**
   * Test for Input where the move is integers, but outside the bounds of the board.
   */
  @Test
  public void testOutOfBoundRow() {
    // row > 3
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 6 1 q");
    StringBuilder game1 = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, game1);
    c.playGame(m);
    String[] lines = game1.toString().split("\n");
    assertEquals(25, lines.length);
    assertEquals("Enter a move for O:", lines[lines.length - 7]);

    // row = 3
    TicTacToe n = new TicTacToeModel();
    StringReader input2 = new StringReader("2 2 0 1 q");
    StringBuilder game2 = new StringBuilder();
    TicTacToeController d = new TicTacToeConsoleController(input2, game2);
    d.playGame(n);
    String[] lines2 = game2.toString().split("\n");
    assertEquals(25, lines2.length);
    assertEquals("Enter a move for O:", lines2[lines2.length - 7]);

    // row < 0
    TicTacToe x = new TicTacToeModel();
    StringReader input3 = new StringReader("2 2 -5 1 q");
    StringBuilder game3 = new StringBuilder();
    TicTacToeController e = new TicTacToeConsoleController(input3, game3);
    e.playGame(x);
    String[] lines3 = game3.toString().split("\n");
    assertEquals(25, lines3.length);
    assertEquals("Enter a move for O:", lines3[lines3.length - 7]);
  }

  /**
   * Test for Input where the move is integers, but outside the bounds of the board.
   */
  @Test
  public void testOutOfBoundCol() {
    // col > 3
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 9 6 1 q");
    StringBuilder game1 = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, game1);
    c.playGame(m);
    String[] lines = game1.toString().split("\n");
    assertEquals(26, lines.length);
    assertEquals("Enter a move for X:", lines[lines.length - 7]);

    // col = 0
    TicTacToe n = new TicTacToeModel();
    StringReader input2 = new StringReader("2 0 2 1 q");
    StringBuilder game2 = new StringBuilder();
    TicTacToeController d = new TicTacToeConsoleController(input2, game2);
    d.playGame(n);
    String[] lines2 = game2.toString().split("\n");
    assertEquals(25, lines2.length);
    assertEquals("Enter a move for O:", lines2[lines2.length - 7]);

    // col < 0
    TicTacToe x = new TicTacToeModel();
    StringReader input3 = new StringReader("2 -10 2 1 q");
    StringBuilder game3 = new StringBuilder();
    TicTacToeController e = new TicTacToeConsoleController(input3, game3);
    e.playGame(x);
    String[] lines3 = game3.toString().split("\n");
    assertEquals(25, lines3.length);
    assertEquals("Enter a move for O:", lines3[lines3.length - 7]);
  }

  /**
   * Test for Input where the move is integers, but invalid because the cell is occupied.
   */
  @Test
  public void testOccupied() {
    // player X
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 3 3 2 2 q");
    StringBuilder game1 = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, game1);
    c.playGame(m);
    String[] lines = game1.toString().split("\n");
    assertEquals(31, lines.length);
    assertEquals("Enter a move for X:", lines[lines.length - 7]);

    // player Y
    TicTacToe n = new TicTacToeModel();
    StringReader input2 = new StringReader("2 2 3 3 2 1 2 1 q");
    StringBuilder game2 = new StringBuilder();
    TicTacToeController d = new TicTacToeConsoleController(input2, game2);
    d.playGame(n);
    String[] lines2 = game2.toString().split("\n");
    assertEquals(37, lines2.length);
    assertEquals("Enter a move for O:", lines2[lines2.length - 7]);
  }

  /**
   * Test for Input including valid moves interspersed with invalid moves,
   * game is played to completion.
   */
  @Test
  public void testMoves() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 9 0 0 99 -7 -3 1 1 88 12 2 1 "
        + "0 0 2 3 66 77 32 -32 3 1 -99 -88 1 3 1 2 3 3");
    StringBuilder gameLog = new StringBuilder();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
    String[] lines = gameLog.toString().split("\n");

    assertEquals(110, lines.length);
    assertEquals("Enter a move for X:", lines[lines.length - 13]);
    assertEquals("Enter a move for O:", lines[lines.length - 26]);

    String lastMsg = String.join("\n",
        Arrays.copyOfRange(lines, lines.length - 6, lines.length));
    assertEquals(" O | X | O\n"
        + "-----------\n"
        + " X | X | O\n"
        + "-----------\n"
        + " X |   | O\n"
        + "Game is over! O wins.", lastMsg);
  }

  /**
   * Test for the input ends "abruptly".
   */
  @Test(expected = IllegalStateException.class)
  public void testAbruptly() {
    TicTacToe m = new TicTacToeModel();
    StringReader input = new StringReader("2 2 1 1 3 3 2 1");
    Appendable gameLog = new FailingAppendable();
    TicTacToeController c = new TicTacToeConsoleController(input, gameLog);
    c.playGame(m);
  }
}
