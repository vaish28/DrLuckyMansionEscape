package killdrluckygame;

/**
 * The Item interface represents an item in the game world.
 * It defines methods to retrieve the name and damage value of the item.
 */
public interface Item {

  /**
   * This function returns the name of the item as a string.
   *
   * @return name of the weapon
   */
  String getItemName();

  /**
   * This function returns the damage value of the weapon/item.
   *
   * @return damage value of the weapon
   */
  int getDamageValue();
}
