package killdrluckygame;


/**
 * This is an interface for a wrapper for custom random.
 */
public interface CustomRandomInterface {
  /**
   * Generates the next random integer within the specified range.
   *
   * @param bound the upper bound (exclusive) of the random integer.
   * @return the generated random integer.
   */
  int nextInt(int bound);

  /**
   * Generates the next random integer within the specified range.
   *
   * @param min the minimum value (inclusive) of the random integer.
   * @param max the maximum value (exclusive) of the random integer.
   * @return the generated random integer.
   * @throws IllegalArgumentException if the specified range is invalid.
   */
  int nextInt(int min, int max);

  /**
   * Generates the next random double between 0.0 and 1.0.
   *
   * @return the generated random double.
   */
  double nextDouble();


}
