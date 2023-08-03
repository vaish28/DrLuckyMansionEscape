package killdrluckygame;

/**
 * This represents the Position as co-ordinates for spaces of the world.
 */
public interface Position {

  /**
   * This function returns the row of a specific co-ordinate.
   *
   * @return value indicating the row.
   */
  int getRow();

  /**
   * This function returns the column of a specific co-ordinate.
   *
   * @return value indicating the columns
   */
  int getColumn();

}
