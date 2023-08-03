package killdrluckygame;

/**
 * A class representing the target character's pet and implement the target character pet interface.
 */
public class TargetCharacterPet implements TargetCharacterPetInterface {

  private final String petName;

  /**
   * A constructor for initialising the name of the target character.
   * @param petName the name of the pet.
   */
  public TargetCharacterPet(String petName) {
    this.petName = petName;
  }

  @Override
  public String getPetName() {
    return petName;
  }

}
