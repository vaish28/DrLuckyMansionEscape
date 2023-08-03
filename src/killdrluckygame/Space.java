package killdrluckygame;

import java.util.List;

/**
 * This represents the space in the world.
 * It has space name,position co-ordinates, neighbors and weapon names.
 */
public interface Space {


  /**
   * Returns the space name.
   *
   * @return name of the space
   */
  String getSpaceName();

  /**
   * Returns the position of the upper left corner of the space.
   *
   * @return WorldPosition object
   */
  Position getUpperLeftPosition();

  /**
   * Returns the position of the lower right corner of the space.
   *
   * @return WorldPosition object
   */
  Position getLowerRightPosition();


  /**
   * Returns a list oof items in that space.
   *
   * @return list indicating items.
   */
  List<Item> getItems();


  /**
   * Returns true if the item was added successfully to the list of items for that space.
   * returns false if the item was not added successfully.
   *
   * @param item item to be added in the space.
   * @return boolean value indicating if the item was added in the room or not.
   */
  boolean addItemToSpace(Item item);

  /**
   * This method removes the item from the space.
   *
   * @param item the item to be removed.
   * @return returns true or false depending on the situation.
   */


  boolean removeItemFromSpace(Item item);


}
