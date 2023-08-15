package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.ArrayList;
import java.util.List;
import killdrluckygame.DrLuckyItem;
import killdrluckygame.DrLuckySpace;
import killdrluckygame.Item;
import killdrluckygame.Position;
import killdrluckygame.Space;
import killdrluckygame.WorldPosition;
import org.junit.Before;
import org.junit.Test;


/**
 * The DrLuckySpaceTest class is used to test the functionality of the DrLuckySpace class in the
 * world template.
 */

public class DrLuckySpaceTest {

  private Item itemOne;
  private Item itemTwo;
  private Item itemThree;
  private Space spaceOne;
  private Space spaceTwo;

  /**
   * Creates and returns a new instance of the Space class with the specified spaceName,
   * upperLeftWorldPosition, and lowerRightWorldPosition.
   *
   * @param spaceName               the name of the space
   * @param upperLeftWorldPosition  the upper-left position of the space in the world
   * @param lowerRightWorldPosition the lower-right position of the space in the world
   * @return a new instance of the Space class with the specified parameters
   */

  protected Space spaceValidChecker(String spaceName,
                                    Position upperLeftWorldPosition,
                                    Position lowerRightWorldPosition) {
    return new DrLuckySpace(spaceName, upperLeftWorldPosition, lowerRightWorldPosition);
  }

  /**
   * This setups the objects required for testing the spaces.
   */
  @Before
  public void setUp() {
    itemOne = new DrLuckyItem("Weapon 1", 8);
    itemTwo = new DrLuckyItem("Weapon 2", 9);
    itemThree = new DrLuckyItem("Weapon 1", 10);


    spaceOne = spaceValidChecker("Space 1", new WorldPosition(1, 2),
            new WorldPosition(4, 4));


    spaceTwo = spaceValidChecker("Space 1", new WorldPosition(1, 2),
            new WorldPosition(4, 4));
  }

  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a space with an
   * empty space name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceDescriptionEmptySpaceName() {
    spaceValidChecker("", new WorldPosition(22, 23),
            new WorldPosition(15, 16));
  }

  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a
   * space with a null upper-left position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceDescriptionEmptyUpperLeftPosition() {
    spaceValidChecker("Armory", null,
            new WorldPosition(15, 16));
  }

  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating a space with a
   * null lower-right position.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidSpaceDescriptionEmptyLowerRightPosition() {
    spaceValidChecker("Armory", new WorldPosition(22, 23),
            null);
  }


  /**
   * Test case to verify the correctness of the toString() method when the space contains items.
   */
  @Test
  public void testToString() {

    List<Item> itemsTemporaryList = new ArrayList<>();
    itemsTemporaryList.add(itemOne);
    itemsTemporaryList.add(itemTwo);


    String stringExpected = String.format("Space Information (Space Name = %s, "
                    + "WorldPosition UpperLeft"
                    + " Row = %s, WorldPosition UpperLeft Column = %s, "
                    + "WorldPosition LowerRight Row = %s, "
                    + "WorldPosition LowerRight Column = %s, Items = %s)",
            "Armory", 22, 23, 15, 16, itemsTemporaryList);


    Space space = spaceValidChecker("Armory", new WorldPosition(22, 23),
            new WorldPosition(15, 16));
    space.addItemToSpace(itemOne);
    space.addItemToSpace(itemTwo);
    assertEquals(stringExpected, space.toString());

  }

  /**
   * Test case to verify the correctness of the toString() method when the space does not contain
   * any items.
   */
  @Test
  public void testToStringEmptyItems() {

    String stringExpected = String.format("Space Information (Space Name = Armory, "
                    + "WorldPosition UpperLeft Row = 22, "
                    + "WorldPosition UpperLeft Column = 23, WorldPosition LowerRight Row = 15, "
                    + "WorldPosition LowerRight Column = 16, Items = %s)",
            "No Items");

    Space space = spaceValidChecker("Armory", new WorldPosition(22, 23),
            new WorldPosition(15, 16));

    assertEquals(stringExpected, space.toString());
  }

  /**
   * Test case to verify the correctness of the toString() method when the space contains one or
   * more items.
   */
  @Test
  public void testToStringOneMoreItems() {

    List<Item> itemsTemporaryList = new ArrayList<>();
    itemsTemporaryList.add(itemOne);
    itemsTemporaryList.add(itemTwo);

    String stringExpected = String.format("Space Information (Space Name = Armory, "
            + "WorldPosition UpperLeft Row = 22, WorldPosition UpperLeft Column = 23, "
            + "WorldPosition LowerRight Row = 15, WorldPosition LowerRight Column = 16, "
            + "Items = [Item Information (Item Name = Weapon 1, Damage Value = 8), "
            + "Item Information (Item Name = Weapon 2, Damage Value = 9)])");

    Space space = spaceValidChecker("Armory", new WorldPosition(22, 23),
            new WorldPosition(15, 16));

    space.addItemToSpace(itemOne);
    space.addItemToSpace(itemTwo);

    assertEquals(stringExpected, space.toString());

  }


  /**
   * Test case to verify the correctness of the getSpaceName() method.
   */
  @Test
  public void testGetSpaceName() {
    assertTrue(spaceOne.getSpaceName().equals("Space 1"));
  }


  /**
   * Test case to verify the correctness of the getUpperLeftPosition() and
   * getLowerRightPosition() methods.
   */
  @Test
  public void testGetCoordinates() {
    assertTrue(spaceOne.getUpperLeftPosition().getRow() == 1);
    assertTrue(spaceOne.getUpperLeftPosition().getColumn() == 2);
    assertTrue(spaceOne.getLowerRightPosition().getRow() == 4);
    assertTrue(spaceOne.getLowerRightPosition().getColumn() == 4);
  }

  /**
   * Test case to verify the correctness of the equals() method.
   */

  @Test
  public void testEquals() {

    assertTrue(spaceOne.equals(spaceOne));

    assertTrue(spaceOne.equals(new DrLuckySpace("Space 1", new WorldPosition(1,
            2), new WorldPosition(4, 4))));

    assertFalse(spaceOne.equals(new DrLuckySpace("Space 1", new WorldPosition(1,
            2), new WorldPosition(4, 5))));

    assertTrue(spaceValidChecker("Space 1", new WorldPosition(1, 2),
            new WorldPosition(4, 4))
            .equals(spaceValidChecker("Space 1", new WorldPosition(1, 2),
                    new WorldPosition(4, 4))));
  }


}
