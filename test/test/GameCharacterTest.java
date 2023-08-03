package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import killdrluckygame.Character;
import killdrluckygame.GameCharacter;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the characters of the game playing it.
 */
public class GameCharacterTest {
  private Character gameCharacter;

  /**
   * Helper method to create a valid GameCharacter object for testing.
   *
   * @param characterName The name of the character.
   * @param health        The health of the character.
   * @param isTarget      Indicates if the character is the target character.
   * @return A valid GameCharacter object.
   */
  protected GameCharacter gameCharacterValidChecker(String characterName,
                                                    int health,
                                                    boolean isTarget) {
    return new GameCharacter(health, characterName, isTarget);
  }

  /**
   * This setups the values required to test the game character.
   */
  @Before
  public void setUp() {
    gameCharacter = gameCharacterValidChecker(
            "DrLucky", 50, true);
  }

  /**
   * Tests the getName() method of the GameCharacter class.
   */
  @Test
  public void testGetName() {
    assertTrue(gameCharacter.getCharacterName().equals("DrLucky"));
  }

  /**
   * Tests the getHealth() method of the GameCharacter class.
   */
  @Test
  public void testGetHealth() {
    assertEquals(gameCharacter.getHealth(), 50);
  }

  /**
   * Tests the checkIfTargetCharacter() method of the GameCharacter class.
   */
  @Test
  public void testIsTarget() {
    assertEquals(gameCharacter.checkIfTargetCharacter(), true);
  }

  /**
   * Tests the toString() method of the GameCharacter class.
   */
  @Test
  public void testString() {
    String expected = "Character Information (Character Name = DrLucky, Character Health = 50, "
            + "Character is Target = true)";
    assertEquals(gameCharacter.toString(), expected);
  }

  /**
   * Tests the reduceHealth() method of the GameCharacter class.
   */
  @Test
  public void testReduceHealth() {
    gameCharacter.reduceHealth(5);
    assertEquals(45, gameCharacter.getHealth());
  }
}
