package killdrluckygame;

/**
 * The GameCharacter class represents a game character and implements the Character interface.
 * It stores information about the character's health, name, and whether it is the target character.
 */
public class GameCharacter implements Character {

  private int health;
  private String characterName;
  private boolean isTarget;

  /**
   * Initializes the game character with the specified health, name, and target status.
   *
   * @param health        The health value of the character.
   * @param characterName The name of the character.
   * @param isTarget      Specifies whether the character is the target.
   * @throws IllegalArgumentException if the character name is empty or the health value is
   *                                  negative.
   */
  public GameCharacter(int health, String characterName, boolean isTarget) throws
          IllegalArgumentException {

    if ("".equals(characterName) || health < 0) {
      throw new IllegalArgumentException(
              "Negative or invalid values ! Conditions must satisfy");
    } // checking for all illegal values
    this.health = health;
    this.characterName = characterName;
    this.isTarget = isTarget;

  }

  @Override
  public int getHealth() {
    return this.health;
  }


  @Override
  public String getCharacterName() {
    return this.characterName;
  }


  @Override
  public boolean checkIfTargetCharacter() {
    return this.isTarget;
  }


  @Override
  public String toString() {
    return String.format("Character Information (Character Name = %s, Character Health = %s, "
                    + "Character is Target = %s)",
            getCharacterName(), getHealth(), checkIfTargetCharacter());
  }


  @Override
  public void reduceHealth(int damageValue) {
    this.health = this.health - damageValue;
  }

}
