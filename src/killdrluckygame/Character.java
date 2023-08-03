package killdrluckygame;

/**
 * The {@code Character} interface represents the target character in a world game.
 * It has properties like name of the target character, the health af the target character and
 * check if it is the target character.
 */

public interface Character {

  /**
   * Returns the health of the target character.
   *
   * @return value indicating the health
   */
  int getHealth();

  /**
   * Returns the name of the target character.
   *
   * @return value indicating name.
   */
  String getCharacterName();

  /**
   * Checks if the character is target character.
   *
   * @return boolean value indicating if it is the target character.
   */

  boolean checkIfTargetCharacter();

  /**
   * Reduces the health of an entity by the specified damage value.
   *
   * @param damageValue The amount of damage to be applied to the entity's health.
   */
  void reduceHealth(int damageValue);
}
