package killdrluckygame;

/**
 * This class is for WorldPosition of space.
 */
public class WorldPosition implements Position {

  private int row;
  private int column;


  /**
   * This initialises row, column and throws an illegal argument exception.
   *
   * @param row    value indicating the row.
   * @param column value indicating the column.
   * @throws IllegalArgumentException throws an illegal argument exception for invalid values.
   */
  public WorldPosition(int row, int column) throws IllegalArgumentException {

    if (row < 0 || column < 0) {
      throw new IllegalArgumentException(
              "Negative values or invalid values ! Conditions must satisfy !");
    }
    this.row = row;
    this.column = column;
  }

  @Override
  public int getRow() {
    return this.row;
  }

  @Override
  public String toString() {
    return super.toString();
  }

  @Override
  public int getColumn() {
    return this.column;
  }

}
