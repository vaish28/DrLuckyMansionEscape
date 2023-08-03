package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import killdrluckygame.DrLuckyItem;
import killdrluckygame.Item;
import org.junit.Before;
import org.junit.Test;

/**
 * The DrLuckyItemTest class is used to test the functionality of the DrLuckyItem class.
 */

public class DrLuckyItemTest {

  private Item item1;

  /**
   * Creates and returns a new instance of the Item class with the specified itemName and
   * damageValue.
   *
   * @param itemName    the name of the item
   * @param damageValue the damage value of the item
   * @return a new instance of the Item class with the specified itemName and
   *         damageValue
   */
  protected Item itemValidChecker(String itemName,
                                  int damageValue) {
    return new DrLuckyItem(itemName, damageValue);
  }

  /**
   * Sets up the test environment by initializing the item1 instance.
   */
  @Before
  public void setUp() {
    item1 = new DrLuckyItem("item 1", 5);
  }

  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating an item with an
   * empty item name.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidItemDescriptionEmptyItemName() {
    itemValidChecker("",
            3);
  }


  /**
   * Test case to verify that an IllegalArgumentException is thrown when creating an item with a
   * negative damage value.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDamageValue() {
    itemValidChecker("", -3);
  }

  /**
   * Test case to verify the correctness of getItemName() method.
   */
  @Test
  public void testGetItemName() {
    assertTrue(item1.getItemName().equals("item 1"));
  }

  /**
   * Test case to verify the correctness of getDamageValue() method.
   */
  @Test
  public void testGetItemDamageValue() {
    assertEquals(item1.getDamageValue(), (5));
  }

  /**
   * Test case to verify the correctness of the toString() method.
   */
  @Test
  public void testToString() {
    String stringExpected = String.format("Item Information (Item Name = %s, "
            + "Damage Value = %s)", "Sharp Knife", 3);
    Item item = itemValidChecker("Sharp Knife", 3);
    assertEquals(stringExpected, item.toString());
  }

  /**
   * Test case to verify the correctness of the equals() method.
   */
  @Test
  public void testEquals() {
    Item item = new DrLuckyItem("item 1", 5);
    assertTrue(item.equals(item));
    assertTrue(item.equals(new DrLuckyItem("item 1", 5)));
    assertTrue(itemValidChecker("item 2", 4)
            .equals(itemValidChecker("item 2", 4)));
  }

  /**
   * Test case to verify the correctness of the hashCode() method.
   */
  @org.junit.Test
  public void testHashCode() {
    assertEquals(itemValidChecker("item 1", 5).hashCode(),
            item1.hashCode());
  }
}
