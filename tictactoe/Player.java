package lab7.tictactoe;

/**
 * This is an enum class representing the players (X and O).
 */
public enum Player {
  X, O;

  /**
   * toString method for enum player.
   */
  @Override
  public String toString() {
    switch (this) {
      case X:
        return "X";
      case O:
        return "O";
      default:
        return null;
    }
  }
}