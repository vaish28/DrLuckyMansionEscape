package killdrluckygame;

import java.util.List;

public interface ReadOnlyWorldModel {

  /**
   * Returns the  name of the world.
   *
   * @return string The name of the world as a string.
   */
  String getName();

  /**
   * Returns the total number of rows in the world.
   *
   * @return int The number of rows in the world.
   */
  int getRows();

  /**
   * Returns the total number of columns in the world.
   *
   * @return int The number of columns in the world.
   */
  int getColumns();

  /**
   * Returns the total number of spaces in the world.
   *
   * @return integer value indicating the number of spaces.
   */
  int getTotalSpaces();


  /**
   * Returns the spaces in the world as list of spaces.
   *
   * @return spaces as a list of spaces.
   */
  List<Space> getSpaces();

  /**
   * Returns the current space the target player is in.
   *
   * @return a space interface object.
   */
  Space getCurrentSpaceTargetIsIn();

  /**
   * A function to return the target health.
   *
   * @return an integer values indicating the health of the target character.
   */
  int getTargetHealth();


  /**
   * Returns the details about the target character.
   *
   * @return target details as string.
   */
  String targetCharacterDetails();

  /**
   * Display th potential list of spaces a player can move into.
   *
   * @return string value indicating the list od spaces.
   */
  String displayPotentialListOfSpaces();
}
