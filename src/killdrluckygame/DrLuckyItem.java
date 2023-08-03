package killdrluckygame;

import java.util.Objects;

/**
 * The DrLuckyItem class represents the items used to kill the target character in the game.
 * It implements the Item interface.
 */

public class DrLuckyItem implements Item {

  private String itemName;
  private int damageValue;

  /**
   * This initialises the item name, room index and damage value of the item.
   *
   * @param itemName    value indicating the name of the item.
   * @param damageValue value indicating the damage value of the item.
   * @throws IllegalArgumentException illegal exception thrown for the invalid input.
   */
  public DrLuckyItem(String itemName, int damageValue) throws
          IllegalArgumentException {

    if ("".equals(itemName)
            || damageValue < 0) {
      throw new IllegalArgumentException(
              "Negative or invalid values ! Conditions must satisfy");
    } // checking for all illegal values


    this.itemName = itemName;

    this.damageValue = damageValue;
  }


  @Override
  public String getItemName() {
    return itemName;
  }

  @Override
  public int getDamageValue() {
    return damageValue;
  }

  @Override
  public String toString() {
    return String.format("Item Information (Item Name = %s, Damage Value = "
            + "%s)", getItemName(), getDamageValue());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Item)) {
      return false;
    }

    Item that = (Item) o;

    return this.getItemName().equals(that.getItemName())
            && this.getDamageValue() == that.getDamageValue();
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.getItemName(), this.getDamageValue());
  }

}
