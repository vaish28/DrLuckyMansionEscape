package test;

import static org.junit.Assert.assertEquals;

import java.io.StringReader;
import killdrluckygame.DrLuckyWorld;
import killdrluckygame.Space;
import killdrluckygame.World;
import org.junit.Before;
import org.junit.Test;

/**
 * This class tests the input parsing.
 */
public class InputTest {

  private World inputTestObjectOne;
  private DrLuckyWorld inputTestObjectTwo;

  private DrLuckyWorld.Input testObjectOne;

  private DrLuckyWorld.Input testObjectTwo;

  /**
   * This setups objects required for testing the input for parsing.
   */
  @Before
  public void setUp() {


    StringBuilder stringBuilder = new StringBuilder();

    stringBuilder.append("36 30 Doctor Lucky's Mansion\n");
    stringBuilder.append("50 Doctor Lucky\n");
    stringBuilder.append("Fortune Dr Lucky Cat\n");
    stringBuilder.append("21\n");
    stringBuilder.append("22 19 23 26 Armory\n");
    stringBuilder.append("16 21 21 28 Billiard Room\n");
    stringBuilder.append("28  0 35  5 Carriage House\n");
    stringBuilder.append("12 11 21 20 Dining Hall\n");
    stringBuilder.append("22 13 25 18 Drawing Room\n");
    stringBuilder.append("26 13 27 18 Foyer\n");
    stringBuilder.append("28 26 35 29 Green House\n");
    stringBuilder.append("30 20 35 25 Hedge Maze\n");
    stringBuilder.append("16  3 21 10 Kitchen\n");
    stringBuilder.append(" 0  3  5  8 Lancaster Room\n");
    stringBuilder.append(" 4 23  9 28 Library\n");
    stringBuilder.append(" 2  9  7 14 Lilac Room\n");
    stringBuilder.append(" 2 15  7 22 Master Suite\n");
    stringBuilder.append(" 0 23  3 28 Nursery\n");
    stringBuilder.append("10  5 15 10 Parlor\n");
    stringBuilder.append("28 12 35 19 Piazza\n");
    stringBuilder.append(" 6  3  9  8 Servants' Quarters\n");
    stringBuilder.append(" 8 11 11 20 Tennessee Room\n");
    stringBuilder.append("10 21 15 26 Trophy Room\n");
    stringBuilder.append("22  5 23 12 Wine Cellar\n");
    stringBuilder.append("30  6 35 11 Winter Garden\n");
    stringBuilder.append("20\n");
    stringBuilder.append("8 3 Crepe Pan\n");
    stringBuilder.append("4 2 Letter Opener\n");
    stringBuilder.append("12 2 Shoe Horn\n");
    stringBuilder.append("8 3 Sharp Knife\n");
    stringBuilder.append("0 3 Revolver\n");
    stringBuilder.append("15 3 Civil War Cannon\n");
    stringBuilder.append("2 4 Chain Saw\n");
    stringBuilder.append("16 2 Broom Stick\n");
    stringBuilder.append("1 2 Billiard Cue\n");
    stringBuilder.append("19 2 Rat Poison\n");
    stringBuilder.append("6 2 Trowel\n");
    stringBuilder.append("2 4 Big Red Hammer\n");
    stringBuilder.append("6 2 Pinking Shears\n");
    stringBuilder.append("18 3 Duck Decoy\n");
    stringBuilder.append("13 2 Bad Cream\n");
    stringBuilder.append("18 2 Monkey Hand\n");
    stringBuilder.append("11 2 Tight Hat\n");
    stringBuilder.append("19 2 Piece of Rope\n");
    stringBuilder.append("9 3 Silken Cord\n");
    stringBuilder.append("7 2 Loud Noise");


    String result = stringBuilder.toString();
    inputTestObjectOne = new DrLuckyWorld.Input().readInput(new StringReader(result));
  }

  /**
   * Tests the parsing of the input file.
   */
  @Test
  public void testParsingFile() {
    assertEquals(inputTestObjectOne.getName(), "Doctor Lucky's Mansion");
    assertEquals(inputTestObjectOne.getRows(), 36);
    assertEquals(inputTestObjectOne.getColumns(), 30);
  }

  /**
   * Tests reading the world specifications.
   */
  @Test
  public void testReadWorldSpecification() {
    assertEquals(21, inputTestObjectOne.getTotalSpaces());

    Space currentSpace = inputTestObjectOne.getCurrentSpaceTargetIsIn();
    assertEquals(currentSpace.getUpperLeftPosition().getRow(), 22);
    assertEquals(currentSpace.getUpperLeftPosition().getColumn(), 19);
    assertEquals(currentSpace.getLowerRightPosition().getRow(), 23);
    assertEquals(currentSpace.getLowerRightPosition().getColumn(), 26);
    assertEquals(currentSpace.getSpaceName(), "Armory");
  }

  /**
   * Tests the movement to the Billiard Room space.
   */
  @Test
  public void testSpaceBilliardRoom() {
    inputTestObjectOne.moveTargetCharacter();

    Space currentSpaceBilliard = inputTestObjectOne.getCurrentSpaceTargetIsIn();
    assertEquals(currentSpaceBilliard.getUpperLeftPosition().getRow(), 16);
    assertEquals(currentSpaceBilliard.getUpperLeftPosition().getColumn(), 21);
    assertEquals(currentSpaceBilliard.getLowerRightPosition().getRow(), 21);
    assertEquals(currentSpaceBilliard.getLowerRightPosition().getColumn(), 28);
    assertEquals(currentSpaceBilliard.getSpaceName(), "Billiard Room");
  }


}
