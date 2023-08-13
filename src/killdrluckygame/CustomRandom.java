package killdrluckygame;

import java.util.Random;

/**
 * The {@code CustomRandom} class provides random number generation functionality,
 * allowing for predictable/mock numbers if specified.
 */

public class CustomRandom implements CustomRandomInterface {
  private Random random;
  private int[] predictableIntNumbers;
  private double[] predictableDoubleNumbers;
  private int intIndex;
  private int doubleIndex;

  /**
   * Constructs a CustomRandom object with default random behavior.
   */
  public CustomRandom() {
    random = new Random();
  }

  /**
   * Constructs a CustomRandom object with predictable integer numbers.
   *
   * @param predictableNumbers The array of predictable integer numbers.
   */
  public CustomRandom(int... predictableNumbers) {
    this.predictableIntNumbers = predictableNumbers;
    intIndex = 0;

  }

  /**
   * Constructs a CustomRandom object with predictable double numbers.
   *
   * @param predictableNumbers the array of predictable double numbers.
   */
  public CustomRandom(double... predictableNumbers) {
    this.predictableDoubleNumbers = predictableNumbers;
    doubleIndex = 0;
  }

  @Override
  public int nextInt(int bound) {
    if (predictableIntNumbers != null) {
      if (intIndex >= predictableIntNumbers.length) {
        intIndex = 0;
      }
      return predictableIntNumbers[intIndex++];
    }
    return random.nextInt(bound);
  }

  @Override
  public int nextInt(int min, int max) {
    if (min >= max) {
      throw new IllegalArgumentException("Invalid range");
    }
    int range = max - min;
    return min + nextInt(range);
  }

  @Override
  public double nextDouble() {
    if (predictableDoubleNumbers != null) {
      if (doubleIndex >= predictableDoubleNumbers.length) {
        doubleIndex = 0;
      }
      return predictableDoubleNumbers[doubleIndex++];
    }
    // custom for probability testing for simulate Action.
    if (random == null) {
      if (predictableIntNumbers[0] == 1) {
        return 0.3;
      } else if (predictableIntNumbers[0] == 2) {
        return 0.6;
      } else if (predictableIntNumbers[0] == 3) {
        return 0.8;
      } else {
        return 0.9;
      }
    }
    return random.nextDouble();

  }


}
