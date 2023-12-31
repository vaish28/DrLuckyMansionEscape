package killdrluckygame;

/**
 * A class representing the target character's pet and implement the target character pet interface.
 */
public class Pet implements PetInterface {

  private final String petName;

  /**
   * A constructor for initialising the name of the target character.
   * @param petName the name of the pet.
   */
  public Pet(String petName) {
    if (petName == null || petName.trim().isEmpty()) {
      throw new IllegalArgumentException("Pet name cannot be null or empty");
    }
    this.petName = petName;
  }

  @Override
  public String getPetName() {
    return petName;
  }

}
