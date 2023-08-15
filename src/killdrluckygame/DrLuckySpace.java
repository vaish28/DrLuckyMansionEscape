package killdrluckygame;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The DrLuckySpace class represents a space in the game world.
 * It implements the Space interface and provides methods to access and manipulate the properties
 * of the space.
 * Each space has a unique name, position coordinates, and contains items if alloted to the space.
 */
public class DrLuckySpace implements Space {

  private String spaceName;
  private Position upperLeftWorldPosition;

  private Position lowerRightWorldPosition;

  private List<Item> itemsInSpace;


  /**
   * Constructs a new DrLuckySpace object with the specified space name and position coordinates.
   *
   * @param spaceName              The name of the space.
   * @param upperLeftWorldPosition The upper-left position of the space.
   * @param loweRightWorldPosition The lower-right position of the space.
   * @throws IllegalArgumentException if the space name is empty or any position is null.
   */
  public DrLuckySpace(String spaceName, Position upperLeftWorldPosition,
                      Position loweRightWorldPosition)
          throws IllegalArgumentException {

    if ("".equals(spaceName) || upperLeftWorldPosition == null
            || loweRightWorldPosition == null) {
      throw new IllegalArgumentException(
              "Negative or invalid values ! Conditions must satisfy");
    } // checking for all illegal values

    this.spaceName = spaceName;

    this.upperLeftWorldPosition = upperLeftWorldPosition;

    this.lowerRightWorldPosition = loweRightWorldPosition;

    this.itemsInSpace = new ArrayList<>();

  }

  /**
   * Returns the name of the space.
   *
   * @return The name of the space.
   */
  @Override
  public String getSpaceName() {
    return this.spaceName;
  }

  /**
   * Returns the upper-left position of the space.
   *
   * @return The upper-left position of the space.
   */
  @Override
  public Position getUpperLeftPosition() {
    return this.upperLeftWorldPosition;
  }

  /**
   * Returns the lower-right position of the space.
   *
   * @return The lower-right position of the space.
   */
  @Override
  public Position getLowerRightPosition() {
    return this.lowerRightWorldPosition;
  }

  /**
   * Returns the list of items in the space.
   *
   * @return The list of items in the space.
   */
  @Override
  public List<Item> getItems() {
    return itemsInSpace;
  }

  /**
   * Adds an item to the space.
   *
   * @param item The item to be added.
   * @return true if the item is successfully added, false if the item already exists in the space.
   */
  @Override
  public boolean addItemToSpace(Item item) {

    if (this.getItems() != null && this.getItems().contains(item)) {
      return false;
    } else {
      this.getItems().add(item);
    }
    return true;
  }

  /**
   * Removes an item from the space.
   *
   * @param item The item to be removed.
   * @return true if the item is successfully removed, false if the item does not exist in the
   *         space.
   */

  @Override
  public boolean removeItemFromSpace(Item item) {
    if (this.getItems() != null && this.getItems().contains(item)) {
      this.getItems().remove(item);
      return true;
    }
    return false;
  }

  /**
   * Returns a string representation of the space.
   * The string includes the space name, upper-left and lower-right position coordinates, and
   * items in the space.
   *
   * @return A string representation of the space.
   */
  @Override
  public String toString() {
    return String.format("Space Information (Space Name = %s, WorldPosition UpperLeft Row = %s, "
                    + "WorldPosition UpperLeft Column = %s, WorldPosition LowerRight Row = %s, "
                    + "WorldPosition LowerRight Column = %s, Items = %s)",
            this.getSpaceName(), this.getUpperLeftPosition().getRow(),
            this.getUpperLeftPosition().getColumn(), this.getLowerRightPosition().getRow(),
            this.getLowerRightPosition().getColumn(),
            this.getItems().isEmpty() ? "No Items" : getItems());


  }

  /**
   * Checks if this DrLuckySpace object is equal to the specified object.
   * Two DrLuckySpace objects are considered equal if they have the same space name, position
   * coordinates, and items.
   *
   * @param o The object to compare.
   * @return true if the objects are equal, false otherwise.
   */
  @Override
  public boolean equals(Object o) {

    // Fast path for pointer equality:
    if (this == o) { // backward compatibility with default equals
      return true;
    }

    // If o isn't the right class then it can't be equal:
    if (!(o instanceof Space)) {
      return false;
    }

    Space that = (Space) o;

    return this.getSpaceName().equals(that.getSpaceName())
            && this.getUpperLeftPosition().getRow() == that.getUpperLeftPosition().getRow()
            && this.getLowerRightPosition().getRow() == that.getLowerRightPosition().getRow()
            && this.getUpperLeftPosition().getColumn() == that.getUpperLeftPosition().getColumn()
            && this.getLowerRightPosition().getColumn() == that.getLowerRightPosition().getColumn();
  }

  /**
   * Generates a hash code for this DrLuckySpace object.
   * The hash code is based on the space name, position coordinates, and items.
   *
   * @return The hash code value for this object.
   */
  @Override
  public int hashCode() {

    return Objects.hash(this.getSpaceName(), this.getLowerRightPosition().getColumn(),
            this.getLowerRightPosition().getRow(), this.getUpperLeftPosition().getRow(),
            this.getUpperLeftPosition().getColumn());
  }

  /**
   * Checks if two lists of items are equal.
   *
   * @param items1 The first list of items.
   * @param items2 The second list of items.
   * @return true if the lists are equal, false otherwise.
   */
  private boolean checkItems(List<Item> items1, List<Item> items2) {
    if (isSizeSame(items1, items2)) {
      return false;
    }
    for (int i = 0; i < items1.size(); i++) {
      if (!items1.get(i).equals(items2.get(i))) {
        return false;
      }
    }
    return true;
  }

  /**
   * Checks if two lists of items have different sizes.
   *
   * @param items1 The first list of items.
   * @param items2 The second list of items.
   * @return true if the lists have different sizes, false otherwise.
   */
  private static boolean isSizeSame(List<Item> items1, List<Item> items2) {
    return items1.size() != items2.size();
  }


}






